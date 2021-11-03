package de.hsosnabrueck.bodengefuegeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class FieldController {

    private String issuer = "de.hsosnabrueck.bodengefuege";

    @GetMapping("/fields")
    public String fields(@RequestHeader String jwtToken, @RequestBody Map<String, Object> inputData) {


        return "fields";
    }

    @DeleteMapping("/fields")
    public ResponseEntity delete(@RequestHeader String jwtToken) {
        if(jwtToken == null){
            return ResponseEntity.badRequest().build();
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
