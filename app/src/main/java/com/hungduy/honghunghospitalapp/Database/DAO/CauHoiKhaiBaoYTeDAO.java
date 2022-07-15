package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospitalapp.Database.Model.CauHoiKhaiBaoYTe;

import java.util.List;

@Dao
public interface CauHoiKhaiBaoYTeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CauHoiKhaiBaoYTe us);

    @Query("DELETE FROM CauHoiKhaiBaoYTe")
    void deleteAll();

    @Query("SELECT * FROM CauHoiKhaiBaoYTe")
    List<CauHoiKhaiBaoYTe> getAll();

    @Query("SELECT * FROM CauHoiKhaiBaoYTe where id = :id")
    CauHoiKhaiBaoYTe getCauHoiKhaiBao(int id);

}
