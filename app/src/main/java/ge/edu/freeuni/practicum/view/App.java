package ge.edu.freeuni.practicum.view;

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
        Parse.initialize(this, "mPaILpJR8k0zyaOk5W8Pmywrcs7EE3gYSx2OMajL", "KOctn0qjjJr0TWTN2MZWmcC9pE4va9Wd5tk1OsNK");
    }

}
