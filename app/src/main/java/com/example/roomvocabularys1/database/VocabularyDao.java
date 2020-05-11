package com.example.roomvocabularys1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roomvocabularys1.database.Vocabulary;

import java.util.List;

//DAO
@Dao
public interface  VocabularyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vocabulary... word);

    @Query("DELETE FROM vocabulary_table")
    void deleteAll();

    @Query("SELECT * from vocabulary_table ORDER BY vocabulary_en ASC")
    //原本
    //List<Vocabulary> getAlphabetizedVocabulary();
    LiveData<List<Vocabulary>> getAlphabetizedVocabulary();

    @Delete
    void delete(Vocabulary... word);

    @Query("SELECT * from vocabulary_table ORDER BY id DESC")
        //原本
        //List<Vocabulary> getAlphabetizedVocabulary();
    LiveData<List<Vocabulary>> getIdVocabulary();

    @Query("SELECT * from vocabulary_table WHERE notebook_type = :type ORDER BY id DESC")
    LiveData<List<Vocabulary>> getIdVocabulary_type(String type);

    @Query("SELECT * from vocabulary_table WHERE notebook_type = :type ORDER BY vocabulary_en ASC")
    LiveData<List<Vocabulary>> getAlphabetizedVocabulary_type(String type);
}
