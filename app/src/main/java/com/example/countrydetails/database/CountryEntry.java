package com.example.countrydetails.database;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CountryEntry")
public class CountryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String capital;
    private String region;
    private  String subregion;
    private String imageUrl;
    private String population;
    private String border;
    private String languages;



    @Ignore
    public CountryEntry(String name, String capital, String imageUrl, String region, String subregion,
                        String population,String border,String languages) {
        this.name = name;
        this.capital =capital;
        this.imageUrl=imageUrl;
        this.region = region;
        this.subregion=subregion;
        this.population=population;
        this.border = border;
        this.languages=languages;

    }

    public CountryEntry(int id,String name, String capital, String imageUrl, String region, String subregion,
                        String population,String border,String languages) {
        this.name = name;
        this.capital =capital;
        this.imageUrl=imageUrl;
        this.region = region;
        this.subregion=subregion;
        this.population=population;
        this.border = border;
        this.languages=languages;

    }

    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }


    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }
    public String getLanguages() {
        return languages;
    }

    public String getBorder() {
        return border;
    }

    public String getPopulation() {
        return population;
    }

    public String getSubregion() {
        return subregion;
    }

}
