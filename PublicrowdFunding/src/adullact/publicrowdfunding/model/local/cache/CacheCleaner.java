package adullact.publicrowdfunding.model.local.cache;

import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;

/*import com.vladium.utils.IObjectProfileNode;
import com.vladium.utils.ObjectProfiler;*/

/**
 * Created by Ferrand on 04/09/2014.
 */
public class CacheCleaner {
    /* --- Singleton --- */
    private static CacheCleaner m_instance = null;
    private CacheCleaner() {
    }

    public static CacheCleaner getInstance() {
        if(m_instance == null) {
            m_instance = new CacheCleaner();
        }

        return m_instance;
    }
    /* ----------------- */

    public void fill() {
        int nbUser = 0;
        int nbProject = 0;
        /*IObjectProfileNode profile = ObjectProfiler.profile(Resource.cachedResource);

        Log.d("Triumvirat", "Initial size : " + profile.size() + " bytes");*/

        /* User */
        for(int i = 0; i < nbUser; i++) {
            User user = new User();
            user.setResourceId(generate(6));
            user.setFirstName(generate(6));
            user.setName(generate(6));
            user.setGender(generate(1));
            user.setCity(generate(10));

            user.getCache();
        }
        /*profile = ObjectProfiler.profile(Resource.cachedResource);
        Log.d("Triumvirat", "Adding " + nbUser + " users : " + profile.size() + " bytes");*/
        /* ---- */

        /* Project */
                /* User */
        for(int i = 0; i < nbProject; i++) {
            Project project = new Project(random(10000), false, generate(15), generate(300), false, generate(6), "10000", "100", "2014-05-06 19:56:21", "2014-05-06 19:56:21", "2014-05-06 19:56:21", 50., 50., 0, generate(10), generate(30), generate(10));

                project.getCache();
        }
        /*profile = ObjectProfiler.profile(Resource.cachedResource);
        Log.d("Triumvirat", "Adding " + nbProject + " projects : " + profile.size() + " bytes");*/
        /* ---- */
        /* ------ */

        /*profile = ObjectProfiler.profile(Resource.cachedResource);

        Log.d("Triumvirat", "Final size : " + profile.size() + " bytes");*/
    }


    public static String generate(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer pass = new StringBuffer();
        for(int x=0;x<length;x++)   {
            int i = (int)Math.floor(Math.random() * (chars.length() -1));
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }

    public static int random(int max) {
        return (int) Math.floor(Math.random() * max);
    }

    public void run() {
        /*System.out.println("Runing time");
        Scheduler.Worker worker = Schedulers.newThread().createWorker();
        worker.schedulePeriodically(new Action0() {
            @Override
            public void call() {
                HashMap<String, HashMap<String, Cache>> cachedResource = Resource.cachedResource;
                for(Map.Entry<String, HashMap<String, Cache>> entry : cachedResource.entrySet()) {
                    System.out.println ("=== " + entry.getKey() + " ===");
                    for(Map.Entry<String, Cache> entryCache : entry.getValue().entrySet()) {
                        System.out.println ("id : " + entry.getKey());
                        IObjectProfileNode profile = ObjectProfiler.profile(entryCache.getValue());

                        System.out.println ("obj size = " + profile.size () + " bytes");
                        System.out.println (profile.dump ());
                        //entryCache.getValue().clean();
                    }
                    System.out.println ("=== ====================== ===");
                }
            }
        }, 1, 3, TimeUnit.SECONDS);*/
    }
}
