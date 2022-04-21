package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public User findById(Long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.orElse(new User());
    }

    public void save(User user) {
        User userBD = userRepository.findByUsername(user.getUsername());
        if (userBD != null) {
            return;
        }
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singleton(role));
        user.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
        userRepository.save(user);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    public void  update(User user) {
        userRepository.update(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()), user.getId());

    }

    public User findByName(String username) {
        return userRepository.findByUsername(username);
    }
}