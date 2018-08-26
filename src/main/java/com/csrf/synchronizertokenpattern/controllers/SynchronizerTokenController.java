package com.csrf.synchronizertokenpattern.controllers;

import com.csrf.synchronizertokenpattern.services.SynchronizerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SynchronizerTokenController
{
    // Create an object of the service class
    private SynchronizerTokenService synchronizerTokenService;

    private HashMap<String, String> hashMap;

    @Autowired
    SynchronizerTokenController(SynchronizerTokenService synchronizerTokenService)
    {
        this.synchronizerTokenService = synchronizerTokenService;
    }

    @PostMapping("/login")
    public RedirectView loginUser(@RequestParam String email, @RequestParam String password, HttpServletResponse response)
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

            // Save values to hashmap
            hashMap = this.synchronizerTokenService.saveToHashMap(email, password, sessionId, csrfToken);

            // Redirect to new page
            return new RedirectView("/form.html");

        }

        // Else redirect to same page with error message false
        return new RedirectView("/?loginStatus=invalid");

    }

    @PostMapping("/csrfToken")
    public String sendCsrfToken(@RequestParam String sessionId)
    {
        for(Map.Entry m:hashMap.entrySet())
        {
            System.out.println(m.getKey()+" "+m.getValue());
        }

        String sessionIdFromHashMap = hashMap.get("sessionId");

        // Return csrf token if the session id is valid
        if(sessionId.equals(sessionIdFromHashMap))
        {
            return hashMap.get("csrfToken");
        }

        return null;
    }

    @PostMapping("/checkCsrfTokenValidity")
    public boolean checkCsrfTokenValidity(@RequestParam String sessionId, @RequestParam String csrfTokenFromClient)
    {
        String sessionIdFromHashMap = hashMap.get("sessionId");

        // Check if the session is valid
        if(sessionId.equals(sessionIdFromHashMap))
        {
            String csrfTokenFromHashMap = hashMap.get("csrfToken");

            // Return true if the 2 csrf tokens match
            return csrfTokenFromClient.equals(csrfTokenFromHashMap);
        }

        // Return false if the 2 csrf tokens do not match
        return false;
    }
}
