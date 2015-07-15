package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
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
import ge.edu.freeuni.practicum.view.fragment.listener.OnUserInfoDownloaded;

/**
 * Application class.
 */
public class App extends Application implements OnUserInfoDownloaded {

    private UserInfo mUserInfo = null;
    private int mNumberOfStudentsInLocation;
    private String[] mGroup = null;
    private List<Location> mLocations = null;
    private List<Location> mWishList = null;
    private OnLocationsWishListDownloaded listener1;
    private OnLocationsDownloadedListener listener2;


    private boolean whoCalled;

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

//        ParseInstallation.getCurrentInstallation().saveInBackground();

//        HashMap<String, Object> params = new HashMap<>();
////        params.put("email", "ggham12@freeuni.edu.ge");
//        params.put("userInfoId", "UNmof7YvDr");
//
//        ParseCloud.callFunctionInBackground("getWishList", params, new FunctionCallback<ArrayList<Location>>() {
//            public void done(ArrayList<Location> result, ParseException e) {
//                if (e == null) {
//                    for (int i = 0; i < result.size(); i++) {
//                        System.out.println(result.get(i).getName() + " " + result.get(i).getWave());
//                        System.out.println("##############");
//                    }
//                }
//            }
//        });

        if (ParseUser.getCurrentUser() == null) {

        } else {
            // Associate the device with a user
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("user", ParseUser.getCurrentUser());
            installation.saveInBackground();
        }
    }

    /**
     *
     */
    public void getUserInfo(final OnUserInfoDownloaded listener) {

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (mUserInfo == null) {
            ParseQuery<UserInfo> query = ParseQuery.getQuery(UserInfo.class);
            query.include("currentLocation");
            query.whereEqualTo("userName", currentUser.getString("email"));
            query.getFirstInBackground(new GetCallback<UserInfo>() {
                public void done(UserInfo object, ParseException e) {
                    if (object == null) {
                        //error no info for that user
                    } else {

                        listener.onUserInfoDownloaded(object);
                        mUserInfo = object;

                        // Associate the device with a user


                        //send a test push notification
                        //                    HashMap<String, Object> params = new HashMap<>();
                        //
                        //                    params.put("userInfoId", "UNmof7YvDr");
                        //
                        //                    ParseCloud.callFunctionInBackground("sendNotification", params, new FunctionCallback<String>() {
                        //                        public void done(String result, ParseException e) {
                        //                            if (e == null) {
                        //
                        //                                System.out.println(result);
                        //                            }
                        //                        }
                        //                    });

                        // starts MainActivity

                    }

                }
            });
        } else {
            listener.onUserInfoDownloaded(mUserInfo);
        }
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

        listener2 = listener;
        if (mUserInfo == null) {
            whoCalled = false;
            getUserInfo(this);
        } else {
            getLocations();
        }

    }

    private void getLocations() {
        if (mLocations == null) {
            ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
            query.findInBackground(new FindCallback<Location>() {
                @Override
                public void done(List<Location> results, ParseException e) {
                    System.out.println("get locations");
                    if (e == null) {
                        System.out.println("get locations e == null");
                        mLocations = results;
                        if (listener2 != null) {
                            listener2.onLocationsDownloaded(results, mUserInfo.getCurrentLocation());
                        }
                    }
                }
            });
        }else {
            listener2.onLocationsDownloaded(mLocations, mUserInfo.getCurrentLocation());
        }
    }

    /**
     * On the first time of invoking, this method downloads and saves the wish list of locations.
     * Every subsequent call of this method the same list is used
     */
    public void getWishListOfLocations(final OnLocationsWishListDownloaded listener){

        listener1 = listener;
        if (mUserInfo == null) {
            whoCalled = true;
            getUserInfo(this);
        } else {
            getWishList();
        }
    }

    private void getWishList() {
        if (mWishList == null) {

            HashMap<String, Object> params = new HashMap<>();
            params.put("userInfoId", mUserInfo.getObjectId());

            ParseCloud.callFunctionInBackground("getWishList", params, new FunctionCallback<ArrayList<Location>>() {
                public void done(ArrayList<Location> result, ParseException e) {
                    System.out.println("get wishlist");
                    if (e == null) {
                        System.out.println("get wishlist e == null");
                        mWishList = result;
                        if (listener1 != null)
                            listener1.onLocationsWishListDownloaded(mWishList);
                    }
                }
            });
        }else {
            listener1.onLocationsWishListDownloaded(mWishList);
        }
    }

    @Override
    public void onUserInfoDownloaded(UserInfo userInfo) {
        mUserInfo = userInfo;
        if (whoCalled) {
            getWishList();
        } else {
            getLocations();
        }
    }
}
