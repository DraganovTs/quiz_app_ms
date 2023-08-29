package com.homecode.service;

import com.homecode.model.User;
import com.homecode.model.dto.UserDTO;
import com.homecode.model.dto.UserRegisterDTO;
import com.homecode.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO register(UserRegisterDTO userRegisterDTO){
        User user = mapUserRegisterDTOToUser(userRegisterDTO);
        userRepository.save(user);
        UserDTO userDTO = mapUserToUserDTO(user);
        return userDTO;
    }

    private UserDTO mapUserToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    private User mapUserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        return  User.builder()
                .email(userRegisterDTO.getEmail())
                .password(userRegisterDTO.getPassword())
                .role("USER")
                .build();

    }


}
