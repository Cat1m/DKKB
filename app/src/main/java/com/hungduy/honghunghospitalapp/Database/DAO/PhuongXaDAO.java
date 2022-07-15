package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.PhuongXa;

import java.util.List;

@Dao
public interface PhuongXaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PhuongXa phuongXa);

    @Update()
    void update(PhuongXa phuongXa);

    @Query("DELETE FROM PhuongXa")
    void deleteAll();

    @Query("SELECT * FROM PhuongXa")
    List<PhuongXa> getAll();

    @Query("SELECT * FROM PhuongXa where MaTinhThanh= :matinhthanh")
    List<PhuongXa> getPhuongXaByTinhThanh(int matinhthanh);

    @Query("SELECT * FROM PhuongXa where MaQuanHuyen= :maquanhuyen")
    List<PhuongXa> getPhuongXaByQuanHuyen(int maquanhuyen);

    @Query("SELECT * FROM PhuongXa where ma= :maphuongxa Limit 1")
    PhuongXa  getPhuongXa(int maphuongxa);
}
