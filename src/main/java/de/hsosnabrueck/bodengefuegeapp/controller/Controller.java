package de.hsosnabrueck.bodengefuegeapp.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {

    @PostMapping("/signIn")
    public String signIn(@RequestBody String requestBody) {        
        
        return "jwtToken";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody String requestBody) {        
        
        return "jwtToken";
    }


}
