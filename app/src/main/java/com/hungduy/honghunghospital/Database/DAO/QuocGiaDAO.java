package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospital.Database.Model.QuanHuyen;
import com.hungduy.honghunghospital.Database.Model.QuocGia;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;

import java.util.List;

@Dao
public interface QuocGiaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(QuocGia quocGia);

    @Update()
    void update(QuocGia quocGia);

    @Query("DELETE FROM QuocGia")
    void deleteAll();

    @Query("SELECT * FROM QuocGia")
    List<QuocGia> getAll();

    @Query("SELECT * FROM QuocGia where ma= :maquocgia Limit 1")
    QuocGia getQuocGia(int maquocgia);
}
