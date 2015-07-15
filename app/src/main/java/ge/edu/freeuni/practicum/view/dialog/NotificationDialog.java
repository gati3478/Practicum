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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseUser;

import ge.edu.freeuni.practicum.R;

/**
 * Advises users to use their university mails to log in
 * Created by Giorgi on 7/12/2015.
 */
public class NotificationDialog extends DialogFragment {
    public static final String FULL_LOCATION = "FULL_LOCATION";

    private View mRootView;
    private String fullLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullLocation = getArguments().getString(FULL_LOCATION);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        mRootView = inflater.inflate(R.layout.notif_dialog, null);
        builder.setView(mRootView);

        // First example student (should also listen for updates on network)
        View elemView1 = inflater.inflate(R.layout.notif_dialog_elem, null);
        TextView textView1 = (TextView) elemView1.findViewById(R.id.text_view_student_name);
        textView1.setText(getString(R.string.default_student_name) + "1");
        Switch switchElem1 = (Switch) elemView1.findViewById(R.id.switch_indicator);
        switchElem1.setEnabled(false);

        ((LinearLayout) mRootView).addView(elemView1);


        // Second example student (should also listen for updates on network)
        View elemView2 = inflater.inflate(R.layout.notif_dialog_elem, null);
        TextView textView2 = (TextView) elemView2.findViewById(R.id.text_view_student_name);
        textView2.setText(getString(R.string.default_student_name) + "2");
        Switch switchElem2 = (Switch) elemView2.findViewById(R.id.switch_indicator);
        switchElem2.setChecked(true);
        switchElem2.setEnabled(false);

        ((LinearLayout) mRootView).addView(elemView2);

        // ME
        String firstName = ParseUser.getCurrentUser().getString("firstName");
        String lastName = ParseUser.getCurrentUser().getString("lastName");
        View elemView3 = inflater.inflate(R.layout.notif_dialog_elem, null);
        TextView textView3 = (TextView) elemView3.findViewById(R.id.text_view_student_name);
        textView3.setText(firstName + " " + lastName);
        Switch switchElem3 = (Switch) elemView3.findViewById(R.id.switch_indicator);
        switchElem3.setEnabled(true);
        switchElem3.setChecked(false);

        ((LinearLayout) mRootView).addView(elemView3);

        switchElem3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        /* Setting Title and content message */
        String msg = getString(R.string.dialog_exchange_notif_msg);
        builder.setMessage(fullLocation + msg).setTitle(R.string.dialog_exchange_notif_title);

        builder.setPositiveButton(R.string.actioin_got_it, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mListener.onDialogPositiveClick(NotificationDialog.this);
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
