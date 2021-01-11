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
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private TravelViewModel mViewModel;
    Context context;
    ListView itemsListView;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(mViewModel == null)
            mViewModel = new ViewModelProvider(this.getActivity()).get(TravelViewModel.class);
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(mViewModel == null)
            mViewModel = new ViewModelProvider(this.getActivity()).get(TravelViewModel.class);
        super.onViewCreated(view, savedInstanceState);


        itemsListView  = (ListView)view.findViewById(R.id.list_view_items);
        mViewModel.getAllTravels().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                ArrayList<Travel> tmp = new ArrayList<Travel>(travels);
                //create adapter object
                TravelsListAdapter adapter = new TravelsListAdapter(context, tmp, mViewModel);
                //set custom adapter as adapter to our list view
                itemsListView.setAdapter(adapter);
            }});
    }



}