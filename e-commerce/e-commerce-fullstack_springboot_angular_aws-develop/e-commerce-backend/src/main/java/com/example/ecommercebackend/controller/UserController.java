package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.UserDTO;
import com.example.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> fetchCurrentlyLoggedInUserDetails() {
        UserDTO userDTO = userService.fetchCurrentlyLoggedInUserDetails();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserInfo(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
        UserDTO updatedUserDTO = userService.updateUserDetails(userDTO, userId);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    // Upload avatar; service will store file and set filename in profileImage
    @PutMapping(value = "/{userId}/avatar", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDTO> uploadAvatar(@PathVariable Long userId, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(userService.uploadUserAvatar(userId, image));
    }

    // Admin: list all users
    @GetMapping("/all")
    public ResponseEntity<java.util.List<UserDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.listAllUsers());
    }

    // Admin: activate/deactivate
    @PutMapping("/{userId}/active")
    public ResponseEntity<UserDTO> setActive(@PathVariable Long userId, @RequestParam boolean active) {
        return ResponseEntity.ok(userService.setActiveStatus(userId, active));
    }

    // Admin: delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
