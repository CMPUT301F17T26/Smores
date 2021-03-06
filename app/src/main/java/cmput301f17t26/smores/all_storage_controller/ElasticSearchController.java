/*
 * ElasticSearchController
 *
 * Version 1.0
 *
 * November 25, 2017
 *
 * Copyright (c) 2017 Team 26, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact rohan@ualberta.ca
 *
 * Purpose: Controller class for interfacing with Elastic search. Designed around a CRUD interface.
 */

package cmput301f17t26.smores.all_storage_controller;

import android.content.pm.PackageInstaller;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cmput301f17t26.smores.all_adapters.RequestAdapter;
import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
import cmput301f17t26.smores.all_models.Request;
import cmput301f17t26.smores.all_models.User;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Doc;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by rohan on 10/19/2017.
 */

public class ElasticSearchController { //ElasticSearchController.GetHabitTask(String[0] = field, String[1] = value)

    private static JestDroidClient client;
    private static Gson gson;
    private static final String ELASTIC_SEARCH_INDEX = "cmput301f17t26_smores";

    public static class AddUserTask extends  AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... params) {
            verifySettings();

            for (User user: params) {
                Index index = new Index.Builder(user)
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("user")
                        .id(user.getUserID().toString())
                        .build();
                try {
                    DocumentResult execute = client.execute(index);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
            return null;
        }
    }

    // Can be used as a GetUserTask
    public static class CheckUserTask extends  AsyncTask<String, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(String... params) {
            verifySettings();

            ArrayList<User> newUsers = new ArrayList<User>();

            String query; //params[0] = field to match on, params[1] = actual field. E.g. params[0] = "mID" and params[1] = "habit.getmID()"
            query =  "{\"from\":0,\"size\":100000,\"query\":{\"match\":{\"" + params[0] + "\":\"" + params[1] + "\"}}}";

            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("user")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<User> foundUsers = execute.getSourceAsObjectList(User.class);
                    newUsers.addAll(foundUsers);
                } else { //it failed

                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            return newUsers;
        }
    }

    public static class UpdateUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... params) {
            verifySettings();

            try {
                JestResult result = client.execute(new Index.Builder(params[0])
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("user")
                        .id(params[0].getUserID().toString())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static class GetHabitTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... params) {
            verifySettings();

            ArrayList<Habit> newHabits = new ArrayList<Habit>();

            String query; //params[0] = field to match on, params[1] = actual field. E.g. params[0] = "mID" and params[1] = "habit.getmID()"
            query =  "{\"from\":0,\"size\":100000,\"query\":{\"match\":{\"" + params[0] + "\":\"" + params[1] + "\"}}}";

            //alternate query if params[0] == "",  "{\"from\" : 0, \"size\" : 10000}" aka Return 100000 of all habits found

            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("habit")
                    .build();
            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<Habit> foundHabits = execute.getSourceAsObjectList(Habit.class);
                    newHabits.addAll(foundHabits);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newHabits;
        }
    }

    public static class AddHabitTask extends AsyncTask<Habit, Void, Void> {

        @Override
        protected Void doInBackground(Habit... params) {
            verifySettings();

            for (Habit habit : params) {
                Index index = new Index.Builder(habit)
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habit")
                        .id(habit.getID().toString())
                        .build();
                try {
                    DocumentResult execute = client.execute(index);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class RemoveHabitTask extends AsyncTask<UUID, Void, Void> {

        @Override
        protected Void doInBackground(UUID... params) {
            verifySettings();
            ArrayList<Habit> habitsToDelete = new ArrayList<Habit>();
            List<SearchResult.Hit<Map,Void>> hitList = null;

            String query = "{\"query\":{ \"match\" :{\"mID\":\"" + params[0] + "\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("habit")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<Habit> foundHabits = execute.getSourceAsObjectList(Habit.class);
                    hitList = execute.getHits(Map.class);
                    habitsToDelete.addAll(foundHabits);
                }
            } catch (IOException e) {

            }

            try {
                Delete delete = new Delete.Builder((String)((Map) hitList.get(0).source).get(JestResult.ES_METADATA_ID))
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habit")
                        .build();

                try {
                    DocumentResult execute = client.execute(delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }


            return null;
        }
    }


    public static class UpdateHabitTask extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... params) {
            verifySettings();

            try {
                JestResult result = client.execute(new Index.Builder(params[0])
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habit")
                        .id(params[0].getID().toString())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static class GetHabitEventTask extends AsyncTask<String, Void, ArrayList<HabitEvent>> {
        @Override
        protected ArrayList<HabitEvent> doInBackground(String... params) {
            verifySettings();

            ArrayList<HabitEvent> newHabitEvents = new ArrayList<HabitEvent>();

            String query; //params[0] = field to match on, params[1] = actual field. E.g. params[0] = "mID" and params[1] = "habitEvent.getmID()"
            query =  "{\"from\":0,\"size\":100000,\"query\":{\"match\":{\"" + params[0] + "\":\"" + params[1] + "\"}}}";

            //alternate query if params[0] == "",  "{\"from\" : 0, \"size\" : 10000}" aka Return 100000 of all habits found

            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("habitevent")
                    .build();
            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<HabitEvent> foundHabitEvents = execute.getSourceAsObjectList(HabitEvent.class);
                    newHabitEvents.addAll(foundHabitEvents);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newHabitEvents;
        }
    }


    public static class AddHabitEventTask extends AsyncTask<HabitEvent, Void, Void> {

        @Override
        protected Void doInBackground(HabitEvent... params) {
            verifySettings();

            for (HabitEvent habitEvent : params) {
                Index index = new Index.Builder(habitEvent)
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habitevent")
                        .id(habitEvent.getID().toString())
                        .build();
                try {
                    DocumentResult execute = client.execute(index);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class RemoveHabitEventTask extends AsyncTask<UUID, Void, Void> {

        @Override
        protected Void doInBackground(UUID... params) {
            verifySettings();
            ArrayList<HabitEvent> habitEventsToDelete = new ArrayList<HabitEvent>();
            List<SearchResult.Hit<Map,Void>> hitList = null;

            String query = "{\"query\":{ \"match\" :{\"mID\":\"" + params[0] + "\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("habitevent")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<HabitEvent> foundHabitEvents = execute.getSourceAsObjectList(HabitEvent.class);

                    hitList = execute.getHits(Map.class);
                    habitEventsToDelete.addAll(foundHabitEvents);
                }
            } catch (IOException e) {

            }

            try {
                Delete delete = new Delete.Builder((String)((Map) hitList.get(0).source).get(JestResult.ES_METADATA_ID))
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habitevent")
                        .build();

                try {
                    DocumentResult execute = client.execute(delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {

            }

            return null;
        }
    }

    public static class UpdateHabitEventTask extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... params) {
            verifySettings();

            try {
                JestResult result = client.execute(new Index.Builder(params[0])
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("habitevent")
                        .id(params[0].getID().toString())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static class AddRequestTask extends  AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... params) {
            verifySettings();

            for (Request request: params) {
                Index index = new Index.Builder(request)
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("request")
                        .id(request.getID())
                        .build();
                try {
                    DocumentResult execute = client.execute(index);
                    Log.d("Elasticsearch", "Added request");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class GetRequestTask extends AsyncTask<String, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(String... params) {
            verifySettings();

            ArrayList<Request> newRequests = new ArrayList<Request>();

            String query; //params[0] = field to match on, params[1] = actual field. E.g. params[0] = "mID" and params[1] = "habitEvent.getmID()"
            query =  "{\"from\":0,\"size\":100000,\"query\":{\"match\":{\"" + params[0] + "\":\"" + params[1] + "\"}}}";

            //alternate query if params[0] == "",  "{\"from\" : 0, \"size\" : 10000}" aka Return 100000 of all habits found

            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("request")
                    .build();
            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<Request> foundRequests = execute.getSourceAsObjectList(Request.class);
                    newRequests.addAll(foundRequests);
                    if (foundRequests.size() == 0) {
                        Log.d("Elasticsearch", "Nobody found");
                    } else {
                        for (Request request: foundRequests) {
                            Log.d("Elasticsearch", request.getID());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newRequests;
        }
    }

    public static class RemoveRequestTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            verifySettings();
            ArrayList<Request> requestsToDelete = new ArrayList<Request>();
            List<SearchResult.Hit<Map,Void>> hitList = null;

            String query = "{\"query\":{ \"match\" :{\"mID\":\"" + params[0] + "\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex(ELASTIC_SEARCH_INDEX)
                    .addType("request")
                    .build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<Request> foundRequests = execute.getSourceAsObjectList(Request.class);
                    hitList = execute.getHits(Map.class);
                    requestsToDelete.addAll(foundRequests);
                }
            } catch (IOException e) {

            }

            try {
                Delete delete = new Delete.Builder((String)((Map) hitList.get(0).source).get(JestResult.ES_METADATA_ID))
                        .index(ELASTIC_SEARCH_INDEX)
                        .type("request")
                        .build();

                try {
                    DocumentResult execute = client.execute(delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch(Exception e) {

            }

            return null;
        }
    }

    //taken from elastic search lab.
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
