package ge.edu.freeuni.practicum.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Data model for UserInfo
 * Created by Giorgi on 7/12/2015.
 */

@ParseClassName("UserInfo")
public class UserInfo extends ParseObject{

    public String  getEmail(){
        return getString("userName");
    }

    public Location getCurrentLocation(){
        return (Location)getParseObject("currentLocation");
    }

    public void setCurrentLocation(String currentLocationId){
        put("currentLocation", currentLocationId);
    }

}
