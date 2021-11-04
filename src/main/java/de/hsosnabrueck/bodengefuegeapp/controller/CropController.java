package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.CropRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;

import org.hibernate.annotations.SourceType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CropController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CropRepository cropRepository;

    @GetMapping("/crops")
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

    @PatchMapping("/crops")
    public ResponseEntity<String> cropsPatch(@RequestHeader("token") String jwtToken, @RequestBody Map<String, Object> inputData)
    {
        Gson gson = new GsonBuilder().create();
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        if(inputData == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("crops")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        try{
            User user = getUserFromToken(jwtToken);
            List<Crop> cropsList = user.getFavCrops();
            JSONObject crops = new JSONObject(inputData);
            
            System.out.println(cropsList);
            System.out.println(crops);

            updateFavCrops(cropsList, crops);
            
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateFavCrops(List<Crop> cropsList, JSONObject crops){
        for(Crop c : cropsList){
            if(c.getId() == Integer.parseInt(crops.getString("id"))){
                System.out.println(c.getId() + " getid");
            }
        }
    }

    private String createResponse(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("crops", createCropArrayList(user));
        String json = gson.toJson(map);

        return json;
    }

    private ArrayList<Map<String,Object>> createCropArrayList(User user){
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

    private User getUserFromToken(String jwtToken){
        for(User u: userRepository.findAll()){
            if(u.getJwtToken().equals(jwtToken)){
                return u;
            }
        }
        return null;
    }

}
