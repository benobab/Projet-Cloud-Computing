package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Benobab on 20/01/16.
 */
public class SearchObject implements Serializable {
    private List<Exercise> exercises;
    private List<TrainingPlan> trainingPlans;


    public SearchObject(List<Exercise> exercises, List<TrainingPlan> trainingPlans) {
        this.exercises = exercises;
        this.trainingPlans = trainingPlans;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<TrainingPlan> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(List<TrainingPlan> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }
}
