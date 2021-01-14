package com.geshtop.project.ui.travel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.geshtop.project.Adapters.AdminAdapter;
import com.geshtop.project.Adapters.MyTravelsAdapter;
import com.geshtop.project.Entity.RequestType;
import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminFragment extends Fragment {

    private TravelViewModel mViewModel;
    private List<String> requestedTypes;
    private RequestType selectedReustType = RequestType.Created;
    private  ListView filteredList;
    private  List<Travel> travelsList;
    public static AdminFragment newInstance() {
        return new AdminFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TravelActivity ta = (TravelActivity)this.getActivity();
        mViewModel = ta.getViewModel();
        requestedTypes = Stream.of(RequestType.values())
                .map(RequestType::name)
                .collect(Collectors.toList());
        return inflater.inflate(R.layout.admin_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private  void fillList( @NonNull View view){

        List<Travel> filterTravels = travelsList.stream()
                .filter(
                        c -> c.getRequestType().equals(selectedReustType)
                )
                .collect(Collectors.toList());

        ArrayList<Travel> tmp = new ArrayList<Travel>(filterTravels);
        //create adapter object
        AdminAdapter adapter = new AdminAdapter(view.getContext(), tmp, mViewModel);
        //set custom adapter as adapter to our list view
        filteredList.setAdapter(adapter);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filteredList  = (ListView)view.findViewById(R.id.filteredList);
        Spinner filterSpinner = (Spinner)view.findViewById(R.id.filterSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, requestedTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        mViewModel.getAllTravels().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travels) {
                travelsList = travels;
               fillList( view);


            }});

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedReustType =RequestType.valueOf(requestedTypes.get(i));
                fillList(   view);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedReustType =RequestType.Created;
                fillList( view);
            }
        });


    }


}