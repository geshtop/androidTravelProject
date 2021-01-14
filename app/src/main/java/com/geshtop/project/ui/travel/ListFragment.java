package com.geshtop.project.ui.travel;

import androidx.lifecycle.Observer;


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
import com.geshtop.project.Utils.GeoHelper;
import com.geshtop.project.Utils.GpsTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.slider.Slider;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ListFragment extends Fragment {

    private TravelViewModel mViewModel;
    private String currEmail;
    private ListView itemsListView;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    private List<Travel> travelsList;
    private int filterRadios = 50000;
    public ListFragment() {
    }

    //public static ListFragment newInstance() {
    //    return new ListFragment();
    //}

    private  void fillList( @NonNull View view){

        List<Travel> filterTravels = travelsList.stream()
                .filter(
                        c -> !c.getClientEmail().equals(currEmail)
                        && (c.getRequestType().equals(RequestType.Accepted) || c.getRequestType().equals(RequestType.Created))
                        && ( c.getCurrentDistance() < filterRadios)
                )
                .collect(Collectors.toList());
        ArrayList<Travel> tmp = new ArrayList<Travel>(filterTravels);
        //create adapter object
        TravelsListAdapter adapter = new TravelsListAdapter(view.getContext(), tmp, mViewModel);
        //set custom adapter as adapter to our list view
        itemsListView.setAdapter(adapter);
    }


    private  void setDestinationToList(){
        for (Travel temp : travelsList) {
            temp.setCurrentDistance(GeoHelper.GetDistance( currentLocation, temp.getLocationFromTravel()));
        }

    }



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

        Slider slider =  (Slider)view.findViewById(R.id.filterSlider);
        currentLocationTextview.setText("Filter travels within " + slider.getValue() + " KM from your location");
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                currentLocationTextview.setText("Filter travels within " + value + " KM from your location");
                filterRadios = (int)value * 1000; //to get kilometers

                fillList( view);
            }
        });
        GpsTracker tracker = new GpsTracker(this.getContext());
        if (!tracker.canGetLocation()) {
            //set to jerusalem
            currentLocation =new Location("origin");
            currentLocation.setLatitude( 31.76711490496574);
            currentLocation.setLongitude( 35.21484384384385);
            tracker.showSettingsAlert();
        } else {
            currentLocation= new Location("origin");
            //the emalutor does not work with the gps
           // currentLocation.setLatitude( tracker.getLatitude());
           // currentLocation.setLongitude( tracker.getLongitude());
            currentLocation.setLatitude( 31.76711490496574);
            currentLocation.setLongitude( 35.21484384384385);
        }


           itemsListView  = (ListView)view.findViewById(R.id.list_view_items);
        mViewModel.getAllTravels().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                travelsList = travels;

                setDestinationToList();
                fillList( view);
            }});
    }



}