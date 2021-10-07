package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospital.Database.Model.KhuPho;
import com.hungduy.honghunghospital.Database.Model.PhuongXa;
import com.hungduy.honghunghospital.Database.Model.QuanHuyen;

import java.util.List;

@Dao
public interface QuanHuyenDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(QuanHuyen quanHuyen);

    @Update()
    void update(QuanHuyen quanHuyen);

    @Query("DELETE FROM QuanHuyen")
    void deleteAll();

    @Query("SELECT * FROM QuanHuyen")
    List<QuanHuyen> getAll();

    @Query("SELECT * FROM QuanHuyen where MaTinhThanh= :matinhthanh")
    List<QuanHuyen> getQuanHuyenByTinhThanh(int matinhthanh);

    @Query("SELECT * FROM QuanHuyen where ma= :maquanhuyen Limit 1")
    QuanHuyen getQuanHuyen(int maquanhuyen);
}
