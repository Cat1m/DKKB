package com.hungduy.honghunghospitalapp.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospitalapp.Database.Model.KetQuaLuu;

import java.util.List;

@Dao
public interface KetQuaLuuDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KetQuaLuu kq);

    @Update()
    void update(KetQuaLuu kq);

    @Query("DELETE FROM KetQuaLuu")
    void deleteAll();

    @Query("SELECT * FROM KetQuaLuu Order by ma desc")
    List<KetQuaLuu> getAll();


    @Query("SELECT * FROM KetQuaLuu where ma= :ma Limit 1")
    KetQuaLuu getKetQuaLuu(int ma);
}
