package com.geshtop.project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.TravelViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyTravelsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private TravelViewModel mViewModel;
    private SimpleDateFormat sdf;

    public MyTravelsAdapter(Context context, ArrayList<Travel> items, TravelViewModel mViewModel) {
        this.context = context;
        this.items = items;
        this.mViewModel = mViewModel;
        sdf = new SimpleDateFormat("dd/MM/yy");
    }
    @Override
    public int getCount() {
        return items.size(); //returns total item in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns the item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyTravelsAdapter.ViewHolder viewHolder;

        //if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_my_travels_item, parent, false);
            viewHolder = new MyTravelsAdapter.ViewHolder(convertView);
        //    convertView.setTag(viewHolder);
        //} else {
        //    viewHolder = (MyTravelsAdapter.ViewHolder) convertView.getTag();
        //}

        Travel currentItem = (Travel) getItem(position);

        if (currentItem != null) {

            if(currentItem.getTravelLocation()!= null && currentItem.getDestinations().size() > 0)
                viewHolder.travelLocation.setText(currentItem.getTravelLocation().toString()  + "-" + currentItem.getDestinations().get(0).toString() );
            viewHolder.traveStatus.setText(currentItem.getRequestType().toString());
            viewHolder.travelDate.setText( sdf.format(currentItem.getTravelDate()) + "-" +  sdf.format(currentItem.getArrivalDate()) );
            if(currentItem.getCompanies().size() >0) {
                viewHolder.approvedIcon.setVisibility(View.VISIBLE);
                viewHolder.notApprovedIcon.setVisibility(View.GONE);
                CompaniesAdapter companyAdapter = new CompaniesAdapter(context,currentItem , mViewModel);
                viewHolder.companiesListView.setAdapter(companyAdapter);
            }else{
                viewHolder.companiesListView.setVisibility(View.GONE);
                viewHolder.approvedIcon.setVisibility(View.GONE);
                viewHolder.notApprovedIcon.setVisibility(View.VISIBLE);
            }




//            viewHolder.acceptButton.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    if (currentUser != null) {
//                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
//                        currentItem.addSingleCompany(tc, true);
//                        mViewModel.updateTravel(currentItem);
//                        Toast.makeText(context, currentUser.name + " update successfullt the travel", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });


        }


        return convertView;
    }


    public class ViewHolder  {
        public final View mView;
        public final TextView travelDate;
        public final TextView travelLocation;
        public final TextView traveStatus;
        public final ImageView approvedIcon;
        public final ImageView notApprovedIcon;

        public final ListView companiesListView;
        public ViewHolder(View view) {
            mView = view;
            travelDate = (TextView) view.findViewById(R.id.travelDate);
            travelLocation = (TextView) view.findViewById(R.id.travelLocation);
            approvedIcon = (ImageView) view.findViewById(R.id.approvedIcon);
            notApprovedIcon = (ImageView) view.findViewById(R.id.notApprovedIcon);
            companiesListView = (ListView) view.findViewById(R.id.companiesListView);
            traveStatus = (TextView) view.findViewById(R.id.traveStatus);
        }

    }
}
