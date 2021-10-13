package com.hungduy.honghunghospital.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hungduy.honghunghospital.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;

import java.util.List;

@Dao
public interface KetQuaLuuDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KetQuaLuu kq);

    @Update()
    void update(KetQuaLuu kq);

    @Query("DELETE FROM KetQuaLuu")
    void deleteAll();

    @Query("SELECT * FROM KetQuaLuu")
    List<KetQuaLuu> getAll();


    @Query("SELECT * FROM KetQuaLuu where ma= :ma Limit 1")
    KetQuaLuu getKetQuaLuu(int ma);
}
