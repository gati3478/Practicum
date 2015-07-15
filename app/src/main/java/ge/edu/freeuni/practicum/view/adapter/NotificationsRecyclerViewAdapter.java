package ge.edu.freeuni.practicum.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.view.dialog.NotificationDialog;
import ge.edu.freeuni.practicum.view.fragment.drawer.NotificationsFragment;

/**
 * Placeholder adapter for group members
 */
public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private NotificationsFragment mFrag;
    private String[] mPlaces;

    public NotificationsRecyclerViewAdapter(Context context, NotificationsFragment frag) {
        mContext = context;
        mFrag = frag;
        mPlaces = mContext.getResources().getStringArray(R.array.default_exchange_queries);
    }

    @Override
    public NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notif_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextViewLoc.setText(mPlaces[position]);

        if (position % 4 == 0) {
            holder.mTextViewWave.setText("ნაკადი III");
            holder.mImageView.setImageResource(R.drawable.ic_cycle_off);
        } else if (position % 4 == 2) {
            holder.mTextViewWave.setText("ნაკადი I");
            holder.mImageView.setImageResource(R.drawable.ic_cycle_on);
        } else {
            holder.mTextViewWave.setText("ნაკადი II");
            holder.mImageView.setImageResource(R.drawable.ic_cycle_off);
        }

        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 20, 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        String fullLocation = holder.mTextViewLoc.getText() + " " + holder.mTextViewWave.getText();
                        NotificationDialog dialog = new NotificationDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString(NotificationDialog.FULL_LOCATION, fullLocation);
                        dialog.setArguments(bundle);
                        dialog.show(mFrag.getChildFragmentManager(), "NotificationsFragment");
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextViewLoc;
        public final TextView mTextViewWave;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.exchange_icon);
            mTextViewLoc = (TextView) view.findViewById(R.id.exchange_target_location);
            mTextViewWave = (TextView) view.findViewById(R.id.exchange_target_wave);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextViewLoc.getText() + " " + mTextViewWave.getText();
        }

    }

}
