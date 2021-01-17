package com.mj.wims.converter;


import com.mj.wims.dto.UserDTO;
import com.mj.wims.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDTOToUserConverter {

    public static User convert(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setActive(true);

        return user;
    }

}
