package com.mj.wims.controller;

import com.mj.wims.model.Message;
import com.mj.wims.model.Reservation;
import com.mj.wims.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/messages")
public class MessageController {
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(messageRepository.findAll());
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);

        if (messageOptional.isPresent()) {
            return ResponseEntity.ok().body(messageOptional);
        }
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping()
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            messageRepository.save(message);
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Object did not create");
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping()
    public ResponseEntity<?> updateMessage(@RequestBody Message message) {
        if (messageRepository.existsById(message.getId())) {
            try {
                messageRepository.save(message);
                return ResponseEntity.ok().body(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PatchMapping("read/{id}")
    public ResponseEntity<?> setMessageReadById(@PathVariable Long id){
        Optional<Message> messageOptional = messageRepository.findById(id);

        if (messageOptional.isPresent()) {
            try {
                messageRepository.setMessageReadById(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.badRequest().body("Message did not set as read");
        }

        return ResponseEntity.badRequest().body("Object did not found");
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);

        if (messageOptional.isPresent()) {
            try {
                messageRepository.delete(messageOptional.get());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.badRequest().body("Object did not delete");
        }

        return ResponseEntity.badRequest().body("Object did not found");
    }
}
