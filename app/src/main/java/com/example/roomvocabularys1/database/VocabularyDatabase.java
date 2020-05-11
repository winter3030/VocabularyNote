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
@Database(entities = {Vocabulary.class,NoteBook.class}, version = 4)
public abstract class VocabularyDatabase extends RoomDatabase {
    //取得DAO物件
    //有多個Entity要有多個DAO
    //一個Entity要有一個DAO操作
    public abstract VocabularyDao getvocabularydao();
    public abstract NoteBookDao getnotebookdao();

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
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                NoteBookDao dao = INSTANCE.getnotebookdao();
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                NoteBook noteBook=new NoteBook("筆記本");
                dao.insert(noteBook);
            });
        }
    };

    static final Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE vocabulary_table ADD COLUMN vocabulary_kk VARCHAR(50)");
        }
    };
    static final Migration MIGRATION_2_3=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `notebook_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `note_name` TEXT NOT NULL)");
        }
    };
    static final Migration MIGRATION_3_4=new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE vocabulary_table ADD COLUMN notebook_type VARCHAR(50) NOT NULL DEFAULT '筆記本'");
        }
    };
}
