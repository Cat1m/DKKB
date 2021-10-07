package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospital.Database.Model.QuocGia;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;

import java.util.List;

@Dao
public interface TinhThanhDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TinhThanh tinhThanh);

    @Update()
    void update(TinhThanh tinhThanh);

    @Query("DELETE FROM TinhThanh")
    void deleteAll();

    @Query("SELECT * FROM TinhThanh")
    List<TinhThanh> getAll();


    @Query("SELECT * FROM TinhThanh where ma= :matinhthanh Limit 1")
    TinhThanh getTinhThanh(int matinhthanh);
}
