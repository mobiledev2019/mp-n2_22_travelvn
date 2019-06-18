package com.example.travelevent;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;



@Dao
public interface CityDao {

    @Insert
    void insert(City... city);

    @Update
    void update(City... city);

    @Delete
    void delete(City... city);

    @Query("Select * FROM city")
    City[] loadAll();

    @Query("Select * FROM city WHERE city_favourite = 1")
    City[] loadAllFavourite();

    @Query("DELETE FROM city")
    void deleteAll();
}
