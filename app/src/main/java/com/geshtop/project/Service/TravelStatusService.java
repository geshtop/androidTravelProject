package com.geshtop.project.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.geshtop.project.Entity.Travel;
import com.geshtop.project.R;
import com.geshtop.project.Repository.ITravelRepository;
import com.geshtop.project.Repository.TravelRepository;
import com.geshtop.project.ui.travel.TravelActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.geshtop.project.ui.App.CHANNEL_ID;

public class TravelStatusService extends Service {
//   ITravelRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TravelStatusService that = this;
        Intent notificationIntent = new Intent(that, TravelActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(that, 0, notificationIntent,0 );

        final DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("TraveRequest");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            //HERE I WANT TO RETURN CHANGE NOTIFICATION
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notification notification = new NotificationCompat.Builder(that, CHANNEL_ID)
                        .setContentTitle("Travels APP")
                        .setContentText("There is an update in the travel interface")
                        .setSmallIcon(R.drawable.logo)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_NOT_STICKY;
    }

    public TravelStatusService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
}