package com.jjgr.store_demo.user;

import java.util.List;
import java.util.Optional;

import com.jjgr.store_demo.exceptions.ApiRequestException;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET all
    public List<UserEntity> getUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new ApiRequestException("No users found");
        }
        return userRepository.findAll();
    }

    // POST
    public void addUser(UserEntity user) {
        Optional<UserEntity> userIsPresent = userRepository.findByEmail(user.getEmail());

        if (userIsPresent.isPresent()) {
            throw new IllegalStateException("Email is already taken");
        }

        userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Long id) {
        boolean userExists = userRepository.existsById(id);
        if (!userExists) {
            throw new IllegalStateException("User with id " + id + " does not exist");
        }
        userRepository.deleteById(id);
    }
}
