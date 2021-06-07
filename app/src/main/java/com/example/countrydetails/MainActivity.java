package com.example.countrydetails;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countrydetails.database.AppDataBase;
import com.example.countrydetails.database.CountryEntry;
import com.example.countrydetails.networkingUtils.CountryLoader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  androidx.loader.app.LoaderManager.LoaderCallbacks<ArrayList<CountryEntry>>,TaskAdapter.ItemClickListener {
   RecyclerView mRecyclerView;
   TaskAdapter mAdapter;
    private AppDataBase mDb;
    private int loaderCounter=0;
    ProgressBar progressBar;
    public static final String REGION_ASIA ="https://restcountries.eu/rest/v2/region/asia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        progressBar=findViewById(R.id.loading_spinner);
        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new TaskAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            progressBar.setVisibility(View.VISIBLE);
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(loaderCounter, null, MainActivity.this).forceLoad();
        }else
        {
            progressBar.setVisibility(View.INVISIBLE);
             setupViewModel();
        }
        mDb = AppDataBase.getInstance(getApplicationContext());
    }


    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<CountryEntry>>() {
            @Override
            public void onChanged(@Nullable List<CountryEntry> taskEntries) {
                Log.d("MainActivity", "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setTasks(taskEntries);
            }
        });
    }

    @Override
    public androidx.loader.content.Loader<ArrayList<CountryEntry>> onCreateLoader(int id, Bundle args) {
        return  new CountryLoader(this, REGION_ASIA);
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull androidx.loader.content.Loader<ArrayList<CountryEntry>> loader, ArrayList<CountryEntry> data) {
        Log.v("Data Size",""+data.size());

        deleteolddata();
        StoreDataInDB(data);
        progressBar.setVisibility(View.INVISIBLE);
        setupViewModel();
    }

    private void deleteolddata() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
              mDb.CrewDao().nukeTable();}
        });
    }

    private void StoreDataInDB(ArrayList<CountryEntry> data) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               final ArrayList<CountryEntry>dataa=data;
                    // insert new task
                    mDb.CrewDao().insertTask(dataa);

                //finish();
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull @NotNull androidx.loader.content.Loader<ArrayList<CountryEntry>> loader) {

    }


    @Override
    public void onItemClickListener(CountryEntry countryEntry) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(String.valueOf(R.string.COUNTRY_NAME), countryEntry.getName());
        bundle.putString(String.valueOf(R.string.COUNTRY_CAPITAL), countryEntry.getCapital());
        bundle.putString(String.valueOf(R.string.COUNTRY_FLAG), countryEntry.getImageUrl());
        bundle.putString(String.valueOf(R.string.COUNTRY_REGION), countryEntry.getRegion());
        bundle.putString(String.valueOf(R.string.COUNTRY_SUBREGION), countryEntry.getSubregion());
        bundle.putString(String.valueOf(R.string.COUNTRY_BORDERS), countryEntry.getBorder());
        bundle.putString(String.valueOf(R.string.COUNTRY_LANGUAGES), countryEntry.getLanguages());
        bundle.putString(String.valueOf(R.string.COUNTRY_POPULATION), countryEntry.getPopulation());
        intent.putExtras(bundle);
        Log.v("MainActivity", countryEntry.getName());
        startActivity(intent);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
             deleteolddata();
        }
        else if(id==R.id.action_refresh)
        {
            LoaderManager loaderManager = getSupportLoaderManager();
            progressBar.setVisibility(View.VISIBLE);
            loaderManager.restartLoader(loaderCounter, null, MainActivity.this).forceLoad();

        }
        return super.onOptionsItemSelected(item);
    }
}