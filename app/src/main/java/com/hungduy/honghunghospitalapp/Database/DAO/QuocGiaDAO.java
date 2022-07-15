package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.QuocGia;

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
