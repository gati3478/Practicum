package ge.edu.freeuni.practicum.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Data model for Location
 * Created by Giorgi on 7/12/2015.
 */
@ParseClassName("Location")
public class Location extends ParseObject{

    /**
     *
     * @return  name of location
     */
    public String getName(){
        return getString("name");
    }

    /**
     *
     * @return  wave of location
     */
    public int getWave(){
        return getInt("wave");
    }

    /**
     *
     * @return  departure time
     */
    public String getDate(){
        return getString("departureTime");
    }

}
