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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ge.edu.freeuni.practicum.model.Cycle;
import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.model.UserInfo;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationDownloaded;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsDownloadedListener;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsWishListDownloaded;
import ge.edu.freeuni.practicum.view.fragment.listener.OnNotificationsDownloaded;
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

        ParseInstallation.getCurrentInstallation().saveInBackground();

        HashMap<String, Object> params = new HashMap<>();
        params.put("cycle", "JrTcLgUUhD");
        params.put("email", "gkink12@freeuni.edu.ge");
        params.put("decision", "JrTcLgUUhD");

        ParseCloud.callFunctionInBackground("updateCycle", params, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
//                    for (int i = 0; i < result.size(); i++) {
//                        for (int j = 0; j < result.get(i).size(); j++){
//                            System.out.println(result.get(i).get(j).get("user"));
//                            System.out.println(result.get(i).get(j).get("agreed"));
//                            System.out.println(result.get(i).get(j).get("cycleId"));
//                            System.out.println(result.get(i).get(j).get("locationId"));
//
//                        }
//                        System.out.println("##############");
//                    }
                    System.out.println(result);
                }
            }
        });

//        HashMap<String, Object> params = new HashMap<>();
//        params.put("locationId", "nQb9weOv0h");
//        params.put("email", "gkink12@freeuni.edu.ge");
//
//        ParseCloud.callFunctionInBackground("findCycles", params, new FunctionCallback<ArrayList<String>>() {
//            public void done(ArrayList<String> result, ParseException e) {
//                if (e == null) {
//                    for (int i = 0; i < result.size(); i++) {
//
//                            System.out.println(result.get(i));
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
            query.include("approvedCycle");
            query.whereEqualTo("userName", currentUser.getString("email"));
            query.getFirstInBackground(new GetCallback<UserInfo>() {
                public void done(UserInfo object, ParseException e) {
                    if (object == null) {
                        //error no info for that user
                    } else {

                        listener.onUserInfoDownloaded(object);
                        mUserInfo = object;
                    }
                }
            });
        } else {
            listener.onUserInfoDownloaded(mUserInfo);
        }
    }

    /**
     * @return number of students in the user's current location
     */
    public int getNumberOfStudents() {
        return mNumberOfStudentsInLocation;
    }

    /**
     * sets number of students in the user's current location
     *
     * @param numberOfStudents number of students to be set
     */
    public void setNumberOfStudents(int numberOfStudents) {
        mNumberOfStudentsInLocation = numberOfStudents;
    }

    /**
     * @return returns the group of people in the user's current location
     */
    public String[] getGroup() {
        return mGroup;
    }

    /**
     * sets the group of people in the user's current location
     *
     * @param group group of students to be set
     */
    public void setGroup(String[] group) {
        mGroup = group;
    }

    /**
     * On the first time of invoking, this method downloads and saves the whole list of locations.
     * Every subsequent call of this method the same list is used
     */
    public void getLocations(final OnLocationsDownloadedListener listener) {

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

                    if (e == null) {

                        mLocations = results;
                        if (listener2 != null) {
                            listener2.onLocationsDownloaded(results, mUserInfo.getCurrentLocation());
                        }
                    }
                }
            });
        } else {
            listener2.onLocationsDownloaded(mLocations, mUserInfo.getCurrentLocation());
        }
    }

    /**
     * On the first time of invoking, this method downloads and saves the wish list of locations.
     * Every subsequent call of this method the same list is used
     */
    public void getWishListOfLocations(final OnLocationsWishListDownloaded listener) {

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

                    if (e == null) {

                        mWishList = result;
                        if (listener1 != null)
                            listener1.onLocationsWishListDownloaded(mWishList);
                    }
                }
            });
        } else {
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

    public void getNotifications(final OnNotificationsDownloaded listener) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("email", ParseUser.getCurrentUser().getEmail());

        final List<Cycle> cycles = new ArrayList<>();

        ParseCloud.callFunctionInBackground("getNotifications", params, new FunctionCallback<ArrayList<ArrayList<HashMap<String, String>>>>() {
            public void done(ArrayList<ArrayList<HashMap<String, String>>> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {
//                        System.out.println(i);
                        cycles.add(new Cycle(result.get(i).get(0).get("cycleId"), result.get(i)));
                        getNotificationName(result.get(i), i, cycles, (OnLocationDownloaded) listener);
//                        for (int j = 0; j < result.get(i).size(); j++) {
//                            System.out.println(result.get(i).get(j).get("user"));
//                            System.out.println(result.get(i).get(j).get("agreed"));
//                            System.out.println(result.get(i).get(j).get("cycleId"));
//                        }
                    }
                    listener.onNotificationsDownloaded(cycles);
                }
            }
        });

    }

    private void getNotificationName(final ArrayList<HashMap<String, String>> cycle, final int num, final List<Cycle> cycles, final OnLocationDownloaded locationDownloaded) {
        for (int i = 0; i < cycle.size(); i++) {
            HashMap<String, String> temp = cycle.get(i);
            if (temp.get("user").equals(ParseUser.getCurrentUser().getEmail())) {

                ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
                query.getInBackground(temp.get("locationId"), new GetCallback<Location>() {
                    public void done(Location object, ParseException e) {
                        if (e == null) {
                            cycles.get(num).setLocation(object);
//                            System.out.println(cycles.get(num).getId() + " " + mUserInfo.getApprovedCycle().getObjectId());
                            if (mUserInfo.getApprovedCycle() != null && cycles.get(num).getId().equals(mUserInfo.getApprovedCycle().getObjectId()))
                                cycles.get(num).setAgreed(true);
                            locationDownloaded.onLocationDownloaded(object);
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        }
    }

    /**
     * Downloads a user info from Parse.com
     */
    public void getNewUserInfo(final OnUserInfoDownloaded listener) {

        ParseUser currentUser = ParseUser.getCurrentUser();

        ParseQuery<UserInfo> query = ParseQuery.getQuery(UserInfo.class);
        query.include("currentLocation");
        query.include("approvedCycle");
        query.whereEqualTo("userName", currentUser.getString("email"));
        query.getFirstInBackground(new GetCallback<UserInfo>() {
            public void done(UserInfo object, ParseException e) {
                if (object == null) {
                    //error no info for that user
                } else {

                    listener.onUserInfoDownloaded(object);
                    mUserInfo = object;
                }
            }
        });
    }
}
