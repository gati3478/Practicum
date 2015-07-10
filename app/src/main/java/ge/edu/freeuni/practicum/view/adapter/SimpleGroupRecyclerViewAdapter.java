package ge.edu.freeuni.practicum.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ge.edu.freeuni.practicum.R;

/**
 * Placeholder adapter for group members
 */
public class SimpleGroupRecyclerViewAdapter extends RecyclerView.Adapter<SimpleGroupRecyclerViewAdapter.ViewHolder> {

    private String[] mNames;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final View mView;
        public final CircleImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (CircleImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(R.id.group_member_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }

    }

    public SimpleGroupRecyclerViewAdapter(String[] names) {
        mNames = names;
    }

    @Override
    public SimpleGroupRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_group_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleGroupRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mNames[position]);

        holder.mImageView.setBorderWidth(8);
        if (position % 3 == 0)
            holder.mImageView.setBorderColorResource(R.color.amber700);

        else
            holder.mImageView.setBorderColorResource(R.color.lightGreen700);
    }

    @Override
    public int getItemCount() {
        return mNames.length;
    }

}
