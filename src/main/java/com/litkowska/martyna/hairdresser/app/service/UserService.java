package com.litkowska.martyna.hairdresser.app.service;

import com.litkowska.martyna.hairdresser.app.model.User;
import com.litkowska.martyna.hairdresser.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Martyna on 13.11.2016.
 */
@Service
public class UserService implements UserDetailsService {

    @Value("${com.litkowska.martyna.hairdresser.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        System.out.println("\n\nloadByUsername\n");
        System.out.println(userDetails);
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
        System.out.println(encoder.matches("martyna",userDetails.getPassword()));
        System.out.println(userDetails.getPassword());

        if (userDetails == null) {
            throw new UsernameNotFoundException(String.format(
                    "There is no user with username: %s", username));
        }
        return userDetails;
    }

    @Transactional
    public User save(final User user) {
        if (checkIfUserJustReservedAVisit(user.getEmail())) {
            User userToSave = userRepository.findByEmail(user.getEmail());
            userToSave.setPassword(user.getPassword());
            userToSave.setLastName(user.getLastName());
            userToSave.setFirstName(user.getFirstName());
            if (user.getPhoneNo() != null) {
                userToSave.setPhoneNo(user.getPhoneNo());
            }
            encryptPassword(userToSave);
            return userRepository.save(userToSave);
        } else if (checkUniqueConstraints(user)) {
            encryptPassword(user);
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
        checkIfAuthorized(id);
        checkIfUserExists(id);
        userRepository.delete(id);
    }

    private void encryptPassword(final User user) {
        StandardPasswordEncoder encoder = new StandardPasswordEncoder(salt);
        String encodedPassword = encoder.encode(user.getPassword());
        System.out.println(encodedPassword);
        System.out.println(encoder.encode("martyna"));
        user.setPassword(encodedPassword);
    }


    private void checkIfAuthorized(final long id) {
        if (!isAuthorized(id))
            throw new InsufficientAuthenticationException(String.format("You have no rights to manage user with id: %s", id));
    }

    private boolean checkIfUserExists(final long id) {
        return userRepository.exists(id);
    }

    private boolean checkUniqueConstraints(final User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        return existingUser == null;
    }

    public boolean checkNotNullConstraints(final User user){
        return user.getEmail()!=null && user.getPassword()!=null;
    }

    /**
     * Method checks if user's email is already existing in our database because User might have reserved a visit in
     * the past but without registration
     *
     * @param email
     * @return
     */
    private boolean checkIfUserJustReservedAVisit(final String email) {
        User existingEmail = userRepository.findByEmail(email);
        return existingEmail != null && existingEmail.getPassword() == null;
    }

    public User getCurrentLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal==null);
        System.out.println(principal);
        return principal instanceof User ? (User) principal : null;
    }

    public Boolean isAuthorized(long id) {
        return getCurrentLoggedUser().getId() == id;
    }

    public User getUserByUsernameAndPassword(final String username, final String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

}
