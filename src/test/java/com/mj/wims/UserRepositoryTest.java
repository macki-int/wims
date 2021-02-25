package com.mj.wims;

import com.mj.wims.model.RoleEnum;
import com.mj.wims.model.User;
import com.mj.wims.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUsername() {
        //BEFORE
//        User user = new User("adamn", "Adam", "Nowak", "test", RoleEnum.ROLE_ADMIN, true);
//        entityManager.persist(user);
//
//        //WHEN
//        Optional<User> userOptional = userRepository.findByUsername("adamn");
//
//        //THEN
//        assertThat(userOptional.get().getUsername()).isEqualTo("adamn");

    }
}
