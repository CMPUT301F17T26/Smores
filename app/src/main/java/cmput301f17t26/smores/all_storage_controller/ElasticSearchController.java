package cmput301f17t26.smores.all_storage_controller;

import android.content.pm.PackageInstaller;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cmput301f17t26.smores.all_models.Habit;
import cmput301f17t26.smores.all_models.HabitEvent;
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

public class ElasticSearchController {

    private static JestDroidClient client;
    private static Gson gson;

    public static class GetHabitTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... params) {
            verifySettings();

            ArrayList<Habit> newHabits = new ArrayList<Habit>();

            String query; //params[0] = field to match on, params[1] = actual field. E.g. params[0] = "mID" and params[1] = "habit.getmID()"
            query =  "{\"from\":0,\"size\":100000,\"query\":{\"match\":{\"" + params[0] + "\":\"" + params[1] + "\"}}}";

            //alternate query if params[0] == "",  "{\"from\" : 0, \"size\" : 10000}" aka Return 100000 of all habits found

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f17t26")
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
                        .index("cmput301f17t26")
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
                    .addIndex("cmput301f17t26")
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
                throw new RuntimeException();
            }

            Delete delete = new Delete.Builder((String)((Map) hitList.get(0).source).get(JestResult.ES_METADATA_ID))
                    .index("cmput301f17t26")
                    .type("habit")
                    .build();

            try {
                DocumentResult execute = client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
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
                        .index("cmput301f17t26")
                        .type("session")
                        .id(params[0].getID().toString())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    //taken from elastic search lab.
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
