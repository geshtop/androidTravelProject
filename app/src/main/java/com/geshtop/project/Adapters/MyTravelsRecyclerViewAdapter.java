package com.geshtop.project.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.TravelViewModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display .
 */
public class MyTravelsRecyclerViewAdapter extends RecyclerView.Adapter<MyTravelsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Travel> mValues;
    private  TravelViewModel mViewModel;
    public MyTravelsRecyclerViewAdapter(Context context, List<Travel> items,  TravelViewModel mViewModel) {
        mValues = items;
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_my_travels_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getTravelId());
        holder.mContentView.setText(mValues.get(position).getClientName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Travel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}