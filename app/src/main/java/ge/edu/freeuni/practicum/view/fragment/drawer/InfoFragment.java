package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;
import ge.edu.freeuni.practicum.view.fragment.tab.adapter.TabFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends FragmentBase {

    private CoordinatorLayout mRootLayout;
    private FloatingActionButton mFabBtn;

    private ViewPager mViewPager;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InfoFragment.
     */
    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.initToolbar();
        initInstances();
    }

    private void initInstances() {
        mRootLayout = (CoordinatorLayout) getActivity().findViewById(R.id.root_layout);

        mViewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mFabBtn = (FloatingActionButton) getActivity().findViewById(R.id.fab_btn);
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mRootLayout, "I'm just pressing buttons", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabFragmentAdapter adapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager(), getActivity());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
