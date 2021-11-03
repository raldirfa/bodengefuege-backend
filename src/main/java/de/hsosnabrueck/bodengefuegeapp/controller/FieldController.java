package de.hsosnabrueck.bodengefuegeapp.controller;

import de.hsosnabrueck.bodengefuegeapp.model.Field;
import de.hsosnabrueck.bodengefuegeapp.model.Sample;
import de.hsosnabrueck.bodengefuegeapp.repository.FieldRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FieldController {

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    SampleRepository sampleRepository;


    private String issuer = "de.hsosnabrueck.bodengefuege";

    @GetMapping("/fields")
    public String fields(@RequestHeader String jwtToken, @RequestBody Map<String, Object> inputData) {


        return "fields";
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




    // private ResponseEntity<String> createResponse(Field field){
    //     Gson gson = new GsonBuilder().create();
    //     Map<String, Object> map = new LinkedHashMap<>();
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.set("JWT-Token", JwtToken.createJWT(issuer, user));

    //     //map <- fields

    //     String json = gson.toJson(map);
    //     return new ResponseEntity<String>(json, headers, HttpStatus.CREATED);
    // }
}
