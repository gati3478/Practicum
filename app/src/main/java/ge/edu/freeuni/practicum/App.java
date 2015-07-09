package ge.edu.freeuni.practicum;

import android.app.Application;

import com.parse.Parse;

/**
 * Application class.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.app_id), getResources().getString(R.string.client_key));
    }

}
