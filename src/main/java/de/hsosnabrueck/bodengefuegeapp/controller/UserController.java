package de.hsosnabrueck.bodengefuegeapp.controller;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.hsosnabrueck.bodengefuegeapp.model.JwtToken;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;


@RestController
public class UserController {

    String issuer = "de.hsosnabrueck.bodengefuege";

    @Autowired
    UserRepository userRepository;    

    @PostMapping("/signIn")
    public String signIn(@RequestBody User user) {    

        if(user != null && isUserValid(user)){
            return JwtToken.createJWT(issuer, user);
        }

        return null;
    }

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        
        if(user != null && user.getEmail() != null && user.getPassword() != null){
            userRepository.save(user);  
            return JwtToken.createJWT(issuer, user);
        } 

        return null;
    }

    @GetMapping("/test")
    public String test(){
        User user = new User("TestName","TestPassword");
        Gson gson = new Gson();
        String jsonInString = gson.toJson(user);

        System.out.println(jsonInString);

        userRepository.save(user);
        
        if(isUserValid(user)){
            System.out.println("User existiert");
        }

        return JwtToken.createJWT("de.hsosnabrueck.bodengefuege", user);
    }

    private boolean isUserValid(User user){
        for (User u : userRepository.findAll()) {
            if(u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }
}
