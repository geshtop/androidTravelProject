package com.geshtop.project.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.TravelViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class AViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView companyName;
    TextView comapnyAddedDate;
    TextView cidTextView;
    Button confirmButton;
    public AViewHolder(View itemView) {
        super(itemView);
        companyName = (TextView)itemView.findViewById(R.id.companyName);
        comapnyAddedDate = (TextView) itemView.findViewById(R.id.comapnyAddedDate);
        cidTextView = (TextView) itemView.findViewById(R.id.cidTextView);
        confirmButton = (Button) itemView.findViewById(R.id.confirmButton);
        //itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}

public class CompanyAdapter extends RecyclerView.Adapter<AViewHolder> {

    private Context context;
    private List<TravelCompany> items;
    private TravelViewModel mViewModel;
    private SimpleDateFormat sdf;
    private Travel currentTravel;
    private LayoutInflater mInflater;
    public CompanyAdapter(Context context, Travel travel,  TravelViewModel mViewModel) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.items = travel.getCompanies();
        this.currentTravel = travel;
        this.mViewModel = mViewModel;
        sdf = new SimpleDateFormat("dd/MM/yy");

    }

    // inflates the row layout from xml when needed
    @Override
    public AViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_my_travels_company_item, parent, false);
        return new AViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AViewHolder viewHolder, int position) {
        TravelCompany tc = items.get(position);
        TravelCompany currentTravelCompany = (TravelCompany) getItem(position);

        if (currentTravelCompany != null) {
            User currentUser = mViewModel.getCurrentUser();
            viewHolder.companyName.setText(currentTravelCompany.getName());
            viewHolder.comapnyAddedDate.setText( sdf.format(currentTravelCompany.getCreatedDate()));
            Integer  temp = position + 1;
            viewHolder.cidTextView.setText(temp.toString());
            // viewHolder.cidTextView.setText(currentTravelCompany.getCid());

            viewHolder.confirmButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        currentTravelCompany.setApproved(true);
                        currentTravelCompany.setApprovedDate(new Date());
                        currentTravel.setRequestType(RequestType.Done);
                        currentTravel.setAcceptedByName(currentTravelCompany.getName());
                        currentTravel.setAcceptedByEmail(currentTravelCompany.getEmail());
                        mViewModel.updateTravel(currentTravel);

//                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
//                        currentItem.addSingleCompany(tc, isChecked);
//                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, "updated travel company ", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }


    // stores and recycles views as they are scrolled off screen


//    // convenience method for getting data at click position
    TravelCompany getItem(int id) {
        return items.get(id);
    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
}