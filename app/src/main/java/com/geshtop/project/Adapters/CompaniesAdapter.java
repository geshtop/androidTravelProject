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

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.TravelViewModel;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompaniesAdapter extends BaseAdapter {
    private Context context;
    private List<TravelCompany> items;
    private TravelViewModel mViewModel;
    private SimpleDateFormat sdf;

    public CompaniesAdapter(Context context, List<TravelCompany> items, TravelViewModel mViewModel) {
        this.context = context;
        this.items = items;
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
        CompaniesAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_my_travels_company_item, parent, false);
            viewHolder = new CompaniesAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CompaniesAdapter.ViewHolder) convertView.getTag();
        }

        TravelCompany currentItem = (TravelCompany) getItem(position);

        if (currentItem != null) {
            User currentUser = mViewModel.getCurrentUser();
            viewHolder.companyName.setText(currentItem.getName());
            viewHolder.comapnyAddedDate.setText( sdf.format(currentItem.getCreatedDate()));



            viewHolder.confirmButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
//                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
//                        currentItem.addSingleCompany(tc, isChecked);
//                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, currentUser.uid + "clicked button", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


        return convertView;
    }


    private class ViewHolder {
        TextView companyName;
        TextView comapnyAddedDate;
        Button confirmButton;
        public ViewHolder(View view) {
            companyName = (TextView)view.findViewById(R.id.companyName);
            comapnyAddedDate = (TextView) view.findViewById(R.id.comapnyAddedDate);
            confirmButton = (Button) view.findViewById(R.id.confirmButton);

        }
    }
}
