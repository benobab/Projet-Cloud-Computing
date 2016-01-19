package model;

import com.google.appengine.api.datastore.Entity;

import java.io.Serializable;

/**
 * Created by Benobab on 19/01/16.
 */
public class Exercise implements Serializable {
    private String title;
    private String description;
    private String duration;


    public Exercise() {
    }

    public Entity toEntity(){
        Entity exercice = new Entity("Exercise");
        exercice.setProperty("title",this.title);
        exercice.setProperty("description",this.description);
        exercice.setProperty("duration",this.duration);
        return exercice;
    }

    public Exercise(String title, String description, String duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
