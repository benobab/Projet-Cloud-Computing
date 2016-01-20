package training;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Created by Benobab on 20/01/16.
 */
public class UTIL {
    public static com.google.appengine.api.datastore.Key put(Entity e){
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        return datastore.put(e);
    }
}
