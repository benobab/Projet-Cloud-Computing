package model;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String title;
    private String description;
    private String duration;
    private long id;
    private long trainingPlanId;

    public Exercise() {
    }

    public Entity toEntity(Key trainingPlanKey){
        Entity exercice = new Entity("Exercise", trainingPlanKey);
        exercice.setProperty("title",this.title);
        exercice.setProperty("description",this.description);
        exercice.setProperty("duration",this.duration);
        return exercice;
    }

    public Exercise(String title, String description, String duration, long trainingPlanId) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.trainingPlanId = trainingPlanId;
    }

    public static Exercise toExercice(Entity e){
        Exercise exercise = new Exercise((String)e.getProperty("title"),(String)e.getProperty("description"),(String)e.getProperty("duration"), e.getParent().getId());
        return exercise;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
