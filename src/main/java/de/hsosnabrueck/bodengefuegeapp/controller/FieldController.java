package de.hsosnabrueck.bodengefuegeapp.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.result.Field;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;

@RestController
public class FieldController {

    private String issuer = "de.hsosnabrueck.bodengefuege";

    @GetMapping("/fields")
    public String fields(@RequestHeader String jwtToken, @RequestBody Map<String, Object> inputData) {


        return "fields";
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
