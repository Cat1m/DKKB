package com.hungduy.honghunghospitalapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hungduy.honghunghospitalapp.Database.DAO.BacSiDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.BacSiDetailDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.CauHoiKhaiBaoYTeDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.DanTocDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.DichVuDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.KetQuaLuuDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.KhuPhoDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.LoaiDichVuDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.PhuongXaDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.QuanHuyenDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.TinTucDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospitalapp.Database.DAO.UserDataDAO;
import com.hungduy.honghunghospitalapp.Database.Model.BacSi;
import com.hungduy.honghunghospitalapp.Database.Model.BacSiDetail;
import com.hungduy.honghunghospitalapp.Database.Model.CauHoiKhaiBaoYTe;
import com.hungduy.honghunghospitalapp.Database.Model.DanToc;
import com.hungduy.honghunghospitalapp.Database.Model.DichVu;
import com.hungduy.honghunghospitalapp.Database.Model.KetQuaLuu;
import com.hungduy.honghunghospitalapp.Database.Model.KhuPho;
import com.hungduy.honghunghospitalapp.Database.Model.LoaiDichVu;
import com.hungduy.honghunghospitalapp.Database.Model.PhuongXa;
import com.hungduy.honghunghospitalapp.Database.Model.QuanHuyen;
import com.hungduy.honghunghospitalapp.Database.Model.QuocGia;
import com.hungduy.honghunghospitalapp.Database.Model.TinTuc;
import com.hungduy.honghunghospitalapp.Database.Model.TinhThanh;
import com.hungduy.honghunghospitalapp.Database.Model.UserData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
        TinhThanh.class,
        QuocGia.class,
        QuanHuyen.class,
        PhuongXa.class,
        KhuPho.class,
        UserData.class,
        CauHoiKhaiBaoYTe.class,
        TinTuc.class,
        BacSi.class,
        DanToc.class,
        LoaiDichVu.class,
        DichVu.class,
        KetQuaLuu.class,
        BacSiDetail.class
        },
        version = 1,
        exportSchema = true)
public abstract class LocalDB extends RoomDatabase {
    private static volatile LocalDB INSTANCE;

    public abstract TinhThanhDAO tinhThanhDAO();
    public abstract QuocGiaDAO quocGiaDAO();
    public abstract QuanHuyenDAO quanHuyenDAO();
    public abstract PhuongXaDAO phuongXaDAO();
    public abstract KhuPhoDAO khuPhoDAO();
    public abstract UserDataDAO userDataDAO();
    public abstract CauHoiKhaiBaoYTeDAO kbytdao();
    public abstract TinTucDAO tinTucDAO();
    public abstract BacSiDAO bacSiDAO();
    public abstract DanTocDAO danTocDAO();
    public abstract DichVuDAO dichVuDAO();
    public abstract LoaiDichVuDAO loaiDichVuDAO();
    public abstract KetQuaLuuDAO ketQuaLuuDAO();
    public abstract BacSiDetailDAO bacSiDetailDAO();


    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LocalDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDB.class, "Local.db")
                            .createFromAsset("data.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static LocalDB.Callback sRoomDatabaseCallback = new LocalDB.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TinhThanhDAO dao = INSTANCE.tinhThanhDAO();
                dao.deleteAll();

            });
        }
    };
}
