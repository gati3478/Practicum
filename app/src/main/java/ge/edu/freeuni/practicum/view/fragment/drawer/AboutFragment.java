package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends FragmentBase {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private CoordinatorLayout mRootLayout;
    private FloatingActionButton mFabBtn;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutFragment() {
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

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
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
        return inflater.inflate(R.layout.fragment_about, container, false);
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
