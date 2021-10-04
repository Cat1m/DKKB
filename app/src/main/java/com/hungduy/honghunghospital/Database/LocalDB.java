package com.hungduy.honghunghospital.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hungduy.honghunghospital.Database.DAO.QuocGiaDAO;
import com.hungduy.honghunghospital.Database.DAO.TinhThanhDAO;
import com.hungduy.honghunghospital.Database.Model.QuocGia;
import com.hungduy.honghunghospital.Database.Model.TinhThanh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TinhThanh.class, QuocGia.class}, version = 1)
public abstract class LocalDB extends RoomDatabase {
    private static volatile LocalDB INSTANCE;

    public abstract TinhThanhDAO tinhThanhDAO();
    public abstract QuocGiaDAO quocGiaDAO();

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static LocalDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDB.class, "Local.db")
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
