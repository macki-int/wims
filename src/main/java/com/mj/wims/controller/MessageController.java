package com.mj.wims.controller;

import com.mj.wims.model.Message;
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

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(messageRepository.findAll());
    }

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
