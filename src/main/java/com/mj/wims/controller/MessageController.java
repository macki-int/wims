package com.mj.wims.controller;

import com.mj.wims.model.Message;
import com.mj.wims.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
