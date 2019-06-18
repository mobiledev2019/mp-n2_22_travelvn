package com.example.model;

public class Event {
    public int id;
    public String name;
    public String topic;
    private String start_time;
    private String end_time;
    private String deleteted_at;
    private String content;
    private String place;
    private int travel_id;

    public Event(int id, String name, String topic, String start_time, String end_time, String deleteted_at, String content, String place, int travel_id) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.start_time = start_time;
        this.end_time = end_time;
        this.deleteted_at = deleteted_at;
        this.content = content;
        this.place = place;
        this.travel_id = travel_id;
    }

    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getDeleteted_at() {
        return deleteted_at;
    }

    public String getContent() {
        return content;
    }

    public String getPlace() {
        return place;
    }

    public int getTravel_id() {
        return travel_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setDeleteted_at(String deleteted_at) {
        this.deleteted_at = deleteted_at;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTravel_id(int travel_id) {
        this.travel_id = travel_id;
    }
}
