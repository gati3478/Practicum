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

import ge.edu.freeuni.practicum.R;

/**
 * Advises users to use their university mails to log in
 * Created by Giorgi on 7/12/2015.
 */
public class RequestExchangeDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

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
    private String prefWave;

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

        /* Setting up spinners */
        setupLocationSpinner();
        setupWaveSpinner();

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

    private void setupLocationSpinner() {
        Spinner locationSpinner = (Spinner) mRootView.findViewById(R.id.spinner_location);

         /* Setting adapter for the first spinner */
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.default_exchange_queries, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(this);
    }

    private void setupWaveSpinner() {
        Spinner waveSpinner = (Spinner) mRootView.findViewById(R.id.spinner_wave);

        /* Setting adapter for the second spinner */
        ArrayAdapter<CharSequence> adapterWaves = ArrayAdapter.createFromResource(getActivity(),
                R.array.default_waves_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterWaves.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        waveSpinner.setAdapter(adapterWaves);
        waveSpinner.setOnItemSelectedListener(this);
    }

    public void setListener(NoticeDialogListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        if (parent.getId() == R.id.spinner_location)
            prefLocation = parent.getItemAtPosition(position).toString();
        else
            prefWave = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getPreferredLocation() {
        return prefLocation;
    }

    public String getPreferredWave() {
        return prefWave;
    }

}
