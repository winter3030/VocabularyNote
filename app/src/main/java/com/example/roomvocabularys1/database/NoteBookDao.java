package com.example.roomvocabularys1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteBook... word);

    @Query("SELECT * from notebook_table ORDER BY id DESC")
    LiveData<List<NoteBook>> getIdNoteBook();
}
