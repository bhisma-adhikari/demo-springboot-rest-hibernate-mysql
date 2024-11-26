package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = "noteEntities")
@ToString(callSuper = true, exclude = "noteEntities")
@Entity(name = "genre")
public class GenreEntity extends BaseEntity {

    private String type;

    @ManyToMany(mappedBy = "genreEntities")
    private Set<NoteEntity> noteEntities;


}
