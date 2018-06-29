package org.launchcode.capstoneprojectjm.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // Required for hibernate to store/get instances from database
public class Category {

    @Id
    @GeneratedValue
    private int id;

    public Category() {};
    public Category(int id) {
        this.id = id;
    }
}
