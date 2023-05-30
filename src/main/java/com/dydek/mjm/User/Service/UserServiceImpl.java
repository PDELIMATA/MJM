package com.dydek.mjm.User.Service;

import com.dydek.mjm.User.Entity.User;
import com.dydek.mjm.User.Repository.UserRepository;
import com.dydek.mjm.User.Role;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewUser(String username, String nonEncodedPassword) {
        var password = passwordEncoder.encode(nonEncodedPassword);
        var user = new User(username, password, Role.USER.toString());
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        return new User(user.getUsername(), user.getPassword(), user.getRole());
    }

    @PostConstruct
    public void sampleData() {
        createNewUser("john", "password");
    }
}
