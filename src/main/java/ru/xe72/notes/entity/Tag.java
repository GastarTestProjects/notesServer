package ru.xe72.notes.entity;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Entity
@Table(name = "tags"
//        ,indexes = @Index(columnList = "name", unique = true)
//        ,uniqueConstraints = @UniqueConstraint(columnNames = {"name"}) // Наверно не нужен, раз в индексе указано
)
@Indexed
public class Tag {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Id
    @Field(name = "nameSearch")
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    // Почему-то апдейте не помогает. Пишет что нарушено ограничение на уникальность
    // Перед сохранением в БД приводим к нижнему регистру
//    @PrePersist
//    @PreUpdate
//    void nameToLower() {
//        this.name = (this.name == null ? null : this.name.toLowerCase());
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
