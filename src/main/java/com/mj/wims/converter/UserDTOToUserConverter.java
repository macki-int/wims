package com.mj.wims.converter;


import com.mj.wims.dto.UserDTO;
import com.mj.wims.model.User;

public class UserDTOToUserConverter {

    public static User convert(UserDTO userDTO) {
        User user = new User();

        user.setNick(userDTO.getNick());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setActive(true);

        return user;
    }

}
