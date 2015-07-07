package ge.edu.freeuni.practicum.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.fragment.drawer.AboutFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.ExchangeFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.InfoFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.MainFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.NotificationsFragment;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private String mAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
        insertInitialFragment();
    }

    private void initInstances() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mAppName = getString(R.string.app_name);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void insertInitialFragment() {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, MainFragment.newInstance()).commit();
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_item_main:
                fragmentClass = MainFragment.class;
                break;
            case R.id.nav_item_information:
                fragmentClass = InfoFragment.class;
                break;
            case R.id.nav_item_change_location:
                fragmentClass = ExchangeFragment.class;
                break;
            case R.id.nav_item_notifs:
                fragmentClass = NotificationsFragment.class;
                break;
            case R.id.nav_item_about:
                fragmentClass = AboutFragment.class;
                break;
            default:
                fragmentClass = MainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        menuItem.setChecked(true);

        if (menuItem.getItemId() != R.id.nav_item_main)
            setTitle(menuItem.getTitle());
        else
            setTitle(mAppName);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

}
