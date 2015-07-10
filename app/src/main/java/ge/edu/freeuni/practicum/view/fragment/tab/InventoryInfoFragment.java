package ge.edu.freeuni.practicum.view.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ge.edu.freeuni.practicum.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryInfoFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BasicInfoFragment.
     */
    public static InventoryInfoFragment newInstance() {
        return new InventoryInfoFragment();
    }

    public InventoryInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory_info, container, false);

        TextView groupSize = (TextView) view.findViewById(R.id.text_view_inventory_info);
        groupSize.setText(getString(R.string.default_inventory));

        return view;
    }

}
