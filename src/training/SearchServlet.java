package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.datastore.v1.Filter;
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
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyWords = request.getParameter("keywords");
        String[] keywords = searchKeyWords.split(" ");

        List<String> keys = new ArrayList<>();
        for(String s : keywords){
            keys.add(s);
        }

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();




        Query.FilterPredicate filter = new Query.FilterPredicate("title", Query.FilterOperator.IN,keys);
        Query q = new Query("TrainingPlan").setFilter(filter);
        PreparedQuery pq = datastore.prepare(q);

        List<TrainingPlan> trainingPlans = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            trainingPlans.add(TrainingPlan.toTrainingPlan(result));
        }

        Query q2 = new Query("Exercise").setFilter(filter);
        PreparedQuery pq2 = datastore.prepare(q);

        List<Exercise> exercises = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            exercises.add(Exercise.toExercice(result));
        }

        Gson gson = new Gson();
        SearchObject searchObject = new SearchObject(exercises,trainingPlans);
        response.getWriter().write(gson.toJson(searchObject));
    }
}
