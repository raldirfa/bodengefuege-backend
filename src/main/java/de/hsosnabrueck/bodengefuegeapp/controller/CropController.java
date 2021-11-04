package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.CropRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;

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
            Claims claims = JwtToken.decodeJWT(jwtToken);

            Optional<User> user = userRepository.findById(Long.valueOf((Integer) claims.get("id")));

            return new ResponseEntity<String>(createResponse(user.get()),HttpStatus.OK);
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
        //String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJkZS5oc29zbmFicnVlY2suYm9kZW5nZWZ1ZWdlIiwiaWQiOjEsInVzZXIiOiJ1c2VyQGVtYWlsLmRlIn0.08iOMKV3FWuuIYqERSzmqOk-bEfVjTqSB84r_KUsS_X-UlUwFIDm5nt35PkHQejpPytpyvXmElmK2x6vWHMPDg";
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
            Claims claims = JwtToken.decodeJWT(jwtToken);
            User inputUser = gson.fromJson(jsonObject.get("user").toString(), User.class);
            List<Crop> crops = inputUser.getFavCrops();



            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    private void updateFavCrops(List<Crop> crops){
        
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
