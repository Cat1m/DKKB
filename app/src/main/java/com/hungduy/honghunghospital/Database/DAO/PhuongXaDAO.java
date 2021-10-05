package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hungduy.honghunghospital.Database.Model.PhuongXa;
import com.hungduy.honghunghospital.Database.Model.QuanHuyen;

import java.util.List;

@Dao
public interface PhuongXaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PhuongXa phuongXa);

    @Query("DELETE FROM PhuongXa")
    void deleteAll();

    @Query("SELECT * FROM PhuongXa")
    List<PhuongXa> getAll();

    @Query("SELECT * FROM PhuongXa where MaTinhThanh= :matinhthanh")
    List<PhuongXa> getPhuongXaByTinhThanh(int matinhthanh);

    @Query("SELECT * FROM PhuongXa where MaQuanHuyen= :maquanhuyen")
    List<PhuongXa> getPhuongXaByQuanHuyen(int maquanhuyen);
}
