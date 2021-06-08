package com.example.countrydetails.networkingUtils;

import android.util.Log;

import com.example.countrydetails.database.CountryEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<CountryEntry> extractCountry(String url) {

        URL urlCountry = createUrl(url);
        ArrayList<CountryEntry> countryDetails;

        String country_string="";
        try {
            country_string= makeHttpRequest(urlCountry);
        } catch (IOException e) {


            Log.e("CountryUtils","couldn't get response back from  the given url",e);
            return null;
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        countryDetails = extractCrewFromJson(country_string);

        // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
        return countryDetails;
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.


        // Return the list of earthquakes

    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("QueryUtils", "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    public static ArrayList<CountryEntry> extractCrewFromJson(String CountryJSON) {
        ArrayList<CountryEntry>countryEntries=new ArrayList<>();

        try {

            JSONArray jsonArray=new JSONArray(CountryJSON);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String name=jsonObject.optString("name");
                String capital=jsonObject.optString("capital");
                String region=jsonObject.optString("region");
                String subregion=jsonObject.optString("subregion");
                String population=jsonObject.optString("population");
                String imageUrl=jsonObject.optString("flag");
                JSONArray borders=jsonObject.getJSONArray("borders");
                String borders_string="";

                    borders_string=borders_string+ borders.join(",");
                    String border_string_Conct="";
                    for(int j=0;j<borders_string.length();j++)
                    {
                        if(borders_string.charAt(j)=='"')
                        {
                            continue;
                        }
                        else
                        {
                            border_string_Conct=border_string_Conct+borders_string.charAt(j);
                        }
                        if(j!=0)
                        {
                            if(borders_string.charAt(j-1)==',')
                            {
                                border_string_Conct=border_string_Conct+" ";
                            }
                        }
                    }

                JSONArray languages=jsonObject.getJSONArray("languages");
                String languages_string="";
                for(int j=0;j<languages.length();j++)
                {
                    JSONObject jsonObjectLang=languages.getJSONObject(j);
                    String langName=jsonObjectLang.optString("name");
                    if(j!=languages.length()-1){languages_string=languages_string+langName+", ";}
                    else {languages_string=languages_string+langName;}
                }
                countryEntries.add(new CountryEntry(name,capital,imageUrl,region,subregion,population,
                        border_string_Conct,languages_string));
            }




            /////////////////////////////////////////////////////
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return countryEntries;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        if(url==null)
        {
            return "";
        }
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                //Log.e(LOG_TAG,"Other code");
            }

        }
        catch (ProtocolException e) {
            //  Log.e(LOG_TAG,"Request Method isnt valid for HTTP",e);
        }catch (IOException e) {

            // Log.e(LOG_TAG,"Problem from reading input Stream",e);
        }

        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
