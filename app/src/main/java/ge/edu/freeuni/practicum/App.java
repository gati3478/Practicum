package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Application class.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.app_id), getResources().getString(R.string.client_key));

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("email", "ggham12@freeuni.edu.ge");
        params.put("locationId", "ehsNlkaF6a");

        ParseCloud.callFunctionInBackground("findCycles", params, new FunctionCallback<ArrayList<String>>() {
            public void done(ArrayList<String> result, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < result.size(); i++) {
                        System.out.println(result.get(i));
                        System.out.println("##############");
                    }
                }
            }
        });
    }

}
