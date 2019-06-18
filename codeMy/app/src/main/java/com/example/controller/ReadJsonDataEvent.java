package com.example.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.model.Event;
import com.example.model.Travel;
import com.example.model.URLjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ReadJsonDataEvent extends AsyncTask<String, Void, String> {
    public ArrayList<Event> eventList = new ArrayList<>();

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            java.net.URL url = new URL(strings[0]);
            Log.d("ReadJsonEvent", "URLjson done");
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
    protected void onPostExecute(String s) {
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
                String content = array.getJSONObject(i).getString("content");
                int travel_id = array.getJSONObject(i).getInt("travel_id");
                String topic = array.getJSONObject(i).getString("topic");
                String start_time = array.getJSONObject(i).getString("start_time");
                String end_time = array.getJSONObject(i).getString("end_time");
                String deleteted_at = array.getJSONObject(i).getString("deleted_at");

                Event event = new Event(id ,name, topic, start_time, end_time, deleteted_at, content, place,travel_id);
                eventList.add(event);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
