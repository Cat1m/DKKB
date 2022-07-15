package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.BacSiDetail;

import java.util.List;

@Dao
public interface BacSiDetailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BacSiDetail bs);

    @Update()
    void update(BacSiDetail bs);

    @Query("DELETE FROM ChiTietBacSi")
    void deleteAll();

    @Query("SELECT * FROM ChiTietBacSi")
    List<BacSiDetail> getAll();


    @Query("SELECT * FROM ChiTietBacSi where MaBS= :ma Limit 1")
    BacSiDetail getChiTietBS(int ma);
}
