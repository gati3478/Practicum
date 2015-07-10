package ge.edu.freeuni.practicum.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.fragment.tab.BasicInfoFragment;
import ge.edu.freeuni.practicum.view.fragment.tab.GroupInfoFragment;
import ge.edu.freeuni.practicum.view.fragment.tab.InventoryInfoFragment;

/**
 * Adapter for tabs.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tab_names[];

    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tab_names = context.getResources().getStringArray(R.array.tab_names);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return BasicInfoFragment.newInstance();
            case 1:
                return GroupInfoFragment.newInstance();
            case 2:
                return InventoryInfoFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_names[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

}
