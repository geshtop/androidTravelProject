package com.geshtop.project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.DetailFragment;
import com.geshtop.project.ui.travel.TravelActivity;
import com.geshtop.project.ui.travel.TravelViewModel;

import java.util.ArrayList;

/**
 * Custom list adapter, implementing BaseAdapter
 */
public class TravelsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private  TravelViewModel mViewModel;
    public TravelsListAdapter(Context context, ArrayList<Travel> items, TravelViewModel mViewModel) {
        this.context = context;
        this.items = items;
        this.mViewModel = mViewModel;
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



    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Hi !\n" + "Youddr account was successfully created.", Toast.LENGTH_LONG).show();

           final Fragment detail = new DetailFragment();
           TravelActivity mainActivity = (TravelActivity) context;
           FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, detail);
           fragmentTransaction.addToBackStack(null);
           fragmentTransaction.commit();
        }
    };



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_view_row_items, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Travel currentItem = (Travel) getItem(position);

        if (currentItem != null) {
            User currentUser = mViewModel.getCurrentUser();
            viewHolder.clientName.setText(currentItem.getClientName());
            viewHolder.clientPhone.setText(currentItem.getClientPhone());
            viewHolder.clientEmail.setText(currentItem.getClientEmail());
            TravelCompany tc = currentItem.companyExists(currentUser.email);
            if(tc == null){
                viewHolder.acceptButton.setEnabled(true);
                viewHolder.accepted.setVisibility(View.INVISIBLE);
                viewHolder.not_accepted.setVisibility(View.INVISIBLE);

            }else{
                viewHolder.acceptButton.setEnabled(false);
                if (tc.getApproved()){ //the client confirm
                    viewHolder.accepted.setVisibility(View.VISIBLE);
                    viewHolder.not_accepted.setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.accepted.setVisibility(View.INVISIBLE);
                    viewHolder.not_accepted.setVisibility(View.VISIBLE);
                }
            }

            viewHolder.acceptButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
                        currentItem.addSingleCompany(tc, true);
                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, currentUser.name + " update successfullt the travel", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }


        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView clientName;
        TextView clientPhone;
        TextView clientEmail;
        TextView accepted;
        TextView not_accepted;
        Button acceptButton;
        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.clientName);
            clientPhone = (TextView) view.findViewById(R.id.clientPhone);
            clientEmail = (TextView) view.findViewById(R.id.clientEmail);
            acceptButton = (Button) view.findViewById(R.id.acceptButton);
            accepted = (TextView) view.findViewById(R.id.accepted);
            not_accepted = (TextView) view.findViewById(R.id.not_accepted);
        }
    }
}
