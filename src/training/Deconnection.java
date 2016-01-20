package training;

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
 * Created by Benobab on 20/01/16.
 */
public class Deconnection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cache cache = null;
        try {
            Map props = new HashMap();
            props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
            props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
            //TODO: get map users
            Map<String,String> users = (HashMap) cache.get("users");
            if(users == null){
                users = new HashMap<>();
            }
            String name = request.getParameter("name");
            if(name != null){
                users.remove(name);
                cache.put("users",users);
            }
        } catch ( net.sf.jsr107cache.CacheException e) {
            e.printStackTrace();
        }
    }
}
