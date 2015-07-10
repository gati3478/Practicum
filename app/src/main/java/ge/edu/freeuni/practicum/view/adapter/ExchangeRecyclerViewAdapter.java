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
import android.widget.Toast;

import ge.edu.freeuni.practicum.R;

/**
 * Placeholder adapter for group members
 */
public class ExchangeRecyclerViewAdapter extends RecyclerView.Adapter<ExchangeRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    String[] mPlaces;

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

    public ExchangeRecyclerViewAdapter(Context context) {
        mContext = context;
        mPlaces = mContext.getResources().getStringArray(R.array.default_exchange_queries);
    }

    @Override
    public ExchangeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_exchange_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExchangeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextViewLoc.setText(mPlaces[position]);

        if (position % 4 == 0)
            holder.mTextViewWave.setText("ნაკადი III");
        else if (position % 4 == 2)
            holder.mTextViewWave.setText("ნაკადი I");
        else
            holder.mTextViewWave.setText("ნაკადი II");

        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Toast.makeText(mContext, "what.ever", Toast.LENGTH_SHORT).show();
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.length;
    }

}
