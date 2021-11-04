package de.hsosnabrueck.bodengefuegeapp.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.hsosnabrueck.bodengefuegeapp.model.Soil;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.SoilRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;

@RestController
public class SoilController {
    
    @Autowired
    UserRepository userRepository; 

    @Autowired
    SoilRepository soilRepository;

    @GetMapping("/soil")
    public ResponseEntity<String> cropsGet(@RequestHeader("token") String jwtToken) {
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try{
            User user = getUserFromToken(jwtToken);

            return new ResponseEntity<String>(createResponse(user),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }

    private String createResponse(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("crops", createSoilArrayList(user));
        String json = gson.toJson(map);

        return json;
    }

    private ArrayList<Map<String,Object>> createSoilArrayList(User user){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();

        for(Soil s: soilRepository.findAll()){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", s.getId());
            map.put("name", s.getName());
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
