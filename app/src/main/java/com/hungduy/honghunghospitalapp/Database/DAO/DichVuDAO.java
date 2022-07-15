package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.DichVu;

import java.util.List;

@Dao
public interface DichVuDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DichVu dv);

    @Update()
    void update(DichVu dv);

    @Query("DELETE FROM DichVu")
    void deleteAll();

    @Query("SELECT * FROM DichVu")
    List<DichVu> getAll();

    @Query("SELECT * FROM DichVu where ma= :maDichVu Limit 1")
    DichVu getDichVu(int maDichVu);

    @Query("SELECT * FROM DichVu where MaNhom= :maNhom")
    List<DichVu> getDichVuByNhom(int maNhom);
}
