package model;

import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import training.UTIL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingPlan implements Serializable {
    private String title;
    private String description;
    private String domain;
    private List<Exercise> exercises;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private Date createDate;
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

    public TrainingPlan(String title, String description, String domain, String email) {
        this.title = title;
        this.description = description;
        this.domain = domain;
        this.email = email;
    }

    public Entity toEntity(){
        Entity trainingPlan = new Entity("TrainingPlan");
        trainingPlan.setProperty("title",this.title);
        trainingPlan.setProperty("description",this.description);
        trainingPlan.setProperty("domain",this.domain);
        trainingPlan.setProperty("email",this.email);
        trainingPlan.setProperty("createDate", this.createDate);
        return trainingPlan;
    }

    public static  TrainingPlan toTrainingPlan(Entity e, List<Exercise> listExercice){
        TrainingPlan trainingPlan = new TrainingPlan((String)e.getProperty("title"),(String)e.getProperty("description"),(String)e.getProperty("domain"),listExercice, (String)e.getProperty("email"));
        trainingPlan.setCreateDate((Date) e.getProperty("createDate"));
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
