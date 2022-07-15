package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospitalapp.Database.Model.UserData;

import java.util.List;

@Dao
public interface UserDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserData us);

    @Query("DELETE FROM UserData")
    void deleteAll();

    @Query("SELECT * FROM UserData")
    List<UserData> getAll();

    @Query("SELECT * FROM UserData where ConfigName = :configname")
    UserData getConfig(String configname);




}
