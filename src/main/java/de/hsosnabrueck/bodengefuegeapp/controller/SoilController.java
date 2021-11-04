package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.Soil;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.SoilRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SoilController {
    
    final UserRepository userRepository;
    final SoilRepository soilRepository;

    public SoilController(UserRepository userRepository, SoilRepository soilRepository) {
        this.userRepository = userRepository;
        this.soilRepository = soilRepository;
    }

    @GetMapping("/soils")
    public ResponseEntity<String> cropsGet(@RequestHeader("token") String jwtToken) {
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try{
            User user = userRepository.getUserFromToken(jwtToken);

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

        map.put("soils", createSoilArrayList(user));
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
   
}
