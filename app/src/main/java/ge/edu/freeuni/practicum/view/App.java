package ge.edu.freeuni.practicum.view;

import android.app.Application;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

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

        ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    System.out.println(result);
                }
            }
        });
    }

}
