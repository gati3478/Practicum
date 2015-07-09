package ge.edu.freeuni.practicum.view.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ge.edu.freeuni.practicum.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_info, container, false);

        TextView studentName = (TextView) view.findViewById(R.id.text_view_student_name);
        studentName.setText(getString(R.string.default_student_name));

        TextView location = (TextView) view.findViewById(R.id.text_view_assigned_location);
        location.setText(getString(R.string.default_student_location));

        TextView waveNum = (TextView) view.findViewById(R.id.text_view_wave_num);
        waveNum.setText("II");

        TextView departDate = (TextView) view.findViewById(R.id.text_view_departure_date);
        departDate.setText("28 ივლისი, 8:32 AM");

        TextView leaderName = (TextView) view.findViewById(R.id.text_view_grou_leader_name);
        leaderName.setText(getString(R.string.default_group_leader_name));

        TextView leaderEmail = (TextView) view.findViewById(R.id.text_view_leader_email);
        leaderEmail.setText(Html.fromHtml("<a href=\"mailto:gkink12@freeuni.edu.ge\">gkink12@freeuni.edu.ge</a>"));

        TextView leaderPhone = (TextView) view.findViewById(R.id.text_view_leader_phone_num);
        leaderPhone.setText(Html.fromHtml("<a href=\"tel:595473533\">595 47 35 33</a>"));

        return view;
    }

}
