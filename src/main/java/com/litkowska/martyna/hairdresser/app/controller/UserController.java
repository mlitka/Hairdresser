package com.litkowska.martyna.hairdresser.app.controller;

import com.litkowska.martyna.hairdresser.app.dto.UserDTO;
import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.security.models.JwtUser;
import com.litkowska.martyna.hairdresser.app.security.utilities.JwtToken;
import com.litkowska.martyna.hairdresser.app.service.ClientService;
import com.litkowska.martyna.hairdresser.app.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Martyna on 21.09.2016.
 */
@RestController
@CrossOrigin
public class UserController {

    @Value("${com.litkowska.martyna.hairdresser.salt}")
    private String salt;

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtToken jwtToken;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getAllClients() {
        List<User> clients = (List<User>) userService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/users/check", method = RequestMethod.GET)
    public ResponseEntity<?> checkUser(@RequestParam ("username") String username) {
        User user = userService.findByUsername(username);
        System.out.println(username);
        System.out.println("chceking");
        System.out.println(user);
        if (user != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);

        }
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/auth/user/logged", method = RequestMethod.GET)
    public ResponseEntity<?> getLoggedUser(final HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token!=null) {
            token = token.substring(7);
            JwtUser jwtUser = jwtToken.decode(token);
            if (jwtUser != null) {
                User user = userService.findOne(Long.parseLong(jwtUser.getUserId()));
                UserDTO userDTO = new UserDTO(user);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>("no user logged", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("did not find auth token", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody final User user) {
        try {
            if (user.checkNotNull() && user.checkPassword()) {
                System.out.println("\n\nregistering\n\n");
                user.setPassword(encoder.encode(user.getPassword()));
                User registeredUser = userService.save(user);
                if (registeredUser != null) {
                    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>("regitration failed", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody final User user) {
        try {
            if (userService.checkLoginAttributes(user.getUsername(), user.getPassword())) {
                System.out.println("\nLOGIN");
                User authUser = userService.getUserByUsernameAndPassword(user.getUsername(),
                        user.getPassword());
                if (authUser != null) {
                    System.out.println(authUser);
                    return new ResponseEntity<>(jwtToken.encode(new JwtUser(String.valueOf(authUser.getId()),
                            authUser.getRole())), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("No user matching given credentials found", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logoutUser() {
        try {
            System.out.println("logging out");
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>("logged out user", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
