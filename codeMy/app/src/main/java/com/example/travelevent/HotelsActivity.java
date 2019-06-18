package com.example.travelevent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.R;
import com.example.bottomnavigation.MapsActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.travelevent.Constants.USER_TOKEN;


/**
 * Display list of hotels in destination city
 */
public class HotelsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_SELECTED_CITY = "KEY_SELECTED_CITY";

    @BindView(R.id.hotel_list)
    RecyclerView recyclerView;
    @BindView(R.id.select_city)
    Button selectCity;
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.layout)
    LinearLayout layout;
    private Activity mActivity;

    private Handler mHandler;
    private String mToken;

    private CitySearchModel mSelectedCity;

    private ArrayList<CitySearchModel> mSearchCities = new ArrayList<>();
    private     List<HotelsModel> hotelsModelList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mHandler = new Handler(Looper.getMainLooper());
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mToken = mSharedPreferences.getString(USER_TOKEN, null);

        fetchCitiesList();

        setTitle("Events");

        selectCity.setOnClickListener(this);

        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SELECTED_CITY)) {
         //   mSelectedCity = savedInstanceState.getParcelable(KEY_SELECTED_CITY);

                //selectCity.setText(String.format(getString(R.string.showing_hotels), mSelectedCity.getName()));
            //    getCityInfo();

        getHotelList();

      //  }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Calls API to get hotel list
     */
    private void getHotelList() {
//        HotelsModel hotelsModel;
//        List<HotelsModel> hotelsModelList = new ArrayList<>();
//        hotelsModel = new HotelsModel("title",
//            "address",
//            "phone",
//            "href",
//            1,
//            1.0,1l
//        );
//        hotelsModelList.add(hotelsModel);
//        recyclerView.setAdapter(new HotelsAdapter(HotelsActivity.this, hotelsModelList));
        ReadJsonDataTravel1 readJsonDataTravel1=new ReadJsonDataTravel1();
        readJsonDataTravel1.execute("http://3.82.158.167/api/events");


       // String uri = API_LINK_V2 + "get-places/" + latitude + "/" + longitude + "/accommodation";
//        String uri = "http://3.82.158.167/api/events";
//        Log.v("EXECUTING", uri);
//
//        //Set up client
//        OkHttpClient client = new OkHttpClient();
//        //Execute request
//        RequestBody requestBody = new MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("username", "palleducanh9@gmail.com")
//            .addFormDataPart("password", "!Khoa1234")
//            .build();
//
//        Request request = new Request.Builder()
//                .header("Authorization", "Token " + mToken)
//                .url(uri)
//                .post(requestBody)
//                .build();
//        //Setup callback
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("Request Failed", "Message : " + e.getMessage());
//                mHandler.post(() -> networkError());
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
////                final String res = Objects.requireNonNull(response.body()).string();
////                Log.v("RESPONSE", res + " ");
////                mHandler.post(() -> {
////                    if (response.isSuccessful() && response.body() != null) {
////                        try {
////                            JSONArray feedItems = new JSONArray(res);
////                            Log.v("response", feedItems + " ");
////                            selectCity.setVisibility(View.GONE);
////                            layout.setVisibility(View.VISIBLE);
////                            animationView.setVisibility(View.GONE);
////                            textView.setVisibility(View.GONE);
////                            int itemsSize = feedItems.length();
////                            if (itemsSize > 0) {
////                                /*
////                                 * Extracting data from json and adding it to the model
////                                 * then adding that model object to the list
////                                 */
////                                HotelsModel hotelsModel;
////                                List<HotelsModel> hotelsModelList = new ArrayList<>();
////                                JSONObject jo;
////                                try {
////                                    for (int i = 0; i < itemsSize; i++) {
////                                        jo = feedItems.getJSONObject(i);
////                                        hotelsModel = new HotelsModel(jo.getString("title"),
////                                                jo.getString("address"),
////                                                jo.optString("phone", "000"),
////                                                jo.optString("href"),
////                                                jo.getInt("distance"),
////                                                jo.getDouble("latitude"),
////                                                jo.getDouble("longitude"));
////                                        hotelsModelList.add(hotelsModel);
//
////                                    }
////                                    //Passing the data list to the adapter
////                                    recyclerView.setAdapter(new HotelsAdapter(HotelsActivity.this, hotelsModelList));
////                                } catch (JSONException je) {
////                                    je.printStackTrace();
////                                    networkError();
////                                }
////                            } else {
////                                noResults();
////                            }
////                        } catch (JSONException e1) {
////                            e1.printStackTrace();
////                            networkError();
////                        }
////                    } else {
////                        networkError();
////                    }
////                });
//            }
//        });
    }

    /**
     * Fetches the list cities from server
     */
    private void fetchCitiesList() {

        String uri ="http://3.82.158.167/api/events";
        Log.v("EXECUTING", uri);

        //Set up client
        OkHttpClient client = new OkHttpClient();
        //Execute request
        final Request request = new Request.Builder()
                .header("Authorization", "Token " + mToken)
                .url(uri)
                .build();
        //Setup callback
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Request Failed", "Message : " + e.getMessage());
                mHandler.post(() -> networkError());
            }

            @Override
            public void onResponse(Call call, final Response response) {
                mHandler.post(() -> {
                    if (response.isSuccessful()) {
//                        try {
//                            String res = response.body().string();
//                            Log.v("RESULT", res);
//                            JSONArray ar = new JSONArray(res);
//                            for (int i = 0; i < ar.length(); i++) {
//                                mSearchCities.add(new CitySearchModel(
//                                        ar.getJSONObject(i).getString("place"),
//                                        ar.getJSONObject(i).optString("image"),
//                                        ar.getJSONObject(i).getString("id")));
//                            }
//                        } catch (JSONException | IOException e) {
//                            e.printStackTrace();
//                            networkError();
//                            Log.e("ERROR", "Message : " + e.getMessage());
//                        }

                        //   ReadJsonDataTravel readJson =new ReadJsonDataTravel();
                          //  readJson.execute(uri);

                    } else {
                        Log.e("ERROR", "Network error");
                        networkError();
                    }
                });
            }
        });

    }


    public void getCityInfo() {
//
//        animationView.setVisibility(View.VISIBLE);
//
//      //  String uri = API_LINK_V2 + "get-city/" + cityId;
//        Log.v("EXECUTING", uri);
//        OkHttpClient client = new OkHttpClient();
//
//        final Request request = new Request.Builder()
//                .header("Authorization", "Token " + mToken)
//                .url(uri)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                mHandler.post(() -> networkError());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful() && response.body() != null) {
//
//                    final String res = Objects.requireNonNull(response.body()).string();
//                    try {
//                        Log.v("Response", res);
//                        JSONObject responseObject = new JSONObject(res);
//                        String latitude = responseObject.getString("latitude");
//                        String longitude = responseObject.getString("longitude");
                        getHotelList();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        networkError();
//                    }
//                } else {
//                    networkError();
//                }
//            }
//        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_city:
                new CitySearchDialogCompat(HotelsActivity.this, getString(R.string.search_title),
                        getString(R.string.search_hint), null, mSearchCities,
                        (SearchResultListener<CitySearchModel>) (dialog, item, position) -> {
                            mSelectedCity = item;
                            String selectedCity = item.getId();
                            selectCity.setText(String.format(getString(R.string.showing_hotels), item.getName()));
                            dialog.dismiss();
                            getCityInfo();
                        }).show();
                recyclerView.setAdapter(null);
                break;
        }
    }


    /**
     * save selected city to bundle
     * in case of configuration change like device screen rotation.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_CITY, mSelectedCity);
    }

    // TODO :: Move adapter to a new class
    public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.HotelsViewHolder> {

        private final Context mContext;
        private List<HotelsModel> mHotelsModelList=new ArrayList<>();

        HotelsAdapter(Context context, List<HotelsModel> mHotelsModelList) {
            this.mContext = context;
            this.mHotelsModelList = mHotelsModelList;
        }

        @NonNull
        @Override
        public HotelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_listitem, parent, false);
            return new HotelsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HotelsViewHolder holder, int position) {

            try {
                holder.title.setText(mHotelsModelList.get(position).getTitle());
                holder.description.setText(android.text.Html.fromHtml(mHotelsModelList.get(position).getAddress()));
                holder.distance.setText(new DecimalFormat("##.##").format((float) mHotelsModelList.get(position)
                        .getDistance() / 1000));
                holder.call.setOnClickListener(view -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    try {
                        intent.setData(Uri.parse("tel:" + mHotelsModelList.get(position).getPhone()));
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        networkError();
                    }
                });

                holder.map.setOnClickListener(view -> {
                    Intent browserIntent;
                    try {
//                        Double latitude =
//                                mHotelsModelList.get(position).getLatitude();
//                        Double longitude =
//                                mHotelsModelList.get(position).getLongitude();
//                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps?q=" +
//                                mHotelsModelList.get(position).getTitle() +
//                                "+(name)+@" + latitude +
//                                "," + longitude));
//                        mContext.startActivity(browserIntent);
                        int travelid =
                            mHotelsModelList.get(position).getTravelid();

                        browserIntent = MapsActivity.getStartIntent(mActivity);
                        browserIntent.putExtra("travelid",travelid);
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        networkError();
                    }
                });
                holder.book.setOnClickListener(view -> {
                    Intent browserIntent;
                    try {
                        browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mHotelsModelList.get(position).getHref()));
                        mContext.startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        networkError();
                    }
                });

                holder.expand_more_details.setOnClickListener((View view) -> {
                    if (holder.detailsLayout.getVisibility() == View.GONE) {
                        holder.detailsLayout.setVisibility(View.VISIBLE);
                        holder.expandCollapse.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    } else {
                        holder.detailsLayout.setVisibility(View.GONE);
                        holder.expandCollapse.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ERROR : ", "Message : " + e.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return mHotelsModelList.size();
        }

        //        View holder Class to hold the Views
        class HotelsViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.hotel_name)
            TextView title;
            @BindView(R.id.hotel_address)
            TextView description;
            @BindView(R.id.call)
            RelativeLayout call;
            @BindView(R.id.map)
            RelativeLayout map;
            @BindView(R.id.book)
            RelativeLayout book;
            @BindView(R.id.more_details)
            LinearLayout detailsLayout;
            @BindView(R.id.expand_collapse)
            ImageView expandCollapse;
            @BindView(R.id.distance)
            TextView distance;
            @BindView(R.id.expand_more_details)
            RelativeLayout expand_more_details;

            private HotelsViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    /**
     * Plays the network lost animation in the view
     */
    private void networkError() {
        layout.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        animationView.setAnimation(R.raw.network_lost);
        animationView.playAnimation();
    }

    /**
     * Plays the no results animation in the view
     */
    private void noResults() {
        layout.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        Toast.makeText(HotelsActivity.this, R.string.no_trips, Toast.LENGTH_LONG).show();
        animationView.setAnimation(R.raw.empty_list);
        animationView.playAnimation();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, HotelsActivity.class);
    }

    // TODO :: Move model to a new class
