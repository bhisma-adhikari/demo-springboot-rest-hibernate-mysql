package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "userEntity")
@ToString(callSuper = true, exclude = "userEntity")
@Entity(name = "login_credential")
public class LoginCredentialEntity extends BaseEntity {

    private String username;
    private String password;

    @OneToOne(mappedBy = "loginCredentialEntity")
    private UserEntity userEntity;


}
