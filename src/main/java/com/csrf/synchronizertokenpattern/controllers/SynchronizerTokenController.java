package com.csrf.synchronizertokenpattern.controllers;

import com.csrf.synchronizertokenpattern.services.SynchronizerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SynchronizerTokenController
{
    // Create an object of the service class
    private SynchronizerTokenService synchronizerTokenService;

    @Autowired
    SynchronizerTokenController(SynchronizerTokenService synchronizerTokenService)
    {
        this.synchronizerTokenService = synchronizerTokenService;
    }

    @PostMapping("/login")
    public boolean loginUser(@RequestParam String email, @RequestParam String password, HttpServletResponse response)
    {
        // Create cookie if login credentials are valid and return true
        if (this.synchronizerTokenService.loginUser(email,password))
        {
            // Generate sessionId
            String sessionId = this.synchronizerTokenService.generateUniqueId();

            // Store sessionId in cookie in the browser
            Cookie cookie = new Cookie("sessionId",sessionId);
            response.addCookie(cookie);

            // Generate csrf token
            String csrfToken = this.synchronizerTokenService.generateUniqueId();
            System.out.println("csrf token: " + csrfToken);

            // Save values to hashmap
            this.synchronizerTokenService.saveToHashMap(email, password, sessionId, csrfToken);
            return true;
        }

        // Else return false
        return false;


    }
}
