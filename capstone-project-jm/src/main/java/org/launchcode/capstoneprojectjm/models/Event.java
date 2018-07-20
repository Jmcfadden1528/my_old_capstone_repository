package org.launchcode.capstoneprojectjm.models;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.util.List;


@Entity // Required for hibernate to store/get instances from database
public class Event {


    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, message = "name cannot be left blank")
    private String name;

    @NotNull(message = "Date cannot be left blank")
    private Date date;

    private Time time;
    //TODO: delete event once date is past.
    @NotNull
    @Size(min = 1, message = "location cannot be left blank")
    private String location;

    @NotNull
    @Size(min = 1, message = "description cannot be left blank")
    private String description;

    private String imageUrl;


    private double latitude;

    private double longitude;


    @ManyToMany(mappedBy = "events")
//    @Cascade(CascadeType.DELETE)
    private List<User> users;

    public Event() {
    }


    public Event(String name, Date date, Time time, String location, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void clearUsers() {
        List<User> users = this.getUsers();
        while (users.size() > 1) {
            for (User u : users) {
                u.removeFromEvent(this);

            }

        }


    }
}
