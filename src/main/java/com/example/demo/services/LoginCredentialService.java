package com.example.demo.services;

import com.example.demo.dtotransformers.LoginCredentialDtoTransformer;
import com.example.demo.entities.LoginCredentialEntity;
import com.example.demo.repositories.LoginCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginCredentialService {

    @Autowired
    LoginCredentialRepository loginCredentialRepository;

    @Autowired
    LoginCredentialDtoTransformer loginCredentialDtoTransformer;


    /**
     * Returns loginCredentialEntity if found, else returns null.
     */
    public LoginCredentialEntity findLoginCredentialEntity(Long loginCredentialId) {
        if (loginCredentialId == null) return null;
        return loginCredentialRepository.findById(loginCredentialId).orElse(null);
    }

}
