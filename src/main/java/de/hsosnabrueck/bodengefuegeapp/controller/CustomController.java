package de.hsosnabrueck.bodengefuegeapp.controller;

import de.hsosnabrueck.bodengefuegeapp.model.Field;
import de.hsosnabrueck.bodengefuegeapp.model.User;
import de.hsosnabrueck.bodengefuegeapp.repository.FieldRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Todo Delete

@RestController
public class CustomController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FieldRepository fieldRepository;


    // @GetMapping
    // public void test() {
    //     User user = new User("name", "pass");
    //     Field field = new Field(user);
    //     Field field2 = new Field(user);
    //     fieldRepository.save(field);
    //     fieldRepository.save(field2);
    // }

    // @GetMapping("/test")
    // public void users() {
        
    // }
}