//    Model class to hold the data instead of passing the whole JSONObject to recyclerView
    class HotelsModel {
        private String mTitle;
        private String mAddress;
        private String mPhone;
        private String mHref;
        private int mDistance;
        private double mLatitude;
        private double mLongitude;
        private int travelid;

        public int getTravelid() {
            return travelid;
        }

        public void setTravelid(int travelid) {
            this.travelid = travelid;
        }

        HotelsModel(String mTitle, String mAddress, String mPhone, String mHref, int mDistance,
                    double mLatitude, double mLongitude, int travelid) {
            this.mTitle = mTitle;
            this.mAddress = mAddress;
            this.mPhone = mPhone;
            this.mHref = mHref;
            this.mDistance = mDistance;
            this.mLatitude = mLatitude;
            this.mLongitude = mLongitude;
            this.travelid=travelid;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getAddress() {
            return mAddress;
        }

        public String getPhone() {
            return mPhone;
        }

        public String getHref() {
            return mHref;
        }

        public int getDistance() {
            return mDistance;
        }

        public double getLatitude() {
            return mLatitude;
        }

        public double getLongitude() {
            return mLongitude;
        }
    }
    public class ReadJsonDataTravel extends AsyncTask<String, Void, String> {

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
            Log.d("ReadJson", "Chay load");
            Log.d("ReadJson", s);
            mSearchCities = new ArrayList<>();

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

                    mSearchCities.add(new CitySearchModel(
                        array.getJSONObject(i).getString("place"),
                        array.getJSONObject(i).optString("image"),
                        array.getJSONObject(i).getString("id")));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            mSearchCities = new ArrayList<>();

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray array = obj.getJSONArray("data");

                hotelsModelList = new ArrayList<>();
                Log.d("size1",String.valueOf(array.length()));
                for (int i = 0; i < array.length(); i++) {


                    HotelsModel hotelsModel;
                    Random random=new Random();
                    hotelsModel = new HotelsModel(array.getJSONObject(i).getString("name"),
                        array.getJSONObject(i).getString("place"),
                        "phone",
                        "href",
                        1000+random.nextInt(10000)
                       ,
                        1.0,1l, array.getJSONObject(i).getInt("travel_id"));

                    hotelsModelList.add(hotelsModel);
                    recyclerView.setAdapter(new HotelsAdapter(HotelsActivity.this, hotelsModelList));
                }
             Log.d("size",String.valueOf(hotelsModelList.size()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
