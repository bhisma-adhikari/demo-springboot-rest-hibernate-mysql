package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"noteEntities"})
@ToString(callSuper = true, exclude = {"noteEntities"})

@Entity(name = "user")
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "login_credential_id", referencedColumnName = "id")
    private LoginCredentialEntity loginCredentialEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NoteEntity> noteEntities;


}
