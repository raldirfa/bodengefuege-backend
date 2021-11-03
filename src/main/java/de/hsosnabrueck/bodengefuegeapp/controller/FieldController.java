package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.*;
import de.hsosnabrueck.bodengefuegeapp.repository.FieldRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.SampleRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
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


    private String issuer = "de.hsosnabrueck.bodengefuege";

    @GetMapping("/fields")
    public ResponseEntity<String> fields(@RequestHeader("token") String jwtToken) {

        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try{
            Claims claims = JwtToken.decodeJWT(jwtToken);

            Optional<User> user = userRepository.findById(Long.valueOf((Integer) claims.get("id")));

            return new ResponseEntity<String>(createResponseFields(user.get()), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }


    }

    //Todo DELETEMAPPING
    @GetMapping("/fields/{fieldId}/samples/{sampleId}")
    public ResponseEntity delete(/*@RequestHeader String jwtToken,*/ @PathVariable long fieldId, @PathVariable long sampleId) {
        //if(jwtToken == null){
            //return ResponseEntity.badRequest().build();
        //}

        Sample sample = sampleRepository.findById(sampleId).get();

        sampleRepository.delete(sample);

        Field field = fieldRepository.findById(fieldId).get();

        for (Sample s : field.getSamples()) {
            System.out.println(s.getDate());
        }

        return ResponseEntity.ok("");
    }


    private String createResponseFields(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("fields", createFieldsArrayList(user));
        String json = gson.toJson(map);

        return json;
    }

    private ArrayList<Map<String,Object>> createFieldsArrayList(User user){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();

        //ToDo: Delte Map2 - DEMO
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("id",1);
        map2.put("name","testName");
        arrayList.add(map2);
        for(Field f: user.getFields()){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", f.getId());
            map.put("name", f.getName());
            map.put("grade",f.getGrade());
            map.put("soil",f.getSoil());
            arrayList.add(map);
        }

        return arrayList;
    }


}
