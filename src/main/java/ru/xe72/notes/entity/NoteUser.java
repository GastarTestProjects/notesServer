package ru.xe72.notes.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class NoteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String email;
//    private Collection<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Collection<String> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<String> roles) {
//        this.roles = roles;
//    }
}
