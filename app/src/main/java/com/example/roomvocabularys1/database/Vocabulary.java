package com.example.roomvocabularys1.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity
//SQLite table
@Entity(tableName = "vocabulary_table")
public class Vocabulary {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "vocabulary_en")
    private String vocabulary;

    @NonNull
    @ColumnInfo(name = "vocabulary_ch")
    private String vocabulary_ch;

    @ColumnInfo(name = "vocabulary_kk")
    private String vocabulary_kk;


    public Vocabulary(@NonNull String vocabulary,@NonNull String vocabulary_ch,String vocabulary_kk){
        this.vocabulary=vocabulary;
        this.vocabulary_ch=vocabulary_ch;
        this.vocabulary_kk=vocabulary_kk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(@NonNull String vocabulary) {
        this.vocabulary = vocabulary;
    }

    @NonNull
    public String getVocabulary_ch() {
        return vocabulary_ch;
    }

    public void setVocabulary_ch(@NonNull String vocabulary_ch) {
        this.vocabulary_ch = vocabulary_ch;
    }

    public String getVocabulary_kk() {
        return vocabulary_kk;
    }

    public void setVocabulary_kk(String vocabulary_kk) {
        this.vocabulary_kk = vocabulary_kk;
    }
}
