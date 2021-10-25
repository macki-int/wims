package com.mj.wims.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.wims.model.RoleEnum;
import com.mj.wims.model.User;
import com.mj.wims.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(UserController.class)
public class UserControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserRepository userRepository;


    User USER_1 = new User("adamk", "Adam", "Kowalski", "1234", RoleEnum.ROLE_ADMIN, true);

    @Test
    public void findAllUserTest() throws Exception {
        List<User> userList = new ArrayList<>(Arrays.asList(USER_1));

        Mockito.when(userRepository.findAll()).thenReturn(userList);
//https://stackabuse.com/guide-to-unit-testing-spring-boot-rest-apis/
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Jane Doe")));
    }

}
