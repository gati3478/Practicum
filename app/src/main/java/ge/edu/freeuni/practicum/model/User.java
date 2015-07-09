package ge.edu.freeuni.practicum.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Data model fot user
 * Created by Giorgi on 7/7/2015.
 */
//@ParseClassName("")
public class User extends ParseObject{

    private String firstName, lastName, email, password;
    private Location currentLocation;
    private List<Location> wishList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<Location> getWishList() {
        return wishList;
    }

    /**
     * Adds a new location into the list of desired locations
     * @param location  location to be added
     */
    public void addLocationToWishList(Location location){
        wishList.add(location);
    }

    /**
     * Removes a location from the list of desired locations
     * @param location  location to be removed
     */
    public void removeLocationFromWishList(Location location){
        wishList.remove(location);
    }
}
