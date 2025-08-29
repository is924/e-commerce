package com.example.ecommercebackend.service.impl;

import com.example.ecommercebackend.dto.UserDTO;
import com.example.ecommercebackend.exception.custom.ResourceNotFoundException;
import com.example.ecommercebackend.model.User;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.service.UserService;
import com.example.ecommercebackend.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.image.upload.path}")
    private String userImageUploadPath;


    @Override
    public UserDTO fetchCurrentlyLoggedInUserDetails() {
        User loggedInUser = authUtil.loggedInUser();
        Optional<User> optionalUser = userRepository.findById(loggedInUser.getUserId());
        UserDTO dto = modelMapper.map(optionalUser.get(), UserDTO.class);
        if (dto.getProfileImage() != null && !dto.getProfileImage().isBlank() && !dto.getProfileImage().startsWith("http")) {
            dto.setProfileImage("http://localhost:8080/api/images/" + dto.getProfileImage());
        }
        if (dto.getProfileImage() == null || dto.getProfileImage().isBlank()) {
            dto.setProfileImage("https://via.placeholder.com/80");
        }
        return dto;
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDTO, Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format("User not found with ID: ", userId)));
        if (userDTO.getUserName() != null)
            existingUser.setUserName(userDTO.getUserName());

        if (userDTO.getEmail() != null)
            existingUser.setEmail(userDTO.getEmail());

        if (userDTO.getProfileImage() != null)
            existingUser.setProfileImage(userDTO.getProfileImage());

        if (userDTO.getActive() != null)
            existingUser.setActive(userDTO.getActive());

        // Ensure admins are always active
        boolean isAdmin = existingUser.getRoles().stream().anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));
        if (isAdmin) {
            existingUser.setActive(true);
        }

        User updatedUser = userRepository.save(existingUser);
        UserDTO dto = modelMapper.map(updatedUser, UserDTO.class);
        if (dto.getProfileImage() != null && !dto.getProfileImage().isBlank() && !dto.getProfileImage().startsWith("http")) {
            dto.setProfileImage("http://localhost:8080/api/images/" + dto.getProfileImage());
        }
        if (dto.getProfileImage() == null || dto.getProfileImage().isBlank()) {
            dto.setProfileImage("https://via.placeholder.com/80");
        }
        return dto;
    }

    @Override
    public java.util.List<UserDTO> listAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> {
                    UserDTO dto = modelMapper.map(u, UserDTO.class);
                    if (dto.getProfileImage() != null && !dto.getProfileImage().isBlank() && !dto.getProfileImage().startsWith("http")) {
                        dto.setProfileImage("http://localhost:8080/api/images/" + dto.getProfileImage());
                    }
                    if (dto.getProfileImage() == null || dto.getProfileImage().isBlank()) {
                        dto.setProfileImage("https://via.placeholder.com/40");
                    }
                    return dto;
                })
                .toList();
    }

    @Override
    public UserDTO setActiveStatus(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Prevent deactivating admins or deactivating self if admin
        boolean targetIsAdmin = user.getRoles().stream().anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));
        if (!active && targetIsAdmin) {
            throw new IllegalArgumentException("Administrators cannot be deactivated.");
        }

        User acting = authUtil.loggedInUser();
        boolean actingIsAdmin = acting.getRoles().stream().anyMatch(r -> r.getRoleName().name().equals("ROLE_ADMIN"));
        if (!active && actingIsAdmin && acting.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You cannot deactivate your own admin account.");
        }

        user.setActive(active);
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO uploadUserAvatar(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        try {
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(userImageUploadPath);
            java.nio.file.Files.createDirectories(uploadPath);
            String originalFilename = image.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String fileName = "avatar_" + user.getUserName() + "_" + System.currentTimeMillis() + ext;
            java.nio.file.Path filePath = uploadPath.resolve(fileName);
            java.nio.file.Files.copy(image.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            user.setProfileImage(fileName);
            User saved = userRepository.save(user);
            UserDTO dto = modelMapper.map(saved, UserDTO.class);
            dto.setProfileImage("http://localhost:8080/api/images/" + fileName);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading avatar", e);
        }
    }
}
