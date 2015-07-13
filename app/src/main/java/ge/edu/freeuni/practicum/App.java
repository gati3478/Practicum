package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.model.UserInfo;

/**
 * Application class.
 */
public class App extends Application {

    private UserInfo mUserInfo;
    private int mNumberOfStudentsInLocation;
    private String[] mGroup = null;

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

        HashMap<String, Object> params = new HashMap<>();
//        params.put("email", "ggham12@freeuni.edu.ge");
        params.put("locationId", "nQb9weOv0h");

        ParseCloud.callFunctionInBackground("studentsByLocation", params, new FunctionCallback<ArrayList<ParseUser>>() {
            public void done(ArrayList<ParseUser> result, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < result.size(); i++) {
                        System.out.println(result.get(i).getEmail());
                        System.out.println("##############");
                    }
                }
            }
        });
    }

    /**
     * sets the UserInfo object of the current user
     * @param userInfo UserINfo object to be set
     */
    public void setUserInfo(UserInfo userInfo){
        mUserInfo = userInfo;
    }

    /**
     *
     * @return  UserInfo object of the current user
     */
    public UserInfo getUserInfo(){
        return mUserInfo;
    }

    /**
     * sets number of students in the user's current location
     * @param numberOfStudents number of students to be set
     */
    public void setNumberOfStudents(int numberOfStudents){
        mNumberOfStudentsInLocation = numberOfStudents;
    }

    /**
     *
     * @return  number of students in the user's current location
     */
    public int getNumberOfStudents(){
        return mNumberOfStudentsInLocation;
    }

    /**
     * sets the group of people in the user's current location
     * @param group group of students to be set
     */
    public void setGroup(String[] group){
        mGroup = group;
    }

    /**
     *
     * @return  returns the group of people in the user's current location
     */
    public String[] getGroup(){
        return mGroup;
    }
}
