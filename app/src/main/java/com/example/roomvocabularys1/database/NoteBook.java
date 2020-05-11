package com.example.roomvocabularys1.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notebook_table")
public class NoteBook {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "note_name")
    private String note_name;

    public NoteBook(@NonNull String note_name) {
        this.note_name = note_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNote_name() {
        return note_name;
    }

    public void setNote_name(@NonNull String note_name) {
        this.note_name = note_name;
    }
}
