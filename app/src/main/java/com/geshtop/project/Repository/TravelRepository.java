package com.geshtop.project.Repository;

import androidx.lifecycle.MutableLiveData;

import com.geshtop.project.Entity.IHistoryDataSource;
import com.geshtop.project.Entity.Travel;

import java.util.List;

public class TravelRepository implements ITravelRepository {
    ITravelDataSource travelDataSource;
    private IHistoryDataSource historyDataSource;
    private ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerRepository;
    List<Travel> travelList;




    private static TravelRepository instance;

//    public static TravelRepository getInstance(Application application) {
//        if (instance == null)
//            instance = new TravelRepository(application);
//        return instance;
//    }
    public static TravelRepository getInstance() {
        if (instance == null)
            instance = new TravelRepository();
        return instance;
    }
    private TravelRepository() {
        travelDataSource = TravelFirebaseDataSource.getInstance();
        //historyDataSource = new HistoryDataSource(application.getApplicationContext());

        ITravelDataSource.NotifyToTravelListListener notifyToTravelListListener = new ITravelDataSource.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                 travelList = travelDataSource.getAllTravels();


                if (notifyToTravelListListenerRepository != null)
                    notifyToTravelListListenerRepository.onTravelsChanged();

            }
        };

        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener);
    }
    @Override
    public void addTravel(Travel travel) {
        travelDataSource.addTravel(travel);
    }

    @Override
    public void updateTravel(Travel travel) {
        travelDataSource.updateTravel(travel);
    }

    @Override
    public List<Travel> getAllTravels() {
        return travelList;
    }

    @Override
    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    public void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l) {
        notifyToTravelListListenerRepository = l;
    }
}
