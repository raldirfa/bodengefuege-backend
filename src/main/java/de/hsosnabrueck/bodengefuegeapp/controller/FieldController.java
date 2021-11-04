package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.dto.SampleDto;
import de.hsosnabrueck.bodengefuegeapp.model.*;
import de.hsosnabrueck.bodengefuegeapp.repository.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class FieldController {

    final FieldRepository fieldRepository;
    final SampleRepository sampleRepository;
    final UserRepository userRepository;
    final CropRepository cropRepository;

    @Autowired
    SoilRepository soilRepository;


    private String issuer = "de.hsosnabrueck.bodengefuege";

    public FieldController(FieldRepository fieldRepository, SampleRepository sampleRepository, UserRepository userRepository, CropRepository cropRepository) {
        this.fieldRepository = fieldRepository;
        this.sampleRepository = sampleRepository;
        this.userRepository = userRepository;
        this.cropRepository = cropRepository;
    }

    @GetMapping("/fields")
    public ResponseEntity<String> fieldsGet(@RequestHeader("token") String jwtToken) {

        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userRepository.getUserFromToken(jwtToken);

            return new ResponseEntity<String>(createResponseFields(user), HttpStatus.OK);
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/fields")
    public ResponseEntity<String> fieldsPost(@RequestHeader("token") String jwtToken, @RequestBody Map<String,Object> inputData) {
        Gson gson = new GsonBuilder().create();
        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if(inputData == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("field")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            JSONObject jsonObjectField = (JSONObject) jsonObject.get("field");
            User user = userRepository.getUserFromToken(jwtToken);
            Field field = new Field(jsonObjectField.get("name").toString(), user);
            Grade grade = new Grade(Double.parseDouble(jsonObjectField.get("lng").toString()),Double.parseDouble(jsonObjectField.get("lat").toString()));
            Soil soil = gson.fromJson(jsonObjectField.get("soil").toString(), Soil.class);
            field.setGrade(grade);
            field.setSoil(soil);
            if(user.getFields() == null){
                ArrayList<Field> arrayList = new ArrayList<>();
                arrayList.add(field);
                user.setFields(arrayList);
            }
            else{
                user.getFields().add(field);
            }
            userRepository.save(user);

            return new ResponseEntity<String>( HttpStatus.OK);
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/fields/{fieldId}")
    public ResponseEntity fieldsPatch(@RequestHeader("token") String jwtToken, @PathVariable long fieldId, @RequestBody Map<String,Object> inputData) {
        Gson gson = new GsonBuilder().create();
        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if(inputData == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("field")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            JSONObject jsonObjectField = (JSONObject) jsonObject.get("field");
            Optional<Field> field = fieldRepository.findById(Long.parseLong(jsonObjectField.get("id").toString()));
            User user = userRepository.getUserFromToken(jwtToken);
            field.get().setName(jsonObjectField.get("name").toString());
            Soil soil = gson.fromJson(jsonObjectField.get("soil").toString(), Soil.class);
            field.get().setSoil(soil);
            Grade grade = new Grade(Double.parseDouble(jsonObjectField.get("lng").toString()),Double.parseDouble(jsonObjectField.get("lat").toString()));
            field.get().setGrade(grade);
            
            userRepository.save(user);

            return new ResponseEntity<String>( HttpStatus.OK);
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/fields/{fieldId}")
    public ResponseEntity fieldsDelete(@RequestHeader("token") String jwtToken, @PathVariable long fieldId) {
        Gson gson = new GsonBuilder().create();
        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }       
        try {
            fieldRepository.deleteById(fieldId);

            return new ResponseEntity<String>( HttpStatus.OK);
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fields/{fieldId}/samples")
    public ResponseEntity addSample(@RequestHeader("token") String jwtToken, @PathVariable long fieldId, @RequestBody SampleDto sampleDto) {
        if (jwtToken == null) {
            return ResponseEntity.badRequest().build();
        }

        // TODO map dto into sample
        Field field = fieldRepository.findById(fieldId).get();
        Crop crop = cropRepository.findById(sampleDto.getCrop()).get();
        Sample sample = new Sample(field, sampleDto.getDate(), sampleDto.getProperties(), crop, sampleDto.getGrade());

        sampleRepository.save(sample);

        return ResponseEntity.ok("");
    }

    @DeleteMapping("/fields/{fieldId}/samples/{sampleId}")
    public ResponseEntity deleteSample(@RequestHeader("token") String jwtToken, @PathVariable long fieldId, @PathVariable long sampleId) {
        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        Sample sample = sampleRepository.findById(sampleId).get();
        sampleRepository.delete(sample);

        return ResponseEntity.ok("");
    }


    private String createResponseFields(User user) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("fields", createFieldsArrayList(user));
        String json = gson.toJson(map);

        return json;
    }

    private ArrayList<Map<String, Object>> createFieldsArrayList(User user) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();

        for (Field f : user.getFields()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", f.getId());
            map.put("name", f.getName());
            map.put("lng", f.getGrade().getLongitude());
            map.put("lat", f.getGrade().getLatitude());
            map.put("soil", f.getSoil());
            arrayList.add(map);
        }

        return arrayList;
    }
   

}
