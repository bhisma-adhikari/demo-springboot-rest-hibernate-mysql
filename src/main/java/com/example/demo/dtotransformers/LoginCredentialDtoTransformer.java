package com.example.demo.dtotransformers;

import com.example.demo.dtos.LoginCredentialDto;
import com.example.demo.entities.LoginCredentialEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.LoginCredentialService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static com.example.demo.utils.MiscUtil.safelyGet;

@Component
public class LoginCredentialDtoTransformer {

    @Lazy
    @Autowired
    LoginCredentialService loginCredentialService;

    @Lazy
    @Autowired
    UserService userService;


    public LoginCredentialDto transformToDto(LoginCredentialEntity loginCredentialEntity) {
        if (loginCredentialEntity == null) return null;
        return LoginCredentialDto.builder()
                .id(loginCredentialEntity.getId())
                .username(loginCredentialEntity.getUsername())
                .password(loginCredentialEntity.getPassword())
                .userId(safelyGet(() -> loginCredentialEntity.getUserEntity().getId()))
                .build();
    }


    /**
     * Returns an entity version of the DTO.
     * <p>
     * If such entity already exists in the database,then the same entity is returned
     * with its fields updated, but not yet persisted into the database, per the DTO.
     * <p>
     * If such entity does not exist in the database,
     * then a new non-persistent entity is created and returned.
     */
    public LoginCredentialEntity transformToEntity(LoginCredentialDto loginCredentialDto) {
        UserEntity userEntity = userService.findUser(loginCredentialDto.getUserId());

        LoginCredentialEntity loginCredentialEntityDb = loginCredentialService.findLoginCredentialByUsername(loginCredentialDto.getUsername());
        if (loginCredentialEntityDb != null) {
            loginCredentialEntityDb.setPassword(loginCredentialDto.getPassword());
            return loginCredentialEntityDb;
        }

        return LoginCredentialEntity.builder()
                .id(loginCredentialDto.getId())
                .username(loginCredentialDto.getUsername())
                .password(loginCredentialDto.getPassword())
                .userEntity(userEntity)
                .build();
    }


}
