package ge.edu.freeuni.practicum.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ge.edu.freeuni.practicum.R;

/**
 * Advises users to use their university mails to log in
 * Created by Giorgi on 7/12/2015.
 */
public class InvalidEmailDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.error_invalid_email)
                .setPositiveButton(R.string.actioin_got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // just return to the LoginActivity
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
