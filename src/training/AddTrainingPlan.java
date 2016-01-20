package training;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.api.datastore.Key;
import model.Exercise;
import model.TrainingPlan;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Benobab on 19/01/16.
 */
public class AddTrainingPlan extends HttpServlet {

    private static final String trainingKey = "trainingPlan";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        TrainingPlan tp = gson.fromJson(request.getParameter(trainingKey), TrainingPlan.class);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        Key trKey = datastore.put(tp.toEntity());
        for (Exercise exercice : tp.getExercises()) {
            datastore.put(exercice.toEntity(trKey));
        }
        response.setStatus(200);

    }
}
