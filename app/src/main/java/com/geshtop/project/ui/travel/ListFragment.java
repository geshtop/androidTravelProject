package com.geshtop.project.ui.travel;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.geshtop.project.Adapters.TravelsListAdapter;
import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;
import com.geshtop.project.Utils.GpsTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListFragment extends Fragment {

    private TravelViewModel mViewModel;
    private String currEmail;
    private ListView itemsListView;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude;
    private double longitude;

    public ListFragment() {
    }

    //public static ListFragment newInstance() {
    //    return new ListFragment();
    //}





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {





        TravelActivity ta = (TravelActivity)this.getActivity();
        mViewModel = ta.getViewModel();
        currEmail =mViewModel.getCurrentUser().email.trim();
        return inflater.inflate(R.layout.list_fragment, container, false);

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        TextView   currentLocationTextview  = (TextView)view.findViewById(R.id.currentLocationTextview);

        GpsTracker tracker = new GpsTracker(view.getContext());
        if (!tracker.canGetLocation()) {
            tracker.showSettingsAlert();
        } else {
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
        }
        currentLocationTextview.setText("Current location: latitude: " + " longitude: " + latitude + longitude);
        itemsListView  = (ListView)view.findViewById(R.id.list_view_items);
        mViewModel.getAllTravels().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                List<Travel> filterTravels = travels.stream()
                        .filter(
                                c -> !c.getClientEmail().equals(currEmail)
                                        && (c.getRequestType().equals(RequestType.Accepted) || c.getRequestType().equals(RequestType.Created))
                        )
                        .collect(Collectors.toList());
                ArrayList<Travel> tmp = new ArrayList<Travel>(filterTravels);
                //create adapter object
                TravelsListAdapter adapter = new TravelsListAdapter(view.getContext(), tmp, mViewModel);
                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }



}