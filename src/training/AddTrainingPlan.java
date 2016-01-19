package training;

import com.google.appengine.repackaged.com.google.gson.Gson;
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
