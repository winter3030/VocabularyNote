package com.example.roomvocabularys1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.ui.Translation;

import java.util.ArrayList;
import java.util.List;

//把查詢結果存到ViewModel中 UI在跟ViewModel層拿資料
public class VocabularyViewModel extends AndroidViewModel {
    private VocabularyRepository vocabularyRepository;
    private LiveData<List<Vocabulary>> LiveDatalist;
    private LiveData<List<Vocabulary>> LiveDatalistid;
    private MutableLiveData<String> vocabularych;
    private MutableLiveData<ArrayList<Translation>> translationlist;

    public VocabularyViewModel(Application application){
        super(application);
        //初始化Repository
        vocabularyRepository=new VocabularyRepository(application);
        LiveDatalist=vocabularyRepository.getLiveDatalist();
        LiveDatalistid=vocabularyRepository.getLiveDatalistid();
        //初始化MutableLiveData
        if (vocabularych == null) {
            vocabularych = new MutableLiveData<String>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
        if (translationlist == null) {
            translationlist = new MutableLiveData<ArrayList<Translation>>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
    }

    public MutableLiveData<String> getVocabularych() {
        return vocabularych;
    }

    public void setVocabularych(MutableLiveData<String> vocabularych) {
        this.vocabularych = vocabularych;
    }

    public MutableLiveData<ArrayList<Translation>> getTranslationlist() {
        return translationlist;
    }

    public LiveData<List<Vocabulary>> getLiveDatalist() {
        return LiveDatalist;
    }

    public LiveData<List<Vocabulary>> getLiveDatalistid() {
        return LiveDatalistid;
    }

    //WHY?
    public void insert(Vocabulary... word) {
        vocabularyRepository.insert(word);
    }

    public void deleteAll() {
        vocabularyRepository.deleteAll();
    }

    public void delete(Vocabulary... word) {
        vocabularyRepository.delete(word);
    }

    /*public void get_vocabularykk(String vocabulary){
        vocabularyRepository.get_vocabularykk(vocabulary);
    }*/
}
