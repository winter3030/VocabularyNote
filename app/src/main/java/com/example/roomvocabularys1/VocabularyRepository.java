package com.example.roomvocabularys1;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.database.VocabularyDao;
import com.example.roomvocabularys1.database.VocabularyDatabase;

import java.util.List;

//把MainActivity裡的查詢功能模組化
//查詢資料
public class VocabularyRepository {
    private VocabularyDatabase vocabularyDatabase;
    private VocabularyDao vocabularyDao;
    private LiveData<List<Vocabulary>> LiveDatalist;
    private LiveData<List<Vocabulary>> LiveDatalistid;

    public VocabularyRepository(Application application) {
        //新增一個Database
        vocabularyDatabase= VocabularyDatabase.getDatabase(application);
        //取得DAO
        vocabularyDao=vocabularyDatabase.getvocabularydao();
        LiveDatalist=vocabularyDao.getAlphabetizedVocabulary();
        LiveDatalistid=vocabularyDao.getIdVocabulary();
    }

    public LiveData<List<Vocabulary>> getLiveDatalist() {
        return LiveDatalist;
    }

    public LiveData<List<Vocabulary>> getLiveDatalistid() {
        return LiveDatalistid;
    }

    //RepositoryInsert
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

    /*MutableLiveData<String> get_vocabularykk(String vocabulary){
        vocabularykk=queryKK.get_vocabularykk(vocabulary);
        return vocabularykk;
    }*/
}
