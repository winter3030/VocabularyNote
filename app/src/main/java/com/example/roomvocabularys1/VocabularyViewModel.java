package com.example.roomvocabularys1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomvocabularys1.database.NoteBook;
import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.ui.Translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//把查詢結果存到ViewModel中 UI在跟ViewModel層拿資料
public class VocabularyViewModel extends AndroidViewModel {
    private VocabularyRepository vocabularyRepository;
    private LiveData<List<Vocabulary>> LiveDatalist;
    private LiveData<List<Vocabulary>> LiveDatalistid;
    private LiveData<List<NoteBook>> LiveDatanotebookid;
    private MutableLiveData<String> vocabularych;
    private MutableLiveData<ArrayList<Translation>> translationlist;
    private MutableLiveData<String> current_notebook;
    private MutableLiveData<String> pre_notebook;

    public VocabularyViewModel(Application application){
        super(application);
        //初始化Repository
        vocabularyRepository=new VocabularyRepository(application);
        //改成動態
        //LiveDatalist=vocabularyRepository.getLiveDatalist();
        //LiveDatalistid=vocabularyRepository.getLiveDatalistid();
        LiveDatanotebookid=vocabularyRepository.getLiveDatanotebookid();
        //初始化MutableLiveData
        if (vocabularych == null) {
            vocabularych = new MutableLiveData<String>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
        if (translationlist == null) {
            translationlist = new MutableLiveData<ArrayList<Translation>>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
        if (current_notebook == null) {
            current_notebook = new MutableLiveData<String>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
        if (pre_notebook == null) {
            pre_notebook = new MutableLiveData<String>();
            pre_notebook.setValue("");
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

    /*public LiveData<List<Vocabulary>> getLiveDatalist() {
        return LiveDatalist;
    }*/

    /*public LiveData<List<Vocabulary>> getLiveDatalistid() {
            return LiveDatalistid;
        }*/
    public MutableLiveData<String> getCurrent_notebook() {
        return current_notebook;
    }
    public MutableLiveData<String> getPre_notebook() {
        return pre_notebook;
    }
    //動態查詢
    public LiveData<List<Vocabulary>> queryLiveDatalist_type(String type) {
        if(LiveDatalist==null || !current_notebook.getValue().contains(Objects.requireNonNull(pre_notebook.getValue()))){
            LiveDatalist=vocabularyRepository.queryLiveDatalist_type(type);
            return LiveDatalist;
        }
        else{
            return LiveDatalist;
        }
    }
    public LiveData<List<Vocabulary>> queryLiveDatalistid_type(String type) {
        if(LiveDatalistid==null|| !current_notebook.getValue().contains(Objects.requireNonNull(pre_notebook.getValue()))){
            LiveDatalistid=vocabularyRepository.queryLiveDatalistid_type(type);
            return LiveDatalistid;
        }
        else{
            return LiveDatalistid;
        }
    }

    public LiveData<List<NoteBook>> getLiveDatanotebookid() {
        return LiveDatanotebookid;
    }

    //WHY?
    //Vocabulary
    public void insert(Vocabulary... word) {
        vocabularyRepository.insert(word);
    }
    public void deleteAll() {
        vocabularyRepository.deleteAll();
    }
    public void delete(Vocabulary... word) {
        vocabularyRepository.delete(word);
    }
    //NoteBook
    public void insert_notebook(NoteBook... word) {
        vocabularyRepository.insert_notebook(word);
    }
    public void delete_notebook(NoteBook... word) {
        vocabularyRepository.delete_notebook(word);
    }
}
