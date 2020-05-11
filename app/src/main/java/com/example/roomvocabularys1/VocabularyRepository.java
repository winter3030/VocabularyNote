package com.example.roomvocabularys1;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roomvocabularys1.database.NoteBook;
import com.example.roomvocabularys1.database.NoteBookDao;
import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.database.VocabularyDao;
import com.example.roomvocabularys1.database.VocabularyDatabase;

import java.util.List;

//把MainActivity裡的查詢功能模組化
//查詢資料
public class VocabularyRepository {
    private VocabularyDatabase vocabularyDatabase;
    //Dao
    private VocabularyDao vocabularyDao;
    private NoteBookDao noteBookDao;
    //LiveData
    private LiveData<List<Vocabulary>> LiveDatalist;
    private LiveData<List<Vocabulary>> LiveDatalistid;
    private LiveData<List<NoteBook>> LiveDatanotebookid;

    public VocabularyRepository(Application application) {
        //新增一個Database
        vocabularyDatabase= VocabularyDatabase.getDatabase(application);
        //取得DAO
        vocabularyDao=vocabularyDatabase.getvocabularydao();
        noteBookDao=vocabularyDatabase.getnotebookdao();
        //取得LiveData
        LiveDatalist=vocabularyDao.getAlphabetizedVocabulary();
        LiveDatalistid=vocabularyDao.getIdVocabulary();
        LiveDatanotebookid=noteBookDao.getIdNoteBook();
    }

    //getter LiveData
    public LiveData<List<Vocabulary>> getLiveDatalist() {
        return LiveDatalist;
    }
    public LiveData<List<Vocabulary>> getLiveDatalistid() {
        return LiveDatalistid;
    }
    public LiveData<List<NoteBook>> getLiveDatanotebookid() {
        return LiveDatanotebookid;
    }

    //Vocabulary RepositoryInsert
    void insert(Vocabulary... word){
        VocabularyDatabase.databaseWriteExecutor.execute(() ->{
                vocabularyDao.insert(word);
        });
    }
    void deleteAll(){
        VocabularyDatabase.databaseWriteExecutor.execute(() ->
                vocabularyDao.deleteAll()
        );
    }
    void delete(Vocabulary... word){
        VocabularyDatabase.databaseWriteExecutor.execute(() ->
                vocabularyDao.delete(word)
        );
    }
    //Notebook
    void insert_notebook(NoteBook... word){
        VocabularyDatabase.databaseWriteExecutor.execute(() ->{
            noteBookDao.insert(word);
        });
    }
    void delete_notebook(NoteBook... word){
        VocabularyDatabase.databaseWriteExecutor.execute(() ->
                noteBookDao.delete(word)
        );
    }
}
