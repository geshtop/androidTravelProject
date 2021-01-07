package com.geshtop.project.ui.splash;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import  com.geshtop.project.ui.auth.AuthActivity;
import  com.geshtop.project.Entity.User;
import com.geshtop.project.ui.travel.TravelActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.geshtop.project.Utils.Constants.USER;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;
    /////
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            mAuth = FirebaseAuth.getInstance();
            //FirebaseUser currentUser = mAuth.getCurrentUser();
            mAuth.signOut();

        }catch (Exception ex){

        }
        initSplashViewModel();
        checkIfUserIsAuthenticated();
    }

    private void initSplashViewModel() {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, user -> {
            if (!user.isAuthenticated) {
                goToAuthInActivity();
                finish();
            } else {
                getUserFromDatabase(user.uid);
            }
        });
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, user -> {
            goToMainActivity(user);
            finish();
        });
    }

    private void goToMainActivity(User user) {
        Intent intent = new Intent(SplashActivity.this, TravelActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}