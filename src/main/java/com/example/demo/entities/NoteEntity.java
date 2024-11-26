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
@EqualsAndHashCode(callSuper = true, exclude = "userEntity")
@ToString(callSuper = true, exclude = {"userEntity"})
@Entity(name = "note")
public class NoteEntity extends BaseEntity {

    private String text;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "note_genre_mapping",
            joinColumns = {@JoinColumn(name = "note_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<GenreEntity> genreEntities;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;


}
