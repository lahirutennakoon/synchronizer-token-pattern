package com.csrf.synchronizertokenpattern.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class SynchronizerTokenService
{
    public boolean loginUser(String email, String password)
    {
        System.out.println("Email: " + email);
        System.out.println("password: " + password);
        System.out.println("logged in");

        // Return true if login credentials are valid else return false
        return (email.equals("admin@gmail.com")) && (password.equals("admin"));


    }

    // Generate a unique random value
    public String generateUniqueId()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public HashMap saveToHashMap(String email, String password, String sessionId, String csrfToken)
    {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("email", email);
        hashMap.put("password", password);
        hashMap.put("sessionId", sessionId);
        hashMap.put("csrfToken", csrfToken);

        for(Map.Entry m:hashMap.entrySet())
        {
            System.out.println(m.getKey()+" "+m.getValue());
        }
        return hashMap;
    }
}
