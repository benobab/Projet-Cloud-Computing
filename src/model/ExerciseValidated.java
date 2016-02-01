package model;

import com.google.appengine.api.datastore.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExerciseValidated implements Serializable {
    private String exercise;
    private String trainingPlan;
    private Date date;
    private String email;
    private long exerciseId;

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(long trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    private long trainingPlanId;

    public ExerciseValidated(String exercise, String trainingPlan, Date date, String email) {
        this.exercise = exercise;
        this.trainingPlan = trainingPlan;
        this.date = date;
        this.email = email;
    }

    public ExerciseValidated() {

    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(String trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Entity toEntity() {
        Entity exerciseValidation = new Entity("ExerciseValidated");
        exerciseValidation.setProperty("email", email);
        exerciseValidation.setProperty("trainingPlanId", trainingPlanId);
        exerciseValidation.setProperty("trainingPlan", trainingPlan);
        exerciseValidation.setProperty("exerciseId", exerciseId);
        exerciseValidation.setProperty("exercise", exercise);
        exerciseValidation.setProperty("date", date);
        return exerciseValidation;
    }
}
