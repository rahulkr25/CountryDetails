package com.example.countrydetails.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface CrewDao {
    @Query("SELECT * FROM CountryEntry")
    LiveData<List<CountryEntry>> loadAllTasks();

    @Insert
    void insertTask(ArrayList<CountryEntry>Countryentries);


    @Query("DELETE FROM CountryEntry")
    public void nukeTable();

}
