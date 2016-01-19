package training;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Benobab on 19/01/16.
 */
public class InitBDD extends HttpServlet {
    private static final String MSG_ACCUEIL = "message_accueil";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        String msgDatastore= null;
        Query q = new Query(MSG_ACCUEIL);
        PreparedQuery pq = datastore.prepare(q);

        for (Entity result : pq.asIterable()) {
            msgDatastore= (String) result.getProperty(MSG_ACCUEIL);
        }
        if(msgDatastore == null){
            //Récupérer le service Datastore

            //Créer l'entity avec les paramètres
            Entity message = new Entity(MSG_ACCUEIL);
            message.setProperty(MSG_ACCUEIL, "Bienvenue sur notre site web d'entrainement, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");


            //enregistrer l'entité dans le datastore
            datastore.put(message);
        }
        response.getWriter().write("C'est initialise !!");
    }
}
