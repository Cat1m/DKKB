package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.KhuPho;

import java.util.List;

@Dao
public interface KhuPhoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(KhuPho khupho);

    @Update()
    void update(KhuPho khupho);


    @Query("DELETE FROM KhuPho")
    void deleteAll();

    @Query("SELECT * FROM KhuPho")
    List<KhuPho> getAll();

    @Query("SELECT * FROM KhuPho where MaPhuongXa= :maphuongxa")
    List<KhuPho> getKhuPhoByPhuongXa(int maphuongxa);

    @Query("SELECT * FROM KhuPho where ma= :makhupho Limit 1")
    KhuPho  getKhuPho(int makhupho);
}
