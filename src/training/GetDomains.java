package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
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
public class GetDomains extends HttpServlet {
    private final static String domains="DOMAIN";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String search = request.getParameter("searchString");
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query q = new Query(domains);
        PreparedQuery pq = datastore.prepare(q);

        List<String> domains = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            if((String)result.getProperty("label") == search){

            }
            domains.add((String)result.getProperty("label"));
        }*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query q = new Query(domains);
        PreparedQuery pq = datastore.prepare(q);

        List<String> domains = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            domains.add((String)result.getProperty("label"));
        }
        Gson gson = new Gson();
        String domainsJson = gson.toJson(domains);
        response.getWriter().write(domainsJson);
    }
}
