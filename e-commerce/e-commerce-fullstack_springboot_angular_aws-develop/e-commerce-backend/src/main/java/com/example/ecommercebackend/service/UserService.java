package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.UserDTO;

public interface UserService {
    UserDTO fetchCurrentlyLoggedInUserDetails();

    UserDTO updateUserDetails(UserDTO userDTO, Long userId);

    java.util.List<UserDTO> listAllUsers();

    UserDTO setActiveStatus(Long userId, boolean active);

    void deleteUser(Long userId);

    UserDTO uploadUserAvatar(Long userId, org.springframework.web.multipart.MultipartFile image);
}
