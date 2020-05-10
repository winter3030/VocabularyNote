package com.example.roomvocabularys1.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//RoomDatabase
@Database(entities = {Vocabulary.class}, version = 2)
public abstract class VocabularyDatabase extends RoomDatabase {
    //取得DAO物件
    //有多個Entity要有多個DAO
    //一個Entity要有一個DAO操作
    public abstract VocabularyDao getvocabularydao();

    //定義singleton防止同時打開多個Database實例
    private static volatile VocabularyDatabase INSTANCE;
    //ExecutorService固定THREADS使用在後台THREADS上異步運行數據庫操作
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //return singleton物件
    public static VocabularyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VocabularyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VocabularyDatabase.class, "vocabulary_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            VocabularyDao dao = INSTANCE.getvocabularydao();
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                dao.deleteAll();
            });
        }
    };

    static final Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE vocabulary_table ADD COLUMN vocabulary_kk VARCHAR(50)");
        }
    };
}
