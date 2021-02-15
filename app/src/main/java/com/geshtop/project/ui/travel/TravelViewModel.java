package com.geshtop.project.ui.travel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.geshtop.project.Entity.Travel;
import com.geshtop.project.Entity.User;
import com.geshtop.project.Repository.AuthRepository;
import com.geshtop.project.Repository.ITravelRepository;
import com.geshtop.project.Repository.SplashRepository;
import com.geshtop.project.Repository.TravelRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {
    ITravelRepository repository;
    private FirebaseAuth mAuth  = FirebaseAuth.getInstance();
    public User getCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return null;
        String uid = currentUser.getUid();
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        User user = new User(uid, name, email);
        return user;
        //authenticatedUserMutableLiveData.setValue(user);
    }

    public  void signOut(){
        try {
            mAuth.signOut();

        }catch (Exception ex){

        }
    }
    private MutableLiveData<List<Travel>> mutableLiveData = new MutableLiveData<>();
    public TravelViewModel(Application p) {
        super(p);
        repository = TravelRepository.getInstance();
        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener = new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllTravels();
                mutableLiveData.setValue(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);
    }
    void addTravel(Travel travel)
    {
        repository.addTravel(travel);
    }
    public void updateTravel(Travel travel)
    {
        repository.updateTravel(travel);
    }
    MutableLiveData<List<Travel>> getAllTravels()
    {
        return mutableLiveData;
    }
    MutableLiveData<Boolean> getIsSuccess()
    {
       return repository.getIsSuccess();
    }


}
