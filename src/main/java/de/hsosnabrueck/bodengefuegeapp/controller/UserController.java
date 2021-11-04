package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hsosnabrueck.bodengefuegeapp.model.Setting;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class UserController {

    final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, Object> inputData) {
        if(inputData == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("email") || !jsonObject.has("password")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        User user = isUserValid(jsonObject.getString("email"),jsonObject.getString("password"));
        if(user == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(createResponseWithToken(user), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody Map<String, Object> inputData) {
        if(inputData == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("email") || !jsonObject.has("password") || !jsonObject.has("username")){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(jsonObject.getString("username"), jsonObject.getString("email"), jsonObject.getString("password"));
        userRepository.save(user);

        return new ResponseEntity<String>(createResponseWithToken(user), HttpStatus.OK);
        
    }

    @GetMapping("/user")
    public ResponseEntity<String> userGet(@RequestHeader("token") String jwtToken) {
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        try{           
            User user = userRepository.getUserFromToken(jwtToken);

            return new ResponseEntity<String>(createResponseWithoutToken(user), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        
    }

      @PatchMapping("/user")
      public ResponseEntity<String> userPatch(@RequestHeader("token") String jwtToken, @RequestBody Map<String, Object> inputData) {
          Gson gson = new GsonBuilder().create();
          
          if(jwtToken == null){
              return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
          }

          if(inputData == null){
              return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
          }
          JSONObject jsonObject = new JSONObject(inputData);
          if(!jsonObject.has("user")){
              return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
          }

          try{
              User inputUser = gson.fromJson(jsonObject.get("user").toString(), User.class);

              JSONObject jsonObjectUser = (JSONObject) jsonObject.get("user");
              Setting inputSetting = gson.fromJson(jsonObjectUser.get("settings").toString(), Setting.class);

              User user = getUserFromToken(jwtToken);

              user.setEmail(inputUser.getEmail());
              user.setUsername(inputUser.getUsername());
              user.setSetting(inputSetting);
              userRepository.save(user);

              return new ResponseEntity<String>(HttpStatus.OK);
          }
          catch(Exception e){
              return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
          }
      }

    private String createResponseWithToken(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();        

        map.put("token", user.getJwtToken());   
        map.put("user", createUserMap(user));
        String json = gson.toJson(map);

        return json;
    }

    private String createResponseWithoutToken(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();        
   
        map.put("user", createUserMap(user));
        String json = gson.toJson(map);

        return json;
    }

    private Map<String,Object> createUserMap(User user){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("settings", user.getSetting());

        return map;
    }

    private User isUserValid(String email, String password){
        for (User u : userRepository.findAll()) {
            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
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
