package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ge.edu.freeuni.practicum.R;

/**
 * Base fragment for drawer fragments
 */
public abstract class FragmentBase extends Fragment {
    Toolbar mToolbar;

    protected void initToolbar() {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        final ActionBar actionBar = parentActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
