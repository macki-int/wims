package com.mj.wims.controller;

import com.mj.wims.converter.UserDTOToUserConverter;
import com.mj.wims.dto.LoginDTO;
import com.mj.wims.dto.UserDTO;
import com.mj.wims.model.User;
import com.mj.wims.model.UserCredentials;
import com.mj.wims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok().body(userRepository.findAll(Sort.by(Sort.Direction.ASC, "username")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username")
    public ResponseEntity<?> findByNick(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());

        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = UserDTOToUserConverter.convert(userDTO);

        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Object did not create");
    }

    @PostMapping("/login")
    public void login(@RequestBody UserCredentials userCredentials){
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        if(userRepository.existsById(user.getId())){
            Optional<User> userOptional = userRepository.findById(user.getId());
            //TODO passwordEncoder
            try {
                userRepository.save(user);
                return ResponseEntity.ok().body(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(true);
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()){
            try {
                userRepository.delete(userOptional.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().build();
    }
}
