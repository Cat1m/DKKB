package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospitalapp.Database.Model.TinTuc;

import java.util.List;

@Dao
public interface TinTucDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TinTuc us);

    @Query("DELETE FROM TinTuc")
    void deleteAll();

    @Query("SELECT * FROM TinTuc ORDER BY id DESC")
    List<TinTuc> getAll();

    @Query("SELECT * FROM TinTuc where id = :id")
    TinTuc getTinTuc(int id);

}
