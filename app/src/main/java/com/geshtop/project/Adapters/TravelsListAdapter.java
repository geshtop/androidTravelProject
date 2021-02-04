package com.geshtop.project.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.TravelCompany;
import com.geshtop.project.Entity.User;
import com.geshtop.project.R;
import com.geshtop.project.ui.travel.DetailFragment;
import com.geshtop.project.ui.travel.TravelActivity;
import com.geshtop.project.ui.travel.TravelViewModel;
import com.google.android.material.button.MaterialButton;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom list adapter, implementing BaseAdapter
 */
public class TravelsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Travel> items;
    private  TravelViewModel mViewModel;
    private SimpleDateFormat sdf;
    public TravelsListAdapter(Context context, ArrayList<Travel> items, TravelViewModel mViewModel) {
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



    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Hi !\n" + "Your account was successfully created.", Toast.LENGTH_LONG).show();

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
        String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};
        String[] requestedTypes = RequestType.getTypseAsList().toArray(new String[0]);
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
            viewHolder.titleTextView.setText(currentItem.getTitle());
            viewHolder.datesTextView.setText( sdf.format(currentItem.getTravelDate()) + "-" +  sdf.format(currentItem.getArrivalDate()) );
            viewHolder.stateProgressBar.setStateDescriptionData(requestedTypes);

            Float dis = currentItem.getCurrentDistance();
            if(dis!= 0)
                viewHolder.fillDistance.setText(dis.toString());
            TravelCompany tc = currentItem.companyExists(currentUser.email);
            if(tc == null){
                viewHolder.acceptButton.setEnabled(true);
                //viewHolder.accepted.setVisibility(View.GONE);
                //viewHolder.not_accepted.setVisibility(View.GONE);
                viewHolder.stateProgressBar.setCurrentStateNumber( StateProgressBar.StateNumber.ONE);
            }else{
                viewHolder.acceptButton.setEnabled(false);
                if (tc.getRunning()){ //the client confirm
                   // viewHolder.accepted.setVisibility(View.VISIBLE);
                   // viewHolder.not_accepted.setVisibility(View.GONE);
                    viewHolder.stateProgressBar.setCurrentStateNumber( StateProgressBar.StateNumber.THREE);
                }else{
                  //  viewHolder.accepted.setVisibility(View.GONE);
                  //  viewHolder.not_accepted.setVisibility(View.VISIBLE);
                    viewHolder.stateProgressBar.setCurrentStateNumber( StateProgressBar.StateNumber.TWO);
                }
            }

            viewHolder.acceptButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        TravelCompany tc = new TravelCompany(currentUser.uid, currentUser.name, currentUser.email);
                        currentItem.setRequestType(RequestType.Accepted);
                        currentItem.addSingleCompany(tc, true);
                        mViewModel.updateTravel(currentItem);
                        Toast.makeText(context, currentUser.name + " update successfullt the travel", Toast.LENGTH_LONG).show();
                    }
                }
            });

            viewHolder.phoneCall.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Uri number = Uri.parse("tel:"+Uri.encode(currentItem.getClientPhone()));
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    context.startActivity(callIntent);
                }
            });

            viewHolder.sendMail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setType("text/plain");
                    emailIntent.setData(Uri.parse("mailto:"+Uri.encode(currentItem.getClientEmail()))); // only email apps should handle this
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Travel App " + currentItem.getTitle());
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(emailIntent);
                }
            });


        }


        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView titleTextView;
        TextView datesTextView;
        TextView clientName;
        TextView clientPhone;
        TextView clientEmail;
        TextView fillDistance;
        //TextView accepted;
        //TextView not_accepted;
        MaterialButton acceptButton;
        MaterialButton phoneCall;
        MaterialButton sendMail;
        StateProgressBar stateProgressBar;
        public ViewHolder(View view) {
            clientName = (TextView)view.findViewById(R.id.clientName);
            clientPhone = (TextView) view.findViewById(R.id.clientPhone);
            clientEmail = (TextView) view.findViewById(R.id.clientEmail);
            acceptButton = (MaterialButton) view.findViewById(R.id.acceptButton);
            phoneCall = (MaterialButton) view.findViewById(R.id.phoneCall);
            sendMail = (MaterialButton) view.findViewById(R.id.sendMail);
            //pted = (TextView) view.findViewById(R.id.accepted);
            //not_accepted = (TextView) view.findViewById(R.id.not_accepted);
            fillDistance = (TextView) view.findViewById(R.id.fillDistance);
            titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            datesTextView = (TextView) view.findViewById(R.id.datesTextView);
            stateProgressBar  = (StateProgressBar) view.findViewById(R.id.state_progress_bar_id);
        }
    }
}
