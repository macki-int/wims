package com.mj.wims;

import com.mj.wims.converter.UserDTOToUserConverter;
import com.mj.wims.dto.UserDTO;
import com.mj.wims.model.RoleEnum;
import com.mj.wims.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDTOUnitTest {
    private UserDTO userDTO;
    private User user;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void init() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user = new User();
        userDTO = getSamplerUserDTO();
    }

    private UserDTO getSamplerUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("adamn");
        userDTO.setFirstName("Adam");
        userDTO.setLastName("Nowak");
        userDTO.setPassword("test"); //$2y$12$U2WZxQa/HBOwF2nWbFPeYuRHM.BWX.XXN3tlhg.Fhu4nzGk5cRqC. (BCrypt)
        userDTO.setRole(RoleEnum.ROLE_ADMIN);
        return userDTO;
    }

    @Test
    void whenConvertUserDTOtoUser() {
        //BEFORE

        //WHEN
        user = UserDTOToUserConverter.convert(userDTO);

        //THEN
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertNotEquals(userDTO.getPassword(), user.getPassword());
        assertTrue(bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()));
        assertEquals(userDTO.getRole(), user.getRole());
        assertEquals(true, user.getActive());
    }
}
