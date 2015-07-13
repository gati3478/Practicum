package ge.edu.freeuni.practicum.view.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.adapter.SimpleGroupRecyclerViewAdapter;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupInfoFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public interface SetAdapterData{
        void setAdapterData(String[] names);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BasicInfoFragment.
     */
    public static GroupInfoFragment newInstance() {
        return new GroupInfoFragment();
    }

    public GroupInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView(mRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_group_info, container, false);
        return mRecyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleGroupRecyclerViewAdapter(new String[]{}));

        if (((App) getActivity().getApplication()).getGroup() == null){
            downloadGroups();
        }else {
            updateAdapter(recyclerView.getAdapter(), ((App) getActivity().getApplication()).getGroup());
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    private void downloadGroups(){

        HashMap<String, Object> params = new HashMap<>();
        params.put("locationId", ((App) getActivity().getApplication()).getUserInfo().getCurrentLocation().getObjectId());
        final App app = ((App) getActivity().getApplication());

        ParseCloud.callFunctionInBackground("studentsByLocation", params, new FunctionCallback<ArrayList<ParseUser>>() {
            public void done(ArrayList<ParseUser> result, ParseException e) {
                if (e == null) {

                    String [] fullNames = new String[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        fullNames[i] = result.get(i).getString("firstName") + " " + result.get(i).getString("lastName");
                    }

                    app.setGroup(fullNames);

                    if (mRecyclerView != null) {
                        updateAdapter(mRecyclerView.getAdapter(), fullNames);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });

    }

    //updates group info list after the list is downloaded
    private void updateAdapter(RecyclerView.Adapter adapter, String[] data){
        try {
            ((SetAdapterData) adapter).setAdapterData(data);
        } catch (ClassCastException e) {
            throw new ClassCastException(adapter.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

}
