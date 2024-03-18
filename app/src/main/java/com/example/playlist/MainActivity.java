package com.example.playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ListView playList;
    List<ItemObject> jsonObject = new ArrayList<ItemObject>();
    List<String> countries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playList = findViewById(R.id.listView);
        new AsyncDataClass().execute();

        // Initialize your button
        Button statsButton = findViewById(R.id.statsButton);

        // Set OnClickListener for the button
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show AlertDialog with stats
                showStatsDialog();
            }
        });

    }

    private class AsyncDataClass extends AsyncTask<String, Void,
            String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String...params ) {

            StringBuilder jsonResult = new StringBuilder();

            try {

                URL url = new
                        URL("http://www.papademas.net:81/cd_catalog.json");
                urlConnection = (HttpURLConnection)
                        url.openConnection();
                InputStream in = new
                        BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line);
                }
                System.out.println("Returned Json url object " +
                        jsonResult.toString());

            } catch (Exception e) {System.out.println("Err: " + e);}
            finally {
                urlConnection.disconnect();
            }
            return jsonResult.toString();
        }

        @Override
        protected void onPreExecute() {  }

//        @Override
//        protected void onPostExecute(String result) {
//
//            System.out.println("Result on post execute: " + result);
//            List<ItemObject> parsedObject =
//                    returnParsedJsonObject(result);
//            CustomAdapter jsonCustomAdapter = new
//                    CustomAdapter(MainActivity.this, parsedObject);
//            playList.setAdapter(jsonCustomAdapter);
//        }
// Inside onPostExecute method
@Override
protected void onPostExecute(String result) {
    System.out.println("Result on post execute: " + result);
    List<ItemObject> parsedObject = returnParsedJsonObject(result);

    // Sort the parsedObject list by year in ascending order
    Collections.sort(parsedObject, new Comparator<ItemObject>() {
        @Override
        public int compare(ItemObject item1, ItemObject item2) {
            // Parse years as integers for comparison
            int year1 = Integer.parseInt(item1.year);
            int year2 = Integer.parseInt(item2.year);
            return Integer.compare(year1, year2);
        }
    });

    CustomAdapter jsonCustomAdapter = new CustomAdapter(MainActivity.this, parsedObject);
    playList.setAdapter(jsonCustomAdapter);
}


    } //end AsyncDataClass class

    private List<ItemObject> returnParsedJsonObject(String result){

//        List<ItemObject> jsonObject = new ArrayList<ItemObject>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ItemObject newItemObject = null; //interior object holder

        try {
            resultObject = new JSONObject(result);
            System.out.println("Preparsed JSON object " +
                    resultObject.toString());
            // set up json Array to be parsed
            jsonArray = resultObject.optJSONArray("Bluesy_Music");
        } catch (JSONException e) { e.printStackTrace(); }
        //if (jsonArray != null) {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonChildNode = null;
                try {
                    jsonChildNode = jsonArray.getJSONObject(i);
                    //get all data from stream
                    String songSold = jsonChildNode.getString("SOLD");
                    String songTitle = jsonChildNode.getString("TITLE");
                    String songArtist =
                            jsonChildNode.getString("ARTIST");
                    String songCountry =
                            jsonChildNode.getString("COUNTRY");
                    String songCompany =
                            jsonChildNode.getString("COMPANY");
                    String songPrice = jsonChildNode.getString("PRICE");
                    String songYear = jsonChildNode.getString("YEAR");
                    newItemObject = new ItemObject(songTitle,
                            songArtist, songYear,songSold,songCountry,songCompany,songPrice);
                    jsonObject.add(newItemObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        //}
        return jsonObject;
    } //end method

    private void showStatsDialog() {

        // Prepare the message for AlertDialog
        StringBuilder message = new StringBuilder();
        message.append("1. Number of titles in the playlist: ").append(jsonObject.size()).append("\n");

        // Get the most expensive CD
        ItemObject mostExpensiveCD = findHighestPricedItem(jsonObject);
        message.append("2. Most expensive CD:\n");
        message.append("   Title: ").append(mostExpensiveCD.title).append("\n");
        message.append("   Price: ").append(mostExpensiveCD.price).append("\n");

        // Get unique countries of origin
        List<String> countries = new ArrayList<>();
        for (ItemObject item : jsonObject) {
                countries.add(item.country);
        }

        message.append("3. Countries of origin in order of listing:\n");
        for (String country : countries) {
            message.append("   ").append(country).append("\n");
        }

        // Create and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Statistics")
                .setMessage(message.toString())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static ItemObject findHighestPricedItem(List<ItemObject> items) {
        if (items.isEmpty()) {
            return null; // If list is empty, return null
        }

        ItemObject highestPricedItem = items.get(0); // Assume first item is the highest priced initially

        for (ItemObject item : items) {
            // Parse price strings into double for comparison
            double currentPrice = Double.parseDouble(item.price);
            double highestPrice = Double.parseDouble(highestPricedItem.price);

            // If current item's price is higher than highestPricedItem's price, update highestPricedItem
            if (currentPrice > highestPrice) {
                highestPricedItem = item;
            }
        }
        return highestPricedItem;
    }

    private List<ItemObject> sortItemsByYear(List<ItemObject> items) {
        // Sort the items list by year in ascending order
        Collections.sort(items, new Comparator<ItemObject>() {
            @Override
            public int compare(ItemObject item1, ItemObject item2) {
                // Parse years as integers for comparison
                int year1 = Integer.parseInt(item1.year);
                int year2 = Integer.parseInt(item2.year);
                return Integer.compare(year1, year2);
            }
        });
        return items;
    }
}