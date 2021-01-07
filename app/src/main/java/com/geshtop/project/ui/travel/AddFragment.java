package com.geshtop.project.ui.travel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.UserLocation;
import com.geshtop.project.R;
import com.geshtop.project.Utils.GeoHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;


public class AddFragment extends Fragment {

    //private AddViewModel mViewModel;
    private TravelViewModel mViewModel;
    Calendar fromDatetemporary ;
    Calendar toDatetemporary;
    Button addRequestBtn;
    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        mViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View currView = view;
        addRequestBtn = (Button) view.findViewById(R.id.addRequestBtn);

        CalendarView fromDate = (CalendarView)view.findViewById(R.id.fromDate);
        fromDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                fromDatetemporary = Calendar.getInstance();
                fromDatetemporary.set(year, month, dayOfMonth);

            }
        });

        CalendarView toDate = (CalendarView)view.findViewById(R.id.toDate);
        toDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                toDatetemporary = Calendar.getInstance();
                toDatetemporary.set(year, month, dayOfMonth);

            }
        });

        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.
                Travel t = getTravel(currView);
                mViewModel.addTravel(t);
            }
        });






    }


    private Travel getTravel( View root ) {

        Travel t = new Travel();
        EditText editTextClientName = (EditText)root.findViewById(R.id.editTextClientName);
        EditText editTextTextClientPhone = (EditText)root.findViewById(R.id.editTextTextClientPhone);
        EditText editTextEmailAddress = (EditText)root.findViewById(R.id.editTextEmailAddress);
        EditText editTextFromAddress = (EditText)root.findViewById(R.id.editTextFromAddress);

        EditText editTextToAddress = (EditText)root.findViewById(R.id.editTextToAddress);


        EditText editTextPassengers = (EditText)root.findViewById(R.id.editTextPassengers);

        //from address
        String fromAddress =editTextFromAddress.getText().toString().trim();
        LatLng lFrom = GeoHelper.getLocationFromAddress(fromAddress, root.getContext());
        if(lFrom != null)
            t.setTravelLocation(new UserLocation(lFrom.latitude, lFrom.longitude));
        //to address
        String toAddress =editTextToAddress.getText().toString().trim();
        LatLng lTo =GeoHelper.getLocationFromAddress(toAddress, root.getContext());
        if(lTo != null){
            UserLocation toLocation =    new UserLocation(lTo.latitude, lTo.longitude);
            // List<UserLocation> l = new ArrayList<>();
            // l.add(toLocation);
            // t.setDestinations(l);
            t.addDestionationLocation(toLocation);
        }
        t.setClientName(editTextClientName.getText().toString().trim());
        t.setClientPhone(editTextTextClientPhone.getText().toString().trim());
        t.setClientEmail(editTextEmailAddress.getText().toString().trim());
        t.setTravelDate(fromDatetemporary.getTime());
        t.setArrivalDate(toDatetemporary.getTime());
        t.setPassengers(Integer.parseInt(editTextPassengers.getText().toString().trim()));
        return t;
    }

}