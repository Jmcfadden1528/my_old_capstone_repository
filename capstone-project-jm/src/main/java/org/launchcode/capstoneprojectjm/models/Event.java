package org.launchcode.capstoneprojectjm.models;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import static javax.print.attribute.standard.MediaPrintableArea.MM;


@Entity // Required for hibernate to store/get instances from database
public class Event {



    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    private Date date;

    @NotNull
    private Time time;

    @NotNull
    private String location;

    @NotNull
    private String description;


    public Event() { }



    public Event(String name, Date date, Time time, String location, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }


    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Date getDate() {return this.date;}

    public void setDate(Date date) {this.date = date;}

    public Time getTime() {return time;}

    public void setTime(Time time) {this.time = time;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

}
