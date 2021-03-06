package com.geshtop.project.Entity;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Travel.class}, version = 1, exportSchema = false)
//@TypeConverters({Travel.RequestType.class, Travel.DateConverter.class, Travel.DateConverter.class, Travel.UserLocationConverter.class, Travel.CompanyConverter.class})
public abstract class RoomDataSource extends RoomDatabase {

    public static final String DATABASE_NAME="TraveRequest";
    private static RoomDataSource database;

    public static RoomDataSource getInstance(Context context){
        if (database==null)
            database= Room.databaseBuilder(context, RoomDataSource.class,DATABASE_NAME).allowMainThreadQueries().build();
        return database;
    }

    public abstract TravelDao getTravelDao();
}
