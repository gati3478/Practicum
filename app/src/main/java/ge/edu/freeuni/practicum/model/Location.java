package ge.edu.freeuni.practicum.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Data model for Location
 * Created by Giorgi on 7/12/2015.
 */
@ParseClassName("Location")
public class Location extends ParseObject{

    public String getName(){
        return getString("name");
    }

    public int getWave(){
        return getInt("wave");
    }

}
