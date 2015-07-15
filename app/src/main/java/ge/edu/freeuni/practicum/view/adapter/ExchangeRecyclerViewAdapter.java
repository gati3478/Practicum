package ge.edu.freeuni.practicum.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.model.Location;
import ge.edu.freeuni.practicum.view.fragment.drawer.ExchangeFragment;

/**
 * Placeholder adapter for group members
 */
public class ExchangeRecyclerViewAdapter extends RecyclerView.Adapter<ExchangeRecyclerViewAdapter.ViewHolder> implements ExchangeFragment.SetAdapterData {

    private Context mContext;
    private List<Location> mLocations;

    public ExchangeRecyclerViewAdapter(Context context) {
        mContext = context;
//        mPlaces = mContext.getResources().getStringArray(R.array.default_exchange_queries);
    }

    @Override
    public void setAdapterData(List<Location> locations) {
        mLocations = locations;
    }

    @Override
    public ExchangeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exchange_item, parent, false);
        return new ViewHolder(view);
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

    @Override
    public void onBindViewHolder(ExchangeRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mTextViewLoc.setText(mLocations.get(position).getName());
        holder.mTextViewWave.setText(arabicToRoman(mLocations.get(position).getWave()) + " " + mContext.getString(R.string.wave));

        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocations == null ? 0 : mLocations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mTextViewLoc;
        public final TextView mTextViewWave;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextViewLoc = (TextView) view.findViewById(R.id.exchange_target_location);
            mTextViewWave = (TextView) view.findViewById(R.id.exchange_target_wave);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextViewLoc.getText() + " " + mTextViewWave.getText();
        }

    }

}
