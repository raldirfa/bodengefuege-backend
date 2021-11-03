package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private String issuer = "de.hsosnabrueck.bodengefuege";

    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> inputData) {
        if(inputData == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("email") || !jsonObject.has("password")){
            return null;
        }
        User user = isUserValid(jsonObject.getString("email"),jsonObject.getString("password"));
        if(user == null){
            return null;
        }

        return createResponseJson(user);
    }

    @PostMapping("/registration")
    public String registration(@RequestBody Map<String, Object> inputData) {
        if(inputData == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject(inputData);
        if(!jsonObject.has("email") || !jsonObject.has("password") || !jsonObject.has("username")){
            return null;
        }
        User user = new User(jsonObject.getString("username"), jsonObject.getString("email"), jsonObject.getString("password"));
        userRepository.save(user);

        return createResponseJson(user);
        
    }

    private String createResponseJson(User user){
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("jwt", JwtToken.createJWT(issuer, user));
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("settings", user.getSetting());

        String json = gson.toJson(map);
        return json;
    }

    private User isUserValid(String email, String password){
        for (User u : userRepository.findAll()) {
            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }


    @RequestMapping("/handle")
    public ResponseEntity<String> handle() {
    
    HttpHeaders headers = new HttpHeaders();
    headers.set("MyResponseHeader", "MyValue");
    return new ResponseEntity<String>("Hello World", headers, HttpStatus.CREATED);
}
}
