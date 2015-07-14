package ge.edu.freeuni.practicum.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsDownloadedListener;

/**
 * Advises users to use their university mails to log in
 * Created by Giorgi on 7/12/2015.
 */
public class RequestExchangeDialog extends DialogFragment implements AdapterView.OnItemSelectedListener,
        OnLocationsDownloadedListener{

    private Map<String, List<Integer>> mLocationWaveMap;

    @Override
    public void onLocationsDownloaded(List<Location> locations, Location currentLocation) {
        mLocationWaveMap = new HashMap<>();

        //locations for the locationSpinner
        List<String > spinnerLocations = new ArrayList<>();

        for (int i = 0; i < locations.size(); i++) {
            Location location =  locations.get(i);

            if (location.getObjectId().equals(currentLocation.getObjectId()))
                continue;

            if (mLocationWaveMap.containsKey(location.getName())){
                mLocationWaveMap.get(location.getName()).add(location.getWave());
            }else {
                List<Integer> temp = new ArrayList<>();
                temp.add(location.getWave());
                mLocationWaveMap.put(location.getName(), temp);
                spinnerLocations.add(location.getName());
            }
        }

        /* Setting up location spinner */
        setupLocationSpinner(spinnerLocations);

     }

    /* The acitivty/fragment that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(RequestExchangeDialog dialog);

        void onDialogNegativeClick(RequestExchangeDialog dialog);
    }

    // Use this instance of the interface to deliver action events
    private NoticeDialogListener mListener;

    private View mRootView;

    /* Temp */
    private String prefLocation;
    private int prefWave;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mRootView = inflater.inflate(R.layout.request_exchange_dialog, null);
        builder.setView(mRootView);

        ((App)getActivity().getApplication()).getLocations(this);

        /* Setting Title and content message */
        builder.setMessage(R.string.dialog_exchange_request_msg).setTitle(R.string.dialog_exchange_request_title);

        builder.setPositiveButton(R.string.dialog_exchange_request, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogPositiveClick(RequestExchangeDialog.this);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // RequestExchangeDialog.this.getDialog().cancel();
                mListener.onDialogNegativeClick(RequestExchangeDialog.this);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void setupLocationSpinner(List<String> locations) {
        Spinner locationSpinner = (Spinner) mRootView.findViewById(R.id.spinner_location);

        /* Setting adapter for the first spinner */
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, locations);

        // Specify the layout to use when the list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(this);
    }

    private void setupWaveSpinner(List<String> waves) {
        Spinner waveSpinner = (Spinner) mRootView.findViewById(R.id.spinner_wave);

        /* Setting adapter for the second spinner */
        ArrayAdapter<String> adapterWaves = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, waves);

        // Specify the layout to use when the list of choices appears
        adapterWaves.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        waveSpinner.setAdapter(adapterWaves);
        waveSpinner.setOnItemSelectedListener(this);
    }

    public void setListener(NoticeDialogListener listener) {
        this.mListener = listener;
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

    // converts roman numerals to arabic
    private int romanToArabic(String roman) {
        switch (roman) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            default:
                return 6;
        }
    }

    //generates a list of String waves by a list of Integer waves
    private List<String> generateWaveList(List<Integer> wave){
        Collections.sort(wave);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < wave.size(); i++) {
            Integer integer = wave.get(i);
            result.add(arabicToRoman(integer) + " " + getString(R.string.wave));
        }
        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinner_location) {

            /* Setting up location spinner */
            setupWaveSpinner(generateWaveList(mLocationWaveMap.get(parent.getItemAtPosition(position).toString())));
            prefLocation = parent.getItemAtPosition(position).toString();
        }else {

            String[] waveSplit = parent.getItemAtPosition(position).toString().split(" ");
            prefWave = romanToArabic(waveSplit[0]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getPreferredLocation() {
        return prefLocation;
    }

    public int getPreferredWave() {
        return prefWave;
    }

}
