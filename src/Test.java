import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
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
 * Created by TBD on 20/01/2016.
 */
public class Test extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key k = KeyFactory.createKey("TrainingPlan", 6333186975989760L);//, new AppIdNamespace("projethd-1995", ""));
        Filter kf =
                new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                        FilterOperator.EQUAL,
                        k);
        Query qTPa = new Query("TrainingPlan").setFilter(kf);
        PreparedQuery pqTPa = datastore.prepare(qTPa);


        for (Entity a : pqTPa.asIterable()) {
            System.out.println(a.getProperty("title"));
        }

        Filter titleFilter =
                new FilterPredicate("title",
                        FilterOperator.EQUAL,
                        "AAAA");

        // Use class Query to assemble a query
        Query qTP = new Query("TrainingPlan").setFilter(titleFilter);
        PreparedQuery pqTP = datastore.prepare(qTP);

        List<TrainingPlan> trainingPlans = new ArrayList<>();
        for (Entity rTP : pqTP.asIterable()) {

            TrainingPlan tp = new TrainingPlan();
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

            trainingPlans.add(tp);
        }

        System.out.println("Success");
    }
}
