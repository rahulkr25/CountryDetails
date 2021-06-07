package com.example.countrydetails;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.countrydetails.database.AppDataBase;
import com.example.countrydetails.database.CountryEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<CountryEntry>> tasks;

    public MainViewModel(Application application) {
        super(application);
        AppDataBase database = AppDataBase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        tasks = database.CrewDao().loadAllTasks();
    }

    public LiveData<List<CountryEntry>> getTasks() {
        return tasks;
    }
}

