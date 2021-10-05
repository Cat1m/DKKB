package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.TinhThanh;
import com.hungduy.honghunghospital.Database.Model.UserData;

import java.util.List;

@Dao
public interface UserDataDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserData us);

    @Query("DELETE FROM UserData")
    void deleteAll();

    @Query("SELECT * FROM UserData")
    List<UserData> getAll();
}
