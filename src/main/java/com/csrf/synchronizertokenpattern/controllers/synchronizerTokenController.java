package com.csrf.synchronizertokenpattern.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class synchronizerTokenController
{
    @PostMapping("/login")
    public boolean loginUser()
    {
        return  true;
    }
}
