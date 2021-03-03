package com.mj.wims.converter;

import com.mj.wims.dto.UserWithoutPasswordDTO;
import com.mj.wims.model.User;

public class UserToUserWithoutPasswordDTOConverter {

    public static UserWithoutPasswordDTO convert(User user) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = new UserWithoutPasswordDTO();

        userWithoutPasswordDTO.setUsername(user.getUsername());
        userWithoutPasswordDTO.setFirstName(user.getFirstName());
        userWithoutPasswordDTO.setLastName(user.getLastName());
        userWithoutPasswordDTO.setRole(user.getRole());
        userWithoutPasswordDTO.setActive(user.getActive());

        return  userWithoutPasswordDTO;
    }
}
