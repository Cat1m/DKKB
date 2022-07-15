package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.LoaiDichVu;

import java.util.List;

@Dao
public interface LoaiDichVuDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LoaiDichVu dv);

    @Update()
    void update(LoaiDichVu dv);

    @Query("DELETE FROM LoaiDichVu")
    void deleteAll();

    @Query("SELECT * FROM LoaiDichVu")
    List<LoaiDichVu> getAll();


    @Query("SELECT * FROM LoaiDichVu where ma= :maLoaiDichVu Limit 1")
    LoaiDichVu getLoaiDichVu(int maLoaiDichVu);
}
