**Projet déployé sur le cloud [ici](https://projethd-1195.appspot.com)**

# Etudiants 
- Nicolas Sagon
- Benjamin Lacroix
- Thibault Boksebeld

# Architecture
API en Java EE via des servlets, task, etc.

# Fonctions implémentées
+ Page d'accueil avec appel au cache pour récupérer le message d'accueil et au datastore s'il n'y est pas présent.

```JAVA
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

```

- Connexion / Déconnexion avec Google puis ajout de l'utilisateur dans le Memory Cache <name,email> :

```JAVA
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
            String email = request.getParameter("email");
            if(name != null && email != null){
                users.put(name,email);
                cache.put("users",users);
            }
        } catch ( net.sf.jsr107cache.CacheException e) {
            e.printStackTrace();
        }

```

- Page de Recherche, permettant ainsi de voir la recherche avec ancestor, pour les exercices dans les trainingPlans par exemple, mais aussi de voir les queryFilter. On lui envoie une liste de mot clé. La recherche est stricte !

```JAVA
String searchKeyWords = request.getParameter("keywords");
        String[] keywords = searchKeyWords.split(" ");

        List<String> keys = new ArrayList<>();
        for(String s : keywords){
            keys.add(s);
        }

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query.FilterPredicate filter = new Query.FilterPredicate("title", Query.FilterOperator.IN,keys);
        Query q = new Query("TrainingPlan").setFilter(filter);
        PreparedQuery pq = datastore.prepare(q);

        List<TrainingPlan> trainingPlans = new ArrayList<>();
        for (Entity result : pq.asIterable()) {
            Query queryExercice = new Query("Exercise").setAncestor(result.getKey());
            PreparedQuery preparedQueryExercise = datastore.prepare(queryExercice);
            List<Exercise> listEx = new ArrayList<>();
            for(Entity resultExercise : preparedQueryExercise.asIterable()){
                listEx.add(Exercise.toExercice(resultExercise));
            }
            trainingPlans.add(TrainingPlan.toTrainingPlan(result, listEx));
        }

        Query q2 = new Query("Exercise").setFilter(filter);
        PreparedQuery pq2 = datastore.prepare(q2);

        List<Exercise> exercises = new ArrayList<>();
        for (Entity resultExercice : pq2.asIterable()) {
            exercises.add(Exercise.toExercice(resultExercice));
        }

        Gson gson = new Gson();
        SearchObject searchObject = new SearchObject(exercises,trainingPlans);
        response.getWriter().write(gson.toJson(searchObject));

```


- Page d'ajout de plan d'entrainement, avec un ajout dans le datastore des exercices en spécifiant la key du training plan tout juste créé :

```
Gson gson = new Gson();
        model.TrainingPlan tp = gson.fromJson(request.getParameter(TRAINING_KEY), model.TrainingPlan.class);
        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();
        tp.setCreateDate(new Date());
        Key trKey = datastore.put(tp.toEntity());
        for (Exercise exercice : tp.getExercises()) {
            datastore.put(exercice.toEntity(trKey));
        }

```

- News / Flux RSS, récupéré du site "l'équipe" via URL Fetch (URL + bufferReader) suivi d'une transformation XSL :

```JAVA

try {
            URL url = new URL("http://www.lequipe.fr/rss/actu_rss.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            String message="";
            while ((line = reader.readLine()) != null)
            { // Traitement des données reçue
                message+=line;
            }
            System.out.println(message);
            StringWriter stringWriter = new StringWriter();
            Result m = new StreamResult(stringWriter);
            Source xsl = new StreamSource(new File("rssxsl.xsl"));
            try {
                Transformer t = TransformerFactory.newInstance().newTransformer(xsl);
                t.transform(new StreamSource(new StringReader(message)), m);
                StringBuffer stringBuffer = stringWriter.getBuffer();

                response.getWriter().write(stringBuffer.toString());

            }catch (TransformerException e) {
                    e.printStackTrace();
            }
            reader.close();


        } catch (MalformedURLException e) {
            // Gestion d’exceptions d’ouverture de flux
        } catch (IOException e) {
            // Gestion d’exceptions de lecture de flux
        }

```

- Visualisation des données personnelles, avec récupération des trainingPlan assigné avec l'adresse email de l'utilisateur connecté :

```JAVA
String email = request.getParameter("email");

        DatastoreService datastore = DatastoreServiceFactory
                .getDatastoreService();

        Query.FilterPredicate filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL,email);
        Query q = new Query("ExerciseValidated").setFilter(filter);
        PreparedQuery pq = datastore.prepare(q);


```

- Utilisation d'une queue pour certains ajouts via le datastore :

```JAVA
Queue queue = QueueFactory.getDefaultQueue();
            TaskOptions task = TaskOptions.Builder.withUrl("/validtrainingexercise")
                    .param(TRAINING_ID_KEY, request.getParameter(TRAINING_ID_KEY))
                    .param(EXERCISEID_KEY, request.getParameter(EXERCISEID_KEY))
                    .param(EMAIL_KEY, request.getParameter(EMAIL_KEY))
                    .param(TRAINING_KEY, request.getParameter(TRAINING_KEY))
                    .param(EXERCISE_KEY, request.getParameter(EXERCISE_KEY));
            queue.add(task);
```
- Communication sur le chat général du site web via les channel et un générateur de Token.

# Fonctions partiellement implémentées
- Détail d'un plan d'entrainement

# Fonctions manquantes
- Communication avec un coach particulier / choisi

# Difficultées rencontrées
- Beaucoup de js à écrire, moins de temps pour réellement exploiter le cloud à 100%.
