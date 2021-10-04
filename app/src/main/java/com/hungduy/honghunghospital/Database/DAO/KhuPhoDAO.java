package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.KhuPho;
import com.hungduy.honghunghospital.Database.Model.PhuongXa;
import com.hungduy.honghunghospital.Database.Model.QuocGia;

import java.util.List;

@Dao
public interface KhuPhoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(KhuPho khupho);

    @Query("DELETE FROM KhuPho")
    void deleteAll();

    @Query("SELECT * FROM KhuPho")
    List<KhuPho> getAll();

    @Query("SELECT * FROM KhuPho where MaPhuongXa= :maphuongxa")
    KhuPho getKhuPhoByQuanHuyen(int maphuongxa);
}
