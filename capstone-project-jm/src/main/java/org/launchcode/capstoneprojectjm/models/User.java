package org.launchcode.capstoneprojectjm.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity // Required for hibernate to store/get instances of a database
public class User {

    @Id
    @GeneratedValue
    private int id;
    String username;
    String firstname;
    String lastname;
    String password;
    String email;

    User() {}

    User(String username, String firstname, String lastname, String password, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getFirstname() {return firstname;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}




}
