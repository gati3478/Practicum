package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.model.UserInfo;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsDownloadedListener;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsWishListDownloaded;

/**
 * Application class.
 */
public class App extends Application {

    private UserInfo mUserInfo;
    private int mNumberOfStudentsInLocation;
    private String[] mGroup = null;
    private List<Location> mLocations = null;
    private List<Location> mWishList = null;

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
        params.put("userInfoId", "UNmof7YvDr");

        ParseCloud.callFunctionInBackground("getWishList", params, new FunctionCallback<ArrayList<Location>>() {
            public void done(ArrayList<Location> result, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < result.size(); i++) {
                        System.out.println(result.get(i).getName() + " " + result.get(i).getWave());
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

    /**
     * On the first time of invoking, this method downloads and saves the whole list of locations.
     * Every subsequent call of this method the same list is used
     */
    public void getLocations(final OnLocationsDownloadedListener listener){

        if (mLocations == null) {
            ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
            query.findInBackground(new FindCallback<Location>() {
                @Override
                public void done(List<Location> results, ParseException e) {
                    if (e == null) {
                        mLocations = results;
                        if (listener != null){
                            listener.onLocationsDownloaded(results, mUserInfo.getCurrentLocation());
                        }
                    }
                }
            });
        }else {
            listener.onLocationsDownloaded(mLocations, mUserInfo.getCurrentLocation());
        }

    }

    /**
     * On the first time of invoking, this method downloads and saves the wish list of locations.
     * Every subsequent call of this method the same list is used
     */
    public void getWishListOfLocations(final OnLocationsWishListDownloaded listener){

        if (mWishList == null) {

            HashMap<String, Object> params = new HashMap<>();
            params.put("userInfoId", mUserInfo.getObjectId());

            ParseCloud.callFunctionInBackground("getWishList", params, new FunctionCallback<ArrayList<Location>>() {
                public void done(ArrayList<Location> result, ParseException e) {
                    if (e == null) {
                        mWishList = result;
                        listener.onLocationsWishListDownloaded(mWishList);
                    }
                }
            });
        }else {
            listener.onLocationsWishListDownloaded(mWishList);
        }

    }

}
