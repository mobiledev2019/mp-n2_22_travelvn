package com.example.travelevent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.travelevent.Constants.BASE_TASKS;

public class UtilitiesFragment extends Fragment implements CardViewOptionsAdapter.OnItemClickListener {

    @BindView(R.id.utility_options_recycle_view)
    RecyclerView mUtilityOptionsRecycleView;
    private Activity mActivity;
    private boolean mHasMagneticSensor = true;

    public UtilitiesFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ReadJsonDataTravel1 readJsonDataTravel1=new ReadJsonDataTravel1();
        readJsonDataTravel1.execute("http://3.82.158.167/api/events");
        Log.d("11111111111","travel app ");

        View view = inflater.inflate(R.layout.activity_utilities_fragment, container, false);

        ButterKnife.bind(this, view);

        List<CardItemEntity> cardEntities = getUtilityItems();

        CardViewOptionsAdapter cardViewOptionsAdapter = new CardViewOptionsAdapter(this, cardEntities);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity.getApplicationContext());
        mUtilityOptionsRecycleView.setLayoutManager(mLayoutManager);
        mUtilityOptionsRecycleView.setItemAnimator(new DefaultItemAnimator());
        mUtilityOptionsRecycleView.setAdapter(cardViewOptionsAdapter);

        PackageManager mManager = getActivity().getPackageManager();
        boolean hasAccelerometer = mManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        boolean hasMagneticSensor = mManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
        if (!hasAccelerometer || !hasMagneticSensor) {
            this.mHasMagneticSensor = false;
        }

        return view;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity = (Activity) activity;
    }


    private List<CardItemEntity> getUtilityItems() {
        List<CardItemEntity> cardEntities = new ArrayList<>();
        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.checklist),
                        getResources().getString(R.string.text_checklist)));
        cardEntities.add(
                new CardItemEntity(
                        getResources().getDrawable(R.drawable.hotel),
                        getResources().getString(R.string.hotel)));
                      getResources().getString(R.string.text_weather);
        if (mHasMagneticSensor) {
            cardEntities.add(
                    new CardItemEntity(
                            getResources().getDrawable(R.drawable.compass),
                            getResources().getString(R.string.text_Compass)));
        }

        return cardEntities;
    }
    @Override
    public void onItemClick(int position) {
        Intent intent;
        Log.d("position", String.valueOf(position));
        switch (position) {

            case 0:
                intent = ChecklistActivity.getStartIntent(mActivity);
                startActivity(intent);

                break;
            case 1:

                intent = HotelsActivity.getStartIntent(mActivity);
                startActivity(intent);

                break;
            case 2:
                intent = CompassActivity.getStartIntent(mActivity);
                startActivity(intent);
                break;

        }

    }
    public class ReadJsonDataTravel1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
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
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            Log.d("ReadJson", s+"12321312312312");

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray array = obj.getJSONArray("data");

                Log.d("size1",String.valueOf(array.length()));
                for (int i = 0; i < array.length(); i++) {



                    BASE_TASKS.add( array.getJSONObject(i).getString("name"));



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
