package com.mj.wims.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/server-versions")
public class ServerVersionController {

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping()
    public ResponseEntity<String> getServerVersionNumber() throws IOException {
        final Properties properties = new Properties();
        properties.load(this.getClass(). getClassLoader().getResourceAsStream("application.properties"));

        return ResponseEntity.ok().body(properties.getProperty("appversion"));
    }
}
