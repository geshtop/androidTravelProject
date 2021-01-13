package com.geshtop.project.ui.travel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.geshtop.project.Adapters.TravelsListAdapter;
import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListFragment extends Fragment {

    private TravelViewModel mViewModel;
    private  String currEmail;
    ListView itemsListView;

    //public static ListFragment newInstance() {
    //    return new ListFragment();
    //}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        

        TextView text = (TextView) findViewById(R.id.texts);
        if(mGPS.canGetLocation ){
            mGPS.getLocation();
            text.setText("Lat"+mGPS.getLatitude()+"Lon"+mGPS.getLongitude());
        }else{
            text.setText("Unabletofind");
            System.out.println("Unable");
        }


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