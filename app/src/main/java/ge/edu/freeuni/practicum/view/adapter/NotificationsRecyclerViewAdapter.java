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

import java.util.ArrayList;
import java.util.List;

import ge.edu.freeuni.practicum.R;
import ge.edu.freeuni.practicum.model.Cycle;
import ge.edu.freeuni.practicum.view.dialog.NotificationDialog;
import ge.edu.freeuni.practicum.view.fragment.drawer.NotificationsFragment;

/**
 * Placeholder adapter for group members
 */
public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> implements NotificationsFragment.OnSetAdapterData {

    private Context mContext;
    private NotificationsFragment mFrag;
    private List<Cycle> mCycles = null;

    public NotificationsRecyclerViewAdapter(Context context, NotificationsFragment frag) {
        mContext = context;
        mFrag = frag;
    }

    @Override
    public NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_notif_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextViewLoc.setText(mCycles.get(position).getLocation());


        holder.mTextViewWave.setText(mCycles.get(position).getWave());
        if (mCycles.get(position).isAgreed()) {
            holder.mImageView.setImageResource(R.drawable.ic_cycle_on);
        } else {
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
//                        bundle.putStringArrayList();
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
        return mCycles == null ? 0 : mCycles.size();
    }

    @Override
    public void onSetAdapterData(List<Cycle> list) {
        mCycles = list;
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
