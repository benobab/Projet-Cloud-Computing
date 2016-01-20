package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.Exercise;
import model.SearchObject;
import model.TrainingPlan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        Query q = new Query("TrainingPlan").setFilter(filter);
        PreparedQuery pq = datastore.prepare(q);

        List<TrainingPlan> trainingPlans = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            Query queryExercice = new Query("Exercise").setAncestor(result.getKey());
            PreparedQuery preparedQueryExercise = datastore.prepare(queryExercice);
            List<Exercise> listEx = new ArrayList<>();
            for(Entity resultExercise : preparedQueryExercise.asIterable()){
                listEx.add(Exercise.toExercice(resultExercise));
            }
            trainingPlans.add(TrainingPlan.toTrainingPlan(result, listEx));
        }

        Gson gson = new Gson();
        SearchObject searchObject = new SearchObject(new ArrayList<Exercise>(),trainingPlans);
        response.getWriter().write(gson.toJson(searchObject));
    }
}
