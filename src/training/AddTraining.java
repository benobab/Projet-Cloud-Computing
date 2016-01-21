package training;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.Exercise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Benobab on 21/01/16.
 */
public class AddTraining extends HttpServlet {

    private static final String TRAINING_KEY = "trainingPlan";
    private static final String ACTION_KEY = "action";
    private static final String TRAINING_ID_KEY = "trainingPlanId";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
