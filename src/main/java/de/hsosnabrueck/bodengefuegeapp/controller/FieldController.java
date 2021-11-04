package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.dto.SampleDto;
import de.hsosnabrueck.bodengefuegeapp.model.Field;
import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.Sample;
import de.hsosnabrueck.bodengefuegeapp.model.User;
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

        if (jwtToken == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try {
            Claims claims = JwtToken.decodeJWT(jwtToken);

            Optional<User> user = userRepository.findById(Long.valueOf((Integer) claims.get("id")));

            return new ResponseEntity<String>(createResponseFields(user.get()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/fields/{fieldId}/samples")
    public ResponseEntity addSample(@RequestHeader String jwtToken, @RequestBody SampleDto sampleDto) {
        if (jwtToken == null) {
            return ResponseEntity.badRequest().build();
        }

        // TODO map dto into sample
        Sample sample = new Sample(sampleDto.getField(), sampleDto.getDate(), sampleDto.getProperties(), sampleDto.getCrop(), sampleDto.getGrade());

        sampleRepository.save(sample);

        return ResponseEntity.ok("");
    }

    @DeleteMapping("/fields/{fieldId}/samples/{sampleId}")
    public ResponseEntity delete(@RequestHeader String jwtToken, @PathVariable long fieldId, @PathVariable long sampleId) {
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

        //ToDo: Delte Map2 - DEMO
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("id", 1);
        map2.put("name", "testName");
        arrayList.add(map2);
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


}
