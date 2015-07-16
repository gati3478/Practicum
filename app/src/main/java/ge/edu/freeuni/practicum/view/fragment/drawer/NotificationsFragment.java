package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.model.Cycle;
import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.model.UserInfo;
import ge.edu.freeuni.practicum.view.adapter.NotificationsRecyclerViewAdapter;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationDownloaded;
import ge.edu.freeuni.practicum.view.fragment.listener.OnNotificationsDownloaded;
import ge.edu.freeuni.practicum.view.fragment.listener.OnUserInfoDownloaded;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends FragmentBase implements OnNotificationsDownloaded, OnLocationDownloaded, OnUserInfoDownloaded {

    private RecyclerView mRecyclerView;
    private UserInfo mUserInfo;

    @Override
    public void onLocationDownloaded(Location location) {
        if (mRecyclerView != null)
            mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onUserInfoDownloaded(UserInfo userInfo) {
        mUserInfo = userInfo;
        if (getActivity() != null)
            ((App) getActivity().getApplication()).getNotifications(this);
    }

    public interface OnSetAdapterData {
        void onSetAdapterData(List<Cycle> list);
    }

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotificationsFragment.
     */
    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new NotificationsRecyclerViewAdapter(getActivity(), this));
        ((App) getActivity().getApplication()).getNewUserInfo(this);
    }

    private void initFragmentInstances() {
        /* Empty for now */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onNotificationsDownloaded(List<Cycle> cycles) {
        if (mRecyclerView != null) {
            try {
                ((OnSetAdapterData) mRecyclerView.getAdapter()).onSetAdapterData(cycles);
                mRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (ClassCastException e) {
                throw new ClassCastException(mRecyclerView.getAdapter().toString()
                        + " must implement OnSetAdapterData");
            }
        }
    }
}
