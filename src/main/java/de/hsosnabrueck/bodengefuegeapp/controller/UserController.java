package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import org.hibernate.annotations.SourceType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import io.jsonwebtoken.Claims;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private String issuer = "de.hsosnabrueck.bodengefuege";

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
    public ResponseEntity<String> user() {
        String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJkZS5oc29zbmFicnVlY2suYm9kZW5nZWZ1ZWdlIiwiaWQiOjEsInVzZXIiOiJ1c2VyQGVtYWlsLmRlIn0.08iOMKV3FWuuIYqERSzmqOk-bEfVjTqSB84r_KUsS_X-UlUwFIDm5nt35PkHQejpPytpyvXmElmK2x6vWHMPDg";
        if(jwtToken == null){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        Claims claims = JwtToken.decodeJWT(jwtToken);
        Optional<User> user = userRepository.findById(Long.valueOf((Integer) claims.get("id")));



        return new ResponseEntity<String>(createResponseWithoutToken(user.get()), HttpStatus.OK);
        
    }

    //  @PatchMapping("/user")
    //  public ResponseEntity<String> user(@RequestHeader String jwtToken, @RequestBody Map<String, Object> inputData) {
    //     if(inputData == null || jwtToken == null){
    //         return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    //     }
    //     JSONObject jsonObject = new JSONObject(inputData);
    //     if(!jsonObject.has("email") || !jsonObject.has("password") || !jsonObject.has("username")){ //ToDo Change
    //         return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    //     }

    //     userRepository.findById();

    //     return new ResponseEntity<String>(HttpStatus.OK);
    //  }

    private String createResponseWithToken(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();        

        map.put("token", JwtToken.createJWT(issuer, user));   
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

}
