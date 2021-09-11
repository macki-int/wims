package com.mj.wims.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleEnumTest {

    @Test
    void whenRoleEnumAdmin() {
        //BEFORE
        RoleEnum roleEnum;

        //WHEN
        roleEnum = RoleEnum.ROLE_ADMIN;

        //THEN
        assertEquals(roleEnum.ordinal(), 0);
    }
 
}