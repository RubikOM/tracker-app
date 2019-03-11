package com.nixsolutions.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "author")
    private Set<DictionaryElement> usersElements;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<DictionaryElement> getUsersElements() {
        return usersElements;
    }

    public void setUsersElements(Set<DictionaryElement> usersElements) {
        this.usersElements = usersElements;
    }
}
