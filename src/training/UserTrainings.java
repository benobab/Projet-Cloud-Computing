package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.Exercise;
import model.ExerciseValidated;
import model.SearchObject;
import model.TrainingPlan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Benobab on 20/01/16.
 */
public class UserTrainings extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query.FilterPredicate filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,email);
        Query q = new Query("ExerciseValidated").setFilter(filter);
        PreparedQuery pq = datastore.prepare(q);

        List<ExerciseValidated> returned = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            ExerciseValidated e = new ExerciseValidated();
            e.setDate((Date) result.getProperty("date"));
            e.setEmail((String) result.getProperty("email"));
            e.setExercise((String)result.getProperty("exercise"));
            e.setExerciseId((long)result.getProperty("exerciseId"));
            e.setTrainingPlan((String) result.getProperty("trainingPlan"));
            e.setTrainingPlanId((long)result.getProperty("trainingPlanId"));
            returned.add(e);
        }

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(returned));
    }
}
