package com.mj.wims.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/server-versions")
public class ServerVersionController {

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping()
    public ResponseEntity<String> getServerVersionNumber(){
        return ResponseEntity.ok().body("0.0.2");
    }
}
