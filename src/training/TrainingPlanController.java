package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.repackaged.com.google.datastore.v1.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.Exercise;
import model.TrainingPlan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benobab on 19/01/16.
 */
public class TrainingPlanController extends HttpServlet {

    private static final String TRAINING_KEY = "trainingPlan";
    private static final String ACTION_KEY = "action";
    private static final String TRAINING_ID_KEY = "trainingPlanId";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter(ACTION_KEY) != null && req.getParameter(ACTION_KEY).equals("GET")) {
            String id = req.getParameter(TRAINING_ID_KEY);
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            Query.Filter keyFilter =
                    new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                            Query.FilterOperator.EQUAL,
                            KeyFactory.createKey("TrainingPlan", Long.parseLong(id)));

            // Use class Query to assemble a query
            Query qTP = new Query("TrainingPlan").setFilter(keyFilter);
            PreparedQuery pqTP = datastore.prepare(qTP);

            TrainingPlan tp = new TrainingPlan();
            for (Entity rTP : pqTP.asIterable()) {

                tp.setTitle((String) rTP.getProperty("title"));
                tp.setDescription((String) rTP.getProperty("description"));
                tp.setDomain((String) rTP.getProperty("domain"));
                tp.setExercises(new ArrayList<>());

                Query qE = new Query("Exercise").setAncestor(rTP.getKey());
                PreparedQuery pdE = datastore.prepare(qE);
                for (Entity rE : pdE.asIterable()) {

                    Exercise e = new Exercise();
                    e.setTitle((String) rE.getProperty("title"));
                    e.setDescription((String) rE.getProperty("description"));
                    e.setDuration((String) rE.getProperty("duration"));
                    tp.getExercises().add(e);

                }

                break;
            }

            Gson gson = new Gson();
            resp.getWriter().write(gson.toJson(tp));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter(ACTION_KEY).equals("ADD")) {
            Gson gson = new Gson();
            model.TrainingPlan tp = gson.fromJson(request.getParameter(TRAINING_KEY), model.TrainingPlan.class);
            DatastoreService datastore = DatastoreServiceFactory
                    .getDatastoreService();
            Key trKey = datastore.put(tp.toEntity());
            for (Exercise exercice : tp.getExercises()) {
                datastore.put(exercice.toEntity(trKey));
            }
            response.setStatus(200);
        }

    }
}
