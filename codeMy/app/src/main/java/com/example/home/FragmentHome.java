package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.R;
import com.example.controller.ReadJsonDataEvent;
import com.example.controller.ReadJsonDataTravel;
import com.example.model.Category;
import com.example.bottomnavigation.FadePageTransformer;
import com.example.model.Event;
import com.example.model.TopicEvent;
import com.example.model.URLjson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private RecyclerView viewListCategories;
    private RecyclerView viewListTopicEvent;
    private ViewPager viewPager;
    private Slider slider;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 3000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    GridView grid;
    ArrayList<Category> listCategories;
    ArrayList<Event> listEvents;
    ArrayList<TopicEvent> topicEvent;
    Date todayDate = new Date();
    TextView numberNotification;
    int notification = 0;

    public FragmentHome() {
    }

    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEventList(URLjson.getURLEvent());

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(listEvents != null)
                                {

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date1 = new Date();
                                    System.out.println(sdf.format(date1));
                                    for (int i=0; i< listEvents.size();i++)
                                    {
                                        Date date2 = null;
                                        try {
                                            date2 = sdf.parse(listEvents.get(i).getStart_time());
                                            if (date1.compareTo(date2) < 0) {
                                                notification++;
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    Log.d("nopti" , ""+notification);
                                    numberNotification = getActivity().findViewById(R.id.numberNotification);
                                    numberNotification.setText(""+notification);
                                }
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewpager);

        TabLayout tabLayout = view.findViewById(R.id.tabDots);

        tabLayout.setupWithViewPager(viewPager, true);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slider.getCount()) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
                viewPager.setPageTransformer(true, new FadePageTransformer());
            }
        };

        listCategories = new ArrayList<>();
        listCategories.add(new Category(7,"Đường phố", R.drawable.cate_duong_pho));
        listCategories.add(new Category(3,"Sông", R.drawable.cate_song));
        listCategories.add(new Category(5,"Chùa", R.drawable.cate_chua));
        listCategories.add(new Category(2,"Núi", R.drawable.cate_nui));
        listCategories.add(new Category(1,"Biển", R.drawable.cate_bien));
        listCategories.add(new Category(4,"Hồ", R.drawable.cate_ho));
        listCategories.add(new Category(8,"Hang động", R.drawable.cate_hang_dong));
        listCategories.add(new Category(0,"Kiến trúc", R.drawable.cate_kien_truc));
        listCategories.add(new Category(0,"Sa mạc", R.drawable.cate_sa_mac));
        listCategories.add(new Category(6,"Khách sạn", R.drawable.cate_khach_san));

        viewListCategories = view.findViewById(R.id.listCategories);
        RecyclerView.LayoutManager layoutCategories = new GridLayoutManager(getActivity(), 5);
        viewListCategories.setLayoutManager(layoutCategories);

        MyAdapter myAdapter = new MyAdapter(getActivity(), listCategories);
        viewListCategories.setAdapter(myAdapter);
        viewListCategories.setItemAnimator(new DefaultItemAnimator());
        viewListCategories.setNestedScrollingEnabled(false);

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

        slider = new Slider(getActivity(), images);
        viewPager.setAdapter(slider);

        topicEvent = new ArrayList<>();
        topicEvent.add(new TopicEvent(1, R.drawable.topic_am_nhac, "Ca nhạc"));
        topicEvent.add(new TopicEvent(2, R.drawable.topic_am_thuc, "Ẩm thực"));
        topicEvent.add(new TopicEvent(3, R.drawable.topic_van_hoa, "Văn hóa"));
        topicEvent.add(new TopicEvent(4, R.drawable.topic_game, "Game"));

//        viewListTopicEvent = view.findViewById(R.id.categoryEvent);
//        RecyclerView.LayoutManager layoutTopicEvent = new GridLayoutManager(getActivity(), 4);
//        viewListTopicEvent.setLayoutManager(layoutTopicEvent);
//        TopicEventAdapter topicEventAdapter = new TopicEventAdapter(getActivity(), topicEvent);
//        viewListTopicEvent.setAdapter(topicEventAdapter);
//        viewListTopicEvent.setItemAnimator(new DefaultItemAnimator());
//        viewListTopicEvent.setNestedScrollingEnabled(false);

        SearchView searchView = view.findViewById(R.id.search);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    String key = searchView.getQuery().toString();
                    Intent intent = new Intent(getContext(), CategoryTravel.class);
                    intent.putExtra("KEY_WORD",key );
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });
        Log.d("nopti" , ""+notification);




        return view;
    }
    public void getEventList(String url) {
        ReadJsonDataEvent readJson = new ReadJsonDataEvent();
        readJson.execute(url);
        listEvents = readJson.eventList;
    }

}
