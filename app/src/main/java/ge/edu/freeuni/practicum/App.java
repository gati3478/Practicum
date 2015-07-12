package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.model.UserInfo;

/**
 * Application class.
 */
public class App extends Application {

    private UserInfo mUserInfo;
    private int mNumberOfStudentsInLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        mNumberOfStudentsInLocation = -1;
        //register subclasses
        ParseObject.registerSubclass(UserInfo.class);
        ParseObject.registerSubclass(Location.class);

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

    public void setUserInfo(UserInfo userInfo){
        mUserInfo = userInfo;
    }

    public UserInfo getUserInfo(){
        return mUserInfo;
    }

    //sets number of students in the user's current location
    public void setNumberOfStudents(int numberOfStudents){
        mNumberOfStudentsInLocation = numberOfStudents;
    }

    //returns number of students in the user's current location
    public int getNumberOfStudents(){
        return mNumberOfStudentsInLocation;
    }

}
