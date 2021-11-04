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

import java.lang.reflect.Array;
import java.util.*;

@RestController
public class CropController {

    final UserRepository userRepository;
    final CropRepository cropRepository;

    public CropController(UserRepository userRepository, CropRepository cropRepository) {
        this.userRepository = userRepository;
        this.cropRepository = cropRepository;
    }

    @GetMapping("/crops")
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
        if(!jsonObject.has("crop")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        try{
            User user = userRepository.getUserFromToken(jwtToken);
            List<Crop> cropsList = user.getFavCrops();
            JSONObject jsonObjectCrop = (JSONObject) jsonObject.get("crop");
            Optional<Crop> c = cropRepository.findById(Long.parseLong(jsonObjectCrop.get("id").toString()));

            if(cropsList == null || cropsList.isEmpty()){
                cropsList = new ArrayList<>();
                cropsList.add(c.get());
                user.getFavCrops().add(c.get());


            }
            else{
                System.out.println("l");
                System.out.println(user.getFavCrops());
                updateFavCrops(cropsList, jsonObjectCrop);
                System.out.println(user.getFavCrops());
            }

            
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateFavCrops(List<Crop> cropsList, JSONObject crop){
        System.out.println("j");
        for(Crop c : cropsList){
            System.out.println("c: " + c);
            System.out.println("c1: " + crop);
            if(c.getId() == Integer.parseInt(crop.getString("id"))){

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
}
