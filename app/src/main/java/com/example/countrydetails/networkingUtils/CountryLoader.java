package com.example.countrydetails.networkingUtils;

import android.content.Context;

import com.example.countrydetails.database.CountryEntry;

import java.util.ArrayList;

public class CountryLoader extends  androidx.loader.content.AsyncTaskLoader<ArrayList<CountryEntry>> {

    private String mUrl;

    public CountryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }


    @Override
    public ArrayList<CountryEntry> loadInBackground() {

        if (mUrl == null) {
            return null;
        }


        ArrayList<CountryEntry>crew = QueryUtils.extractCountry(mUrl);
        return crew;
    }
}
