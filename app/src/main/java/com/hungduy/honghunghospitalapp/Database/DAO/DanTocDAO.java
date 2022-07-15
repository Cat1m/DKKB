package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.DanToc;

import java.util.List;

@Dao
public interface DanTocDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DanToc danToc);

    @Update()
    void update(DanToc danToc);

    @Query("DELETE FROM DanToc")
    void deleteAll();

    @Query("SELECT * FROM DanToc")
    List<DanToc> getAll();


    @Query("SELECT * FROM DanToc where ma= :id Limit 1")
    DanToc getDanToc(int id);
}
