package ge.edu.freeuni.practicum.view.fragment.drawer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;
import java.util.List;

import ge.edu.freeuni.practicum.App;
import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.view.adapter.ExchangeRecyclerViewAdapter;
import ge.edu.freeuni.practicum.view.dialog.RequestExchangeDialog;
import ge.edu.freeuni.practicum.view.fragment.listener.OnFragmentInteractionListener;
import ge.edu.freeuni.practicum.view.fragment.listener.OnLocationsWishListDownloaded;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExchangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExchangeFragment extends FragmentBase implements RequestExchangeDialog.NoticeDialogListener,
        OnLocationsWishListDownloaded {

    private RecyclerView mRecyclerView;
    private List<Location> mWishList = null;

    public ExchangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExchangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExchangeFragment newInstance() {
        ExchangeFragment fragment = new ExchangeFragment();
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
    }

    private void initFragmentInstances() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new ExchangeRecyclerViewAdapter(getActivity()));

        ((App) getActivity().getApplication()).getWishListOfLocations(this);

        FloatingActionButton fabBtn = (FloatingActionButton) getActivity().findViewById(R.id.fab_btn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestExchangeDialog dialog = new RequestExchangeDialog();
                dialog.setListener(ExchangeFragment.this);
                dialog.show(ExchangeFragment.this.getChildFragmentManager(), "requestExchangeDialog");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    private void sendNotification() {
        //send a test push notification
        HashMap<String, Object> params = new HashMap<>();
        params.put("userInfoId", "UNmof7YvDr");
        ParseCloud.callFunctionInBackground("sendNotification", params, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {

                    System.out.println(result);
                }
            }
        });
    }

    @Override
    public void onDialogPositiveClick(RequestExchangeDialog dialog) {
        Location loc = dialog.getPreferredLocation();
        sendNotification();
        //TODO send info to Parse.com
        /* Notifying user */
        Snackbar.make(mRootLayout, getString(R.string.plus_btn_snackbar), Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWishList.remove(mWishList.size() - 1);
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                })
                .show();

        /* Refreshing RecyclerView */
        if (mWishList != null) {
            mWishList.add(loc);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick(RequestExchangeDialog dialog) {

    }

    @Override
    public void onLocationsWishListDownloaded(List<Location> wishList) {

        mWishList = wishList;
        if (mRecyclerView != null) {
            updateAdapter(mRecyclerView.getAdapter(), wishList);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    //updates wish list after the list is downloaded
    private void updateAdapter(RecyclerView.Adapter adapter, List<Location> locations) {
        try {
            ((SetAdapterData) adapter).setAdapterData(locations);
        } catch (ClassCastException e) {
            throw new ClassCastException(adapter.toString()
                    + " must implement SetAdapterData");
        }
    }

    public interface SetAdapterData {
        void setAdapterData(List<Location> locations);
    }
}
