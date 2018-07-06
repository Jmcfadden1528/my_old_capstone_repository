package org.launchcode.capstoneprojectjm.models;

import javafx.util.converter.TimeStringConverter;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min=1, message="name cannot be left blank")
    private String name;

    @NotNull(message="Date cannot be left blank")

    private Date date;

    private Time time;

    @NotNull
    @Size(min=1, message="location cannot be left blank")
    private String location;

    @NotNull
    @Size(min=1, message="description cannot be left blank")
    private String description;

    @ManyToOne
    private User user;
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
