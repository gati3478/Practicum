package ge.edu.freeuni.practicum.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data model for Cycle
 * Created by Giorgi on 7/16/2015.
 */

public class Cycle {

    private Location mLocation = null;
    private String mId;
    private List<HashMap<String, String>> mCycle;
    private boolean isAgreed = false;

    public Cycle(String id, List<HashMap<String, String>> cycle) {
        mId = id;
        mCycle = cycle;
    }

    // converts arabic numerals to roman
    private String arabicToRoman(int arabic) {
        switch (arabic) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "VI";
        }
    }

    public String getLocation() {
        return mLocation == null ? "" : mLocation.getName();
    }

    public String getWave() {
        return mLocation == null ? "" : arabicToRoman(mLocation.getWave()) + " ნაკადი";
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setCycle(ArrayList<HashMap<String, String>> mCycle) {
        this.mCycle = mCycle;
    }

    public List<HashMap<String, String>> getCycle() {
        return mCycle;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public Location getmLocation() {
        return mLocation;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

    public boolean isAgreed() {
        return isAgreed;
    }
}
