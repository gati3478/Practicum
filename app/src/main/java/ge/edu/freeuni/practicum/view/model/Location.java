package ge.edu.freeuni.practicum.view.model;

/**
 * Model class for location
 * Created by Giorgi on 7/7/2015.
 */
public class Location {

    private String name;
    private int wave;

    public Location(String name, int wave){

    }

    public int getWave() {
        return wave;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
