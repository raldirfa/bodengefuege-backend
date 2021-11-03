package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.CropRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CropController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CropRepository cropRepository;

    @GetMapping("/crops")
    public ResponseEntity<String> userGet(@RequestHeader("token") String jwtToken) {
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try{
            Claims claims = JwtToken.decodeJWT(jwtToken);

            Optional<User> user = userRepository.findById(Long.valueOf((Integer) claims.get("id")));

            return new ResponseEntity<String>(createResponse(user.get()),HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }

    private String createResponse(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("crops", createCropMap(user));
        String json = gson.toJson(map);

        return json;
    }

    private ArrayList<Map<String,Object>> createCropMap(User user){
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();

        for(Crop c: cropRepository.findAll()){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c.getId());
            map.put("name", c.getName());
            if(isCropInList(c.getId(), user)){
                map.put("favorite", true);
            }
            else{
                map.put("favorite", false);
            }
            arrayList.add(map);
        }
        return arrayList;
    }

    private boolean isCropInList(long id, User user){
        for(Crop c: user.getFavCrops()){
            if(c.getId() == id){
                return true;
            }
        }
        return false;
    }

}
