package training;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Benobab on 19/01/16.
 */
public class Accueil extends HttpServlet {

    private static final String MSG_ACCUEIL = "message_accueil";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        //récupération du service Cache
        Cache cache=null;
        Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
        props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
        } catch ( net.sf.jsr107cache.CacheException e) {
            e.printStackTrace();
        }

        if( cache.get(MSG_ACCUEIL)!=null){
            response.getWriter().write((String)cache.get(MSG_ACCUEIL));
        }else{
            String msgDatastore= null;
            Query q = new Query(MSG_ACCUEIL);
            PreparedQuery pq = datastore.prepare(q);

            for (Entity result : pq.asIterable()) {
                msgDatastore= (String) result.getProperty(MSG_ACCUEIL);
            }
            if(msgDatastore == null){
                msgDatastore = "Datastore mal initialisé";
            }


            //udpate cache
            cache.put(MSG_ACCUEIL, msgDatastore);
            response.getWriter().write(msgDatastore);
        }
    }
}
