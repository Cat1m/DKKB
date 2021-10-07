package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospital.Database.Model.KhaiBaoYTe;
import com.hungduy.honghunghospital.Database.Model.UserData;

import java.util.List;

@Dao
public interface KhaiBaoYTeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KhaiBaoYTe us);

    @Query("DELETE FROM KhaiBaoYTe")
    void deleteAll();

    @Query("SELECT * FROM KhaiBaoYTe")
    List<KhaiBaoYTe> getAll();

    @Query("SELECT * FROM KhaiBaoYTe where id = :id")
    List<KhaiBaoYTe> getKhaiBao(int id);

}
