package com.geshtop.project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.TravelViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompaniesAdapter extends BaseAdapter {
    private Context context;
    private List<TravelCompany> items;
    private TravelViewModel mViewModel;
    private SimpleDateFormat sdf;
    private Travel currentTravel;

    public CompaniesAdapter(Context context, Travel travel,  TravelViewModel mViewModel) {
        this.context = context;
        this.items = travel.getCompanies();
        this.currentTravel = travel;
        this.mViewModel = mViewModel;
        sdf = new SimpleDateFormat("dd/MM/yy");

    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_my_travels_company_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

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
                        mViewModel.updateTravel(currentTravel);

//                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
//                        currentItem.addSingleCompany(tc, isChecked);
//                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, "updated travel company ", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


        return convertView;
    }


    private class ViewHolder {
        TextView companyName;
        TextView comapnyAddedDate;
        TextView cidTextView;
        Button confirmButton;
        public ViewHolder(View view) {
            companyName = (TextView)view.findViewById(R.id.companyName);
            comapnyAddedDate = (TextView) view.findViewById(R.id.comapnyAddedDate);
            cidTextView = (TextView) view.findViewById(R.id.cidTextView);
            confirmButton = (Button) view.findViewById(R.id.confirmButton);

        }
    }
}
