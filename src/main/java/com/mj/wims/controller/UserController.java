package com.mj.wims.controller;

import com.mj.wims.WimsApplication;
import com.mj.wims.converter.UserDTOToUserConverter;
import com.mj.wims.converter.UserToUserWithoutPasswordDTOConverter;
import com.mj.wims.dto.PasswordDTO;
import com.mj.wims.dto.UserDTO;
import com.mj.wims.dto.UserWithoutPasswordDTO;
import com.mj.wims.model.User;
import com.mj.wims.model.UserCredentials;
import com.mj.wims.repository.UserRepository;
import com.mj.wims.service.AuthenticationService;
import com.mj.wims.service.PasswordCompareServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private static final Logger LOGGER = LogManager.getLogger(WimsApplication.class);

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Get all users");
        return ResponseEntity.ok().body(userRepository.findAll(Sort.by(Sort.Direction.ASC, "username")));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            LOGGER.info("Got user by id: " + id);
            //TODO add return Users without passwords
            return ResponseEntity.ok().body(userOptional);
        }

        LOGGER.error("No find user by id: " + id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/logged/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            UserWithoutPasswordDTO userWithoutPasswordDTO = UserToUserWithoutPasswordDTOConverter.convert(userOptional.get());

            LOGGER.info("Got user by name: " + username);
            return ResponseEntity.ok().body(userWithoutPasswordDTO);
        }

        LOGGER.error("No found user by name: " + username);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());

        if (userOptional.isPresent()) {
            LOGGER.error("User exist: " + userDTO);
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = UserDTOToUserConverter.convert(userDTO);

        try {
            userRepository.save(user);
            LOGGER.info("Created user: " + userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            e.printStackTrace();
        }

        LOGGER.error("No created user: " + userDTO);
        return ResponseEntity.badRequest().body("Object did not create");
    }

    @PostMapping("/login")
    public void login(@RequestBody UserCredentials userCredentials) {
        LOGGER.info("Login user: " + userCredentials);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        //TODO change User on UserDTO
        if (userRepository.existsById(user.getId())) {
            Optional<User> userOptional = userRepository.findById(user.getId());
            try {
                userRepository.save(user);
                LOGGER.info("Updated user: " + user);
                return ResponseEntity.ok().body(user);
            } catch (Exception e) {
                LOGGER.error(e.getStackTrace());
                e.printStackTrace();
            }
        }

        LOGGER.error("No found user for update: " + user);
        return ResponseEntity.notFound().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PatchMapping("/password")
    public ResponseEntity<?> changeUserPassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDTO) {
        Authentication authentication = AuthenticationService.getAuthentication(request);
        String userName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            PasswordCompareServiceImpl passwordCompareServiceImpl = new PasswordCompareServiceImpl();
            Boolean equalPassword = passwordCompareServiceImpl.comparePassword(user.getPassword(), passwordDTO.getOldPassword());

            if (equalPassword) {
                user.setPassword(new BCryptPasswordEncoder().encode(passwordDTO.getNewPassword()));
                //TODO try-catch
                userRepository.save(user);
                LOGGER.info("Changed user password: " + passwordDTO);
                return ResponseEntity.ok().build();
            }
            LOGGER.error("No changed user password - no equal: " + passwordDTO);
            return ResponseEntity.badRequest().build();
        }

        LOGGER.error("No found user for change password: " + passwordDTO);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/password/{id}")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long id, @RequestBody Map<String, String> newPassword) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

//            PasswordCompareServiceImpl passwordCompareServiceImpl = new PasswordCompareServiceImpl();
//            Boolean equalPassword = passwordCompareServiceImpl.comparePassword(user.getPassword(), passwordDTO.getOldPassword());

            if (newPassword.size() > 0) {
                user.setPassword(new BCryptPasswordEncoder().encode(newPassword.get("newPassword")));
                //TODO try-catch
                userRepository.save(user);
                LOGGER.info("Reset user passowr");
                return ResponseEntity.ok().build();
            }
            LOGGER.error("No reset user password");
            return ResponseEntity.badRequest().build();
        }

        LOGGER.error("No found user by id for reset paswword: " + id);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(true);
            //TODO try-catch
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            //TODO try-catch
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }

        return ResponseEntity.noContent().build();
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            try {
                userRepository.delete(userOptional.get());
                LOGGER.info("Deleted user by id: " + id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                LOGGER.error(e.getStackTrace());
                e.printStackTrace();
            }

            LOGGER.error("No deleted user by id: " + id);
            return ResponseEntity.badRequest().body("Object did not delete");
        }

        LOGGER.error("No found user by id for delete: " + id);
        return ResponseEntity.badRequest().build();
    }
}
