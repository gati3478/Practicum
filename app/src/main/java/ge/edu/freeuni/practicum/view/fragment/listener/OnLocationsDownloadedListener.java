package ge.edu.freeuni.practicum.view.fragment.listener;

import java.util.List;

import ge.edu.freeuni.practicum.model.Location;

/**
 * Created by Giorgi on 7/14/2015.
 */
public interface OnLocationsDownloadedListener {

    void onLocationsDownloaded(List<Location> locations, Location currentLocation);

}
