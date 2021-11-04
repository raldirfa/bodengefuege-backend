package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.dto.SampleDto;
import de.hsosnabrueck.bodengefuegeapp.model.*;
import de.hsosnabrueck.bodengefuegeapp.repository.CropRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.FieldRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.SampleRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
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

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CropRepository cropRepository;


    private String issuer = "de.hsosnabrueck.bodengefuege";

    @GetMapping("/fields")
    public ResponseEntity<String> fieldsGet(@RequestHeader("token") String jwtToken) {

        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            User user = getUserFromToken(jwtToken);

            return new ResponseEntity<String>(createResponseFields(user), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/fields")
    public ResponseEntity<String> fieldsPost(@RequestHeader("token") String jwtToken, @RequestBody Map<String,Object> inputData) {

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
            User user = getUserFromToken(jwtToken);
            Field field = new Field(jsonObjectField.get("name").toString(), user);
            Grade grade = new Grade(Double.parseDouble(jsonObjectField.get("lng").toString()),Double.parseDouble(jsonObjectField.get("lat").toString()) );
            field.setGrade(grade);
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
            e.printStackTrace();
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
            map.put("grade", f.getGrade());
            map.put("soil", f.getSoil());
            arrayList.add(map);
        }

        return arrayList;
    }

    private User getUserFromToken(String jwtToken){
        for(User u: userRepository.findAll()){
            if(u.getJwtToken().equals(jwtToken)){
                return u;
            }
        }
        return null;
    }

}
