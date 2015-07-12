package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.activity.LoginActivity;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends FragmentBase {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Do some stuff here */
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragmentInstances();
    }

    private void initFragmentInstances() {
        setTextsOnTextViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
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

    private void showLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    // converts arabic numerals to roman
    private String arabicToRoman(int arabic) {
        switch (arabic) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "VI";
        }
    }

    // sets texts to the variable text fields
    private void setTextsOnTextViews() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            showLoginScreen();
        }

        TextView studentName = (TextView) mRootLayout.findViewById(R.id.text_view_student_name);
        String firstName = currentUser.getString("firstName");
        String lastName = currentUser.getString("lastName");

        if (firstName != null && lastName != null)
            studentName.setText(firstName + " " + lastName);

        TextView location = (TextView) mRootLayout.findViewById(R.id.text_view_assigned_location);
        location.setText(((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getName());

        TextView waveNum = (TextView) mRootLayout.findViewById(R.id.text_view_wave_num);
        int wave = ((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getWave();
        waveNum.setText(arabicToRoman(wave));
    }

}
