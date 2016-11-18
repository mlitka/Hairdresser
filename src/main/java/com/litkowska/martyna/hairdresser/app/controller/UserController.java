package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.security.utilities.JwtToken;
import com.litkowska.martyna.hairdresser.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtToken jwtToken;


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @CrossOrigin("*")
    public ResponseEntity<?> getAllClients(){
        List<User> clients = (List<User>) userService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Validated
    @RequestMapping(value = "/security/logged", method = RequestMethod.GET)
    public User getLoggedUser() {
        return (User) userService.getCurrentLoggedUser();
    }

    @Validated
    @RequestMapping(value = "/user/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody final User user) {
        return userService.save(user);
    }
}
