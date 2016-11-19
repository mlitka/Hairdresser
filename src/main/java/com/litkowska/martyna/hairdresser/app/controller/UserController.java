package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.security.models.JwtUser;
import com.litkowska.martyna.hairdresser.app.security.utilities.JwtToken;
import com.litkowska.martyna.hairdresser.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<?> getAllClients() {
        List<User> clients = (List<User>) userService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/logged", method = RequestMethod.GET)
    public ResponseEntity<?> getLoggedUser() {
        User user = userService.getCurrentLoggedUser();
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("no user logged", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "auth/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody final User user) {
        try {
            if (user.checkNotNull() && user.checkPassword()) {
                user.setPassword(encoder.encode(user.getPassword()));
                User registeredUser = userService.save(user);
                if (registeredUser != null) {
                    new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>("regitration failed", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "auth/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody final User user) {
        try {
            if (userService.checkLoginAttributes(user.getUsername(), user.getPassword())) {
                User authUser = userService.getUserByUsernameAndPassword(user.getUsername(),
                        encoder.encode(user.getPassword()));
                if (authUser != null) {
                    return new ResponseEntity<>(jwtToken.encode(new JwtUser(String.valueOf(authUser.getId()),
                            authUser.getRole())), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("No user matching given credentials found", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
