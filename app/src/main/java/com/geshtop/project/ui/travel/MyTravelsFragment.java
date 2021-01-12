package com.geshtop.project.ui.travel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.geshtop.project.Adapters.MyTravelsAdapter;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class MyTravelsFragment extends Fragment {
    private TravelViewModel mViewModel;
    private  ListView itemsListView;
    public MyTravelsFragment() {
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TravelActivity ta = (TravelActivity)this.getActivity();
        mViewModel = ta.getViewModel();
        return inflater.inflate(R.layout.fragment_my_travels_list, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        itemsListView  = (ListView)view.findViewById(R.id.travels_list_view_items);
        mViewModel.getAllTravels().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);
                //create adapter object
                MyTravelsAdapter adapter = new MyTravelsAdapter(view.getContext(), tmp, mViewModel);
                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }
}