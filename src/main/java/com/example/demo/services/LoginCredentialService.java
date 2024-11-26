package com.example.demo.services;

import com.example.demo.dtotransformers.LoginCredentialDtoTransformer;
import com.example.demo.entities.LoginCredentialEntity;
import com.example.demo.repositories.LoginCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginCredentialService {

    @Autowired
    LoginCredentialRepository loginCredentialRepository;

    @Autowired
    LoginCredentialDtoTransformer loginCredentialDtoTransformer;


    /**
     * Returns a list of all loginCredentials.
     * Returns an empty list if no loginCredential is found.
     */
    public List<LoginCredentialEntity> findAllLoginCredentials() {
        List<LoginCredentialEntity> loginCredentials = new ArrayList<>();
        loginCredentialRepository.findAll().forEach(loginCredentials::add);
        return loginCredentials;
    }


    /**
     * Returns loginCredentialEntity if found, else returns null.
     */
    public LoginCredentialEntity findLoginCredentialEntity(Long loginCredentialId) {
        if (loginCredentialId == null) return null;
        return loginCredentialRepository.findById(loginCredentialId).orElse(null);
    }

}
