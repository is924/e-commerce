package online_marketplace.controller;

import online_marketplace.model.User;
import online_marketplace.repository.UserRepository;
import online_marketplace.dto.request.UserRequest;
import online_marketplace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserRequest updatedUserRequest) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUserRequest.getUsername());
            user.setEmail(updatedUserRequest.getEmail());
            user.setPassword(updatedUserRequest.getPassword());
            try {
                user.setAccountType(User.AccountType.valueOf(updatedUserRequest.getAccountType().toUpperCase()));
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid account type: " + updatedUserRequest.getAccountType());
            }
            return userRepository.save(user);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
