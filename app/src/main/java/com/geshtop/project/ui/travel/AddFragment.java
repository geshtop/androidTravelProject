package com.geshtop.project.ui.travel;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.UserLocation;
import com.geshtop.project.R;
import com.geshtop.project.Utils.GeoHelper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.Date;


public class AddFragment extends Fragment {

    //private AddViewModel mViewModel;
    private TravelViewModel mViewModel;
    Date fDate;
    Date tDate;
    Button addRequestBtn;
    private ImageButton mPickDateButton;
    private TextView mShowSelectedDateText;
    private EditText editTextClientName;
    private   EditText editTextEmailAddress;
    private  EditText editTextTextClientPhone;
    private   EditText editTextFromAddress;
    private  EditText editTextToAddress;
    private EditText editTextPassengers;
    public static AddFragment newInstance() {
        return new AddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TravelActivity ta = (TravelActivity)this.getActivity();
        mViewModel = ta.getViewModel();
        //mViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        //mViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        View currView = view;
        addRequestBtn = (Button) view.findViewById(R.id.addRequestBtn);
        mPickDateButton = view.findViewById(R.id.pick_date_button);
        mShowSelectedDateText = view.findViewById(R.id.show_selected_date);
        editTextClientName = view.findViewById(R.id.editTextClientName);
        editTextEmailAddress =view.findViewById(R.id.editTextEmailAddress);
        editTextClientName.setText(mViewModel.getCurrentUser().name);
        editTextEmailAddress.setText(mViewModel.getCurrentUser().email);
        editTextTextClientPhone = view.findViewById(R.id.editTextTextClientPhone);
        editTextFromAddress = view.findViewById(R.id.editTextFromAddress);
         editTextToAddress = view.findViewById(R.id.editTextToAddress);
         editTextPassengers = view.findViewById(R.id.editTextPassengers);

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker pickerRange  = materialDateBuilder.build();
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pickerRange .show(getFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        // now handle the positive button click from the
        // material design date picker
        pickerRange.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override public void onPositiveButtonClick(Pair<Long,Long> selection) {
                Long startDate = selection.first;
                Long endDate = selection.second;
                fDate = new Date(startDate);
                tDate = new Date(endDate);
                mShowSelectedDateText.setText(pickerRange.getHeaderText());
                mShowSelectedDateText.setError(null);
                //Do something...
            }
        });





        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.
                addRequestBtn.setEnabled(false);
                Travel t = getTravel(currView);
                if(validForm(t)) {
                    mViewModel.addTravel(t);
                    Toast.makeText(v.getContext(), "Travel add successfully!!!", Toast.LENGTH_LONG).show();
                }

                addRequestBtn.setEnabled(true);
            }
        });






    }


    private  boolean validForm(Travel t){
        boolean flag = true;
        if(t.getClientName().equals("")){
            editTextClientName.setError( "Client name is required!" );
            flag = false;

        }
        if(t.getClientEmail().equals("")){
            editTextEmailAddress.setError( "Email is required!" );
            flag = false;

        }
        if(t.getClientPhone().equals("")){
            editTextTextClientPhone.setError( "Phone is required!" );
            flag = false;

        }

        if(t.getClientPhone().equals("")){
            editTextTextClientPhone.setError( "Phone is required!" );
            flag = false;

        }
        if( t.getPassengers() ==0){
            editTextPassengers.setError( "Missing Passengers!" );
            flag = false;

        }
        if(t.getArrivalDate()==null || t.getTravelDate()== null){
            mShowSelectedDateText.setError( "Please select dates!" );
            flag = false;

        }
        if( t.getTravelLocation()== null ){
            editTextFromAddress.setError( "The system does not found the location please specific your location adddrss" );
            flag = false;

        }

        if( t.getDestinations()== null || t.getDestinations().size()==0 ){
            editTextToAddress.setError( "The system does not found the location please specific your location adddrss" );
            flag = false;

        }


        return flag;
    }

    private Travel getTravel( View root ) {

        Travel t = new Travel();
        //EditText


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
        t.setTravelDate(fDate);
        t.setArrivalDate(tDate);
        if(!editTextPassengers.getText().toString().trim().equals("")) {
            try {
                t.setPassengers(Integer.parseInt(editTextPassengers.getText().toString().trim()));
            }catch (Exception ex){

            }
        }
        return t;
    }

}