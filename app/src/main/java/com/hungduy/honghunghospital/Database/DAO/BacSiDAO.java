package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.BacSi;
import com.hungduy.honghunghospital.Database.Model.TinTuc;

import java.util.List;

@Dao
public interface BacSiDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BacSi us);

    @Query("DELETE FROM bacsi")
    void deleteAll();

    @Query("SELECT * FROM bacsi ")
    List<BacSi> getAll();

    @Query("SELECT * FROM bacsi where id = :id")
    BacSi getBacSi(int id);

}
