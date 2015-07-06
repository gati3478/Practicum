package ge.edu.freeuni.practicum.view.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
    private ActionBarDrawerToggle mDrawerToggle;
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
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_close, R.string.drawer_open);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mAppName = getString(R.string.app_name);
    }

    private void insertInitialFragment() {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, MainFragment.newInstance()).commit();
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
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState(); // THIS BASTARD

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_sign_out:
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }
}
