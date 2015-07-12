package ge.edu.freeuni.practicum.view.fragment.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasicInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicInfoFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BasicInfoFragment.
     */

    public static BasicInfoFragment newInstance() {
        return new BasicInfoFragment();
    }

    public BasicInfoFragment() {
        // Required empty public constructor
    }

    //shows LoginActivity
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

    //displays number of students in a location
    private void numberOfStudents(final View view){
        final TextView groupSize = (TextView) view.findViewById(R.id.text_view_group_size);

        ParseQuery innerQuery = new ParseQuery("Location");
        innerQuery.whereEqualTo("objectId", ((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getObjectId());

        if (((App) getActivity().getApplication()).getNumberOfStudents() == -1) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
            query.whereMatchesQuery("currentLocation", innerQuery);
            query.countInBackground(new CountCallback() {
                public void done(int count, ParseException e) {
                    if (e == null) {
                        ((App) getActivity().getApplication()).setNumberOfStudents(count);
                        groupSize.setText("" + count);
                    } else {
                        // The request failed
                    }
                }
            });
        }else{
            groupSize.setText("" + ((App) getActivity().getApplication()).getNumberOfStudents());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            showLoginScreen();
        }

        String firstName = currentUser.getString("firstName");
        String lastName = currentUser.getString("lastName");

        TextView studentName = (TextView) view.findViewById(R.id.text_view_student_name);

        //to avoid null-pointer exception
        if (firstName != null && lastName != null)
            studentName.setText(firstName + " " + lastName);

        TextView location = (TextView) view.findViewById(R.id.text_view_assigned_location);
        location.setText(((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getName());

        TextView waveNum = (TextView) view.findViewById(R.id.text_view_wave_num);
        int wave = ((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getWave();
        waveNum.setText(arabicToRoman(wave));

        numberOfStudents(view);

        TextView departDate = (TextView) view.findViewById(R.id.text_view_departure_date);
        departDate.setText(((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getDate());

        TextView flatSurface = (TextView) view.findViewById(R.id.text_view_flat_surface);
        flatSurface.setText(getString(R.string.affirmative_exists));

        TextView lavatory = (TextView) view.findViewById(R.id.text_view_lavatory);
        lavatory.setText(getString(R.string.negative_not_exists));

        TextView shower = (TextView) view.findViewById(R.id.text_view_shower);
        shower.setText(getString(R.string.negative_not_exists));

        TextView averageTemp = (TextView) view.findViewById(R.id.text_view_average_temperature);
        averageTemp.setText("28-30 C");

        TextView leaderName = (TextView) view.findViewById(R.id.text_view_grou_leader_name);
        leaderName.setText(getString(R.string.default_group_leader_name));

        TextView leaderEmail = (TextView) view.findViewById(R.id.text_view_leader_email);
        leaderEmail.setText(Html.fromHtml("<a href=\"mailto:gkink12@freeuni.edu.ge\">gkink12@freeuni.edu.ge</a>"));

        TextView leaderPhone = (TextView) view.findViewById(R.id.text_view_leader_phone_num);
        leaderPhone.setText(Html.fromHtml("<a href=\"tel:595473533\">595 47 35 33</a>"));

        TextView additionalNotes = (TextView) view.findViewById(R.id.text_view_additional_notes);
        additionalNotes.setText(getString(R.string.no_additional_notes));

        return view;
    }

}
