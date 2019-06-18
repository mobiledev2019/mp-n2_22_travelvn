package com.example.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bottomnavigation.MapsActivity;
import com.example.home.CategoryTravel;
import com.example.model.Travel;
import com.example.model.URLjson;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ReadJsonDataTravel extends AsyncTask<String, Void, String> {

    public ArrayList<Travel> travelList = new ArrayList<>();

    @Override
    public String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            java.net.URL url = new URL(strings[0]);
            Log.d("ReadJson", "URLjson done");
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("ReadJson", "Chay load");
        Log.d("ReadJson", s);


        try {
            JSONObject obj = new JSONObject(s);
            JSONArray array = obj.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                int id = array.getJSONObject(i).getInt("id");
                String name = array.getJSONObject(i).getString("name");
                String place = array.getJSONObject(i).getString("place");
                String feature = array.getJSONObject(i).getString("feature");
                String category_id = array.getJSONObject(i).getString("category_id");
                String lat = array.getJSONObject(i).getString("lat");
                String lng = array.getJSONObject(i).getString("lng");
                String created_at = array.getJSONObject(i).getString("created_at");
                String updated_at = array.getJSONObject(i).getString("updated_at");
                String deleted_at = array.getJSONObject(i).getString("deleted_at");

                Travel travel = new Travel(id, name, place, feature, category_id, Double.parseDouble(lat), Double.parseDouble(lng), created_at, updated_at, deleted_at);
                travelList.add(travel);

                ReadJsonImageTravel readImageJson = new ReadJsonImageTravel(travelList.get(i));
                readImageJson.execute(URLjson.getURLImageTravel("" + travelList.get(i).getId()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
