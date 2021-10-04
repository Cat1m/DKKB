package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.TinhThanh;

import java.util.List;

@Dao
public interface TinhThanhDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TinhThanh tinhThanh);

    @Query("DELETE FROM TinhThanh")
    void deleteAll();

    @Query("SELECT * FROM TinhThanh")
    List<TinhThanh> getAll();
}
