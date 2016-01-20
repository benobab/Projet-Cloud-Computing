package model;

import com.google.appengine.api.datastore.*;
import training.UTIL;

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
    private String email;

    public TrainingPlan() {
    }

    public TrainingPlan(String title, String description, String domain, List<Exercise> exercises, String email) {
        this.title = title;
        this.description = description;
        this.domain = domain;
        this.exercises = exercises;
        this.email = email;
    }

    public Entity toEntity(){
        //Key k = KeyFactory.Builder("trainingPlan","trainingPlan").addChild();
        Entity trainingPlan = new Entity("TrainingPlan");
        DatastoreService datastoreService =  DatastoreServiceFactory
                .getDatastoreService();
        trainingPlan.setProperty("title",this.title);
        trainingPlan.setProperty("description",this.description);
        trainingPlan.setProperty("domain",this.domain);
        trainingPlan.setProperty("email",this.email);
        com.google.appengine.api.datastore.Key key = UTIL.put(trainingPlan);

        List<Entity> entities = new ArrayList<>();
        Entities exercises = new Entities();
        for (Exercise e: this.exercises) {
            //datastoreService.put(e.toEntity());
            Entity entity = e.toEntity();
            entity.setProperty("trainingPlan",key);
            UTIL.put(entity);
            //trainingPlan.setProperty("exercise", UTIL.put(e.toEntity()));
        }
        return trainingPlan;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
