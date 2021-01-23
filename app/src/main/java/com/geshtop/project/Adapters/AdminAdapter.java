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

public class AdminAdapter extends BaseAdapter {
    private Context context;
    private List<Travel> items;
    private TravelViewModel mViewModel;
    private SimpleDateFormat sdf;

    public AdminAdapter(Context context, ArrayList<Travel> items, TravelViewModel mViewModel) {
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_admin_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Travel currentItem = (Travel) getItem(position);

        if (currentItem != null) {
            User currentUser = mViewModel.getCurrentUser();
            if (currentItem.getTravelLocation() != null && currentItem.getDestinations().size() > 0)
                viewHolder.travelLocation.setText(currentItem.getTravelLocation().toString() + "-" + currentItem.getDestinations().get(0).toString());
            viewHolder.traveStatus.setText(currentItem.getRequestType().toString());
            viewHolder.travelDate.setText(sdf.format(currentItem.getTravelDate()) + "-" + sdf.format(currentItem.getArrivalDate()));
            viewHolder.clientName.setText(currentItem.getClientName());
            viewHolder.clientEmail.setText(currentItem.getClientEmail());
            viewHolder.clientPhone.setText(currentItem.getClientPhone());
            if (currentItem.getRequestType().equals(RequestType.Done)) {
                viewHolder.paidButton.setVisibility(View.VISIBLE);
            } else {
                viewHolder.paidButton.setVisibility(View.GONE);
            }

            viewHolder.paidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        currentItem.setRequestType(RequestType.Paid);
                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, "updated travel company ", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


        return convertView;
    }


    private class ViewHolder {
        TextView clientName;
        TextView clientEmail;
        TextView travelLocation;
        TextView travelDate;
        TextView traveStatus;
        TextView clientPhone;
        Button paidButton;

        public ViewHolder(View view) {
            clientName = (TextView) view.findViewById(R.id.clientName);
            clientEmail = (TextView) view.findViewById(R.id.clientEmail);
            travelLocation = (TextView) view.findViewById(R.id.travelLocation);
            travelDate = (TextView) view.findViewById(R.id.travelDate);
            traveStatus = (TextView) view.findViewById(R.id.traveStatus);
            clientPhone = (TextView) view.findViewById(R.id.clientPhone);
            paidButton = (Button) view.findViewById(R.id.paidButton);

        }
    }
}
