package training;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.gson.Gson;
import model.Exercise;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AddTraining extends HttpServlet {

    private static final String TRAINING_KEY = "trainingPlan";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        model.TrainingPlan tp = gson.fromJson(request.getParameter(TRAINING_KEY), model.TrainingPlan.class);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        tp.setCreateDate(new Date());
        Key trKey = datastore.put(tp.toEntity());
        for (Exercise exercice : tp.getExercises()) {
            datastore.put(exercice.toEntity(trKey));
        }
        response.setStatus(200);
    }
}
