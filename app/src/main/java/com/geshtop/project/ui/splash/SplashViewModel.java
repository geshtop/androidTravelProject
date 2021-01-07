package com.geshtop.project.ui.splash;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.geshtop.project.Entity.User;
import com.geshtop.project.Repository.SplashRepository;

public class SplashViewModel extends AndroidViewModel {
    private SplashRepository splashRepository;
    public LiveData<User> isUserAuthenticatedLiveData;
    public LiveData<User> userLiveData;

    public SplashViewModel(Application application) {
        super(application);
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    public void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }
}
