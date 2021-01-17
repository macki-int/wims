package com.mj.wims.converter;


import com.mj.wims.dto.UserDTO;
import com.mj.wims.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDTOToUserConverter {

    public static User convert(UserDTO userDTO) {
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setActive(true);

        return user;
    }

}
