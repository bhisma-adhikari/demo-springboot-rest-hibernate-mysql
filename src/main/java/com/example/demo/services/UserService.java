package com.example.demo.services;

import com.example.demo.dtos.UserDto;
import com.example.demo.dtotransformers.UserDtoTransformer;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDtoTransformer userDtoTransformer;


    /**
     * Returns a set of all users.
     * Returns an empty list if no user is found.
     */
    public List<UserEntity> findAllUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        userRepository.findAll().forEach(userEntities::add);
        return userEntities;
    }


    /**
     * Returns user if found, else returns null.
     */
    public UserEntity findUser(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId).orElse(null);
    }


    /**
     * Returns user if found, else returns null.
     */
    public UserEntity findUserByUsername(String username) {
        if (username == null) return null;
        List<UserEntity> userEntities = findAllUsers();
        for (UserEntity userEntity : userEntities) {
            if (userEntity.getLoginCredentialEntity().getUsername().equals(username)) {
                return userEntity;
            }
        }
        return null;
    }


    /**
     * Returns the new user added to the database.
     */
    public UserEntity addUser(UserDto userDto) {
        UserEntity userEntity = userDtoTransformer.transformToEntity(userDto);
        userEntity.getLoginCredentialEntity().setUserEntity(userEntity);
        return userRepository.save(userEntity);
    }


    /**
     * Returns the updated user entity in the database.
     */
    public UserEntity updateUser(UserDto userDto) {
        UserEntity userEntity = userDtoTransformer.transformToEntity(userDto);
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.getLoginCredentialEntity().setUsername(userDto.getLoginCredentialDto().getUsername());
        userEntity.getLoginCredentialEntity().setPassword(userDto.getLoginCredentialDto().getPassword());
        return userRepository.save(userEntity);
    }


    /**
     * Returns the updated user entity in the database.
     */
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    /**
     * Deletes the user with given id from the database.
     * If no such user exists, this method does nothing.
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
