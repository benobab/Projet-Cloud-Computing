package model;

import com.google.appengine.api.datastore.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benobab on 19/01/16.
 */
public class TrainingPlan implements Serializable {
    private String title;
    private String description;
    private String domain;
    private List<Exercise> exercises;

    public TrainingPlan() {
    }

    public TrainingPlan(String title, String description, String domain, List<Exercise> exercises) {
        this.title = title;
        this.description = description;
        this.domain = domain;
        this.exercises = exercises;
    }

    public Entity toEntity(){
        Entity trainingPlan = new Entity("TrainingPlan");
        trainingPlan.setProperty("title",this.title);
        trainingPlan.setProperty("description",this.description);
        trainingPlan.setProperty("domain",this.domain);
        List<Entity> entities = new ArrayList<>();
        for (Exercise e: this.exercises) {
            entities.add(e.toEntity());
        }
        trainingPlan.setProperty("exercices",entities);
        return trainingPlan;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
