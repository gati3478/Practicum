package ge.edu.freeuni.practicum.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.fragment.drawer.ExchangeFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.InfoFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.MainFragment;
import ge.edu.freeuni.practicum.view.fragment.drawer.NotificationsFragment;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private DrawerLayout mDrawerLayout;
    private Menu mMenu;
    private String mAppName;
    private static final String SELECTED_MENU_ITEM_ID = "ge.edu.freeuni.practicum.SELECTED_ITEM_ID";
    private int mCurrMenuItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ParseUser.getCurrentUser() == null) {
            System.out.println("null");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            System.out.println("not null");
            initInstances();
            setOnDrawerStudentName();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrMenuItemId = savedInstanceState.getInt(SELECTED_MENU_ITEM_ID, R.id.nav_item_main);
    }

    private void initInstances() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null)
            setupDrawerContent(navigationView);

        mAppName = getString(R.string.app_name);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        final MenuItem fItem = menuItem;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                selectDrawerItem(fItem);
                            }
                        }, 200);
                        return true;
                    }
                });
        mMenu = navigationView.getMenu();
    }

    private void setOnDrawerStudentName() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null)
            showLoginScreen();

        TextView userFullName = (TextView) mDrawerLayout.findViewById(R.id.name_student);

        String firstName = currentUser.getString("firstName");
        String lastName = currentUser.getString("lastName");

        if (firstName != null && lastName != null)
            userFullName.setText(firstName + " " + lastName);
    }

    private void showLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        insertInitialFragment();
    }

    private void insertInitialFragment() {
        // Insert the fragment by replacing any existing fragment
        Fragment fragment = null;

        Class fragmentClass;
        switch (mCurrMenuItemId) {
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
            default:
                fragmentClass = MainFragment.class;
                mCurrMenuItemId = R.id.nav_item_main;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment == null)
            return;

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        MenuItem menuItem = mMenu.findItem(mCurrMenuItemId);
        if (mCurrMenuItemId == R.id.nav_item_main)
            setTitle(mAppName);
        else
            setTitle(menuItem.getTitle());
        menuItem.setChecked(true);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        if (menuItem.getItemId() == mCurrMenuItemId)
            return;

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
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return;
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

        if (menuItem.getItemId() != R.id.nav_item_main)
            setTitle(menuItem.getTitle());
        else
            setTitle(mAppName);

        mCurrMenuItemId = menuItem.getItemId();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_MENU_ITEM_ID, mCurrMenuItemId);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

    @Override
    public void onBackPressed() {
        /* Tricky fix for onBackPressed weird behavior */
        moveTaskToBack(true);
    }

}
