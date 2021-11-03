package de.hsosnabrueck.bodengefuegeapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class FieldController {

    @GetMapping("/fields")
    public String fields(@RequestHeader String jwtToken) {




        return "fields";
    }
}
