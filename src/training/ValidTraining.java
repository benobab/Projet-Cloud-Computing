package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.ExerciseValidated;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ValidTraining extends HttpServlet {

    private static final String TRAININGID_KEY = "trainingPlanId";
    private static final String TRAINING_KEY = "trainingPlan";
    private static final String EXERCISEID_KEY = "exerciseId";
    private static final String EXERCISE_KEY = "exercise";
    private static final String EMAIL_KEY = "email";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        long tpId = Long.parseLong((String) request.getParameter(TRAININGID_KEY));
        long exId = Long.parseLong((String) request.getParameter(EXERCISEID_KEY));
        String email = (String) request.getParameter(EMAIL_KEY);
        String tpTitle = (String) request.getParameter(TRAINING_KEY);
        String exTitle = (String) request.getParameter(EXERCISE_KEY);
        if (email != null && email != "") {
            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();
            ExerciseValidated e = new ExerciseValidated();
            e.setEmail(email);
            e.setTrainingPlan(tpTitle);
            e.setTrainingPlanId(tpId);
            e.setExerciseId(exId);
            e.setExercise(exTitle);
            e.setDate(new Date());
            datastore.put(e.toEntity());
        }
        response.setStatus(200);
    }
}
