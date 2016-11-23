package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import com.litkowska.martyna.hairdresser.app.security.models.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Martyna on 13.11.2016.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format(
                    "There is no user with username: %s", username));
        }
        return user;
    }

    @Transactional
    public User save(final User user) {
        if (checkUniqueConstraints(user)) {
            user.setUsername(user.getEmail());
            return userRepository.save(user);
        }
        return null;
    }

    public User saveWithoutRegistering(final User user) {
        if (user.checkNotNull()) {
            return userRepository.save(user);
        }
        return null;
    }

    public User findOne(final long id) {
        return userRepository.findOne(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void delete(long id) {
//        checkIfAuthorized(username);
        checkIfUserExists(id);
        userRepository.delete(id);
    }

    private void checkIfAuthorized(final String username) {
        if (!isAuthorized(username))
            throw new InsufficientAuthenticationException(String.format("You have no rights to manage user with username: %s", username));
    }

    private boolean checkIfUserExists(final long id) {
        return userRepository.exists(id);
    }

    private boolean checkUniqueConstraints(final User user) {
        User existingUser = userRepository.findByUsername(user.getEmail());
        return existingUser == null;
    }

    public AuthenticatedUser getAuthLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof AuthenticatedUser ? (AuthenticatedUser) principal : null;
    }

    public User getCurrentLoggedUser() {
        AuthenticatedUser authUser = getAuthLoggedUser();
        if (authUser != null) {
            return findByUsername(authUser.getUsername());
        }
        return null;
    }

    public Boolean isAuthorized(final String username) {
        return getCurrentLoggedUser().getUsername() == username;
    }

    public User getUserByUsernameAndPassword(final String username, final String password) {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if(encoder.matches(password, user.getPassword())){
            System.out.println("pass ok");
            return user;
        }
        return null;
    }

    public boolean checkLoginAttributes(final String username, final String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

}
