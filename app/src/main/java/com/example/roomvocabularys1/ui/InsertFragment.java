package com.example.roomvocabularys1.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.roomvocabularys1.MainActivity;
import com.example.roomvocabularys1.audio.OnEventListener;
import com.example.roomvocabularys1.audio.PlayAudio;
import com.example.roomvocabularys1.query.QueryAudio;
import com.example.roomvocabularys1.query.QueryCH;
import com.example.roomvocabularys1.query.QueryKK;
import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.VocabularyViewModel;
import com.example.roomvocabularys1.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InsertFragment extends Fragment{
    private static String LogTag = InsertFragment.class.getName();
    private EditText editText_en;
    private EditText editText_ch;
    private EditText editText_kk;
    private Button button_save;
    private Button button_quert;
    private Button button_query_ch;
    private VocabularyViewModel vocabularyViewModel;
    private MutableLiveData<String> vocabularykk;
    private Button playaudio;
    private PlayAudio playAudio;
    private String current_notebook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.insert_fragment,container,false);
        //((MainActivity) requireActivity()).count_fragment();
        //從ViewModel層取得資料
        //關聯ViewModel與Activity
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        current_notebook=vocabularyViewModel.getCurrent_notebook().getValue();
        playAudio=new PlayAudio(getContext());
        editText_en=view.findViewById(R.id.editText_en);
        editText_ch=view.findViewById(R.id.editText_ch);
        button_save=view.findViewById(R.id.button_save);
        button_quert=view.findViewById(R.id.button_quert);
        //textView_kk=view.findViewById(R.id.editText_kk);
        editText_kk=view.findViewById(R.id.editText_kk);
        button_query_ch=view.findViewById(R.id.button_quert_ch);
        playaudio=view.findViewById(R.id.playaudio);
        //初始化MutableLiveData
        if (vocabularykk == null) {
            vocabularykk = new MutableLiveData<String>();
            //Log.d(LogTag,"new MutableLiveData" );
        }
        //觀察MutableLiveData
        vocabularykk.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    if(s.equals("0")){
                        editText_kk.setHint(R.string.hint_input3_notfound);
                        editText_kk.setHintTextColor(getResources().getColor(R.color.colorred));
                    }
                    else{
                        editText_kk.setText(s);
                    }
                }
                else {
                    Utils.show_toast(getContext(),"無法查詢\n請檢察網路");
                }
            }
        });
        vocabularyViewModel.getVocabularych().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    if(s.equals("0")){
                        editText_ch.setHint(R.string.hint_input3_notfound);
                        editText_ch.setHintTextColor(getResources().getColor(R.color.colorred));
                    }
                    else{
                        editText_ch.setText(s);
                    }
                }
                else {
                    Utils.show_toast(getContext(),"無法查詢\n請檢察網路");
                }
            }
        });

        //把MutableLiveData設在QueryKK.java裡
        /*queryKK.getVocabularykk().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("0")){
                    textView_kk.setHint(R.string.hint_input3_notfound);
                    textView_kk.setHintTextColor(getResources().getColor(R.color.colorred));
                }
                else{
                    textView_kk.setText(s);
                }
            }
        });*/
        //儲存
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editText_en.getText()) || TextUtils.isEmpty(editText_ch.getText())){
                    if(TextUtils.isEmpty(editText_en.getText())){
                        editText_en.setHint(R.string.hint_input1_impty);
                        editText_en.setHintTextColor(getResources().getColor(R.color.colorred));
                        Log.d(LogTag,"editText_en NULL");
                    }
                    if(TextUtils.isEmpty(editText_ch.getText())){
                        editText_ch.setHint(R.string.hint_input2_impty);
                        editText_ch.setHintTextColor(getResources().getColor(R.color.colorred));
                        Log.d(LogTag,"editText_ch NULL");
                    }
                }
                else{
                    Log.d(LogTag,"INPUT_OK");
                    String vocabulary_en=editText_en.getText().toString();
                    String vocabulary_ch=editText_ch.getText().toString();
                    String vocabulary_kk=editText_kk.getText().toString();
                    Vocabulary vocabulary=new Vocabulary(vocabulary_en,vocabulary_ch,vocabulary_kk,current_notebook);
                    vocabularyViewModel.insert(vocabulary);
                    vocabularyViewModel.getVocabularych().setValue("");//清空
                    requireActivity().onBackPressed();
                }
            }
        });
        //查詢KK音標
        //執行QueryKK.quert(input)反回String 在setText會出錯 因查詢是新開Thread
        //動態更新介面==>用livedata
        button_quert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editText_en.getText())){
                    editText_en.setHint(R.string.hint_input1_impty);
                    editText_en.setHintTextColor(getResources().getColor(R.color.colorred));
                    Log.d(LogTag,"editText_en NULL");
                }
                else{
                    //String t1=queryKK.get_vocabularykk(editText_en.getText().toString());
                    //String t1="123";
                    //vocabularykk.setValue(t1);
                    QueryKK queryKK=new QueryKK(vocabularykk);
                    queryKK.setWord(editText_en.getText().toString());
                    queryKK.execute();
                }
            }
        });
        //查詢翻譯
        button_query_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText_en.getText())){
                    editText_en.setHint(R.string.hint_input1_impty);
                    editText_en.setHintTextColor(getResources().getColor(R.color.colorred));
                    Log.d(LogTag,"editText_en NULL");
                }
                else{
                    QueryCH queryCH=new QueryCH(editText_en.getText().toString(), new OnEventListener<ArrayList<Translation>>() {
                        @Override
                        public void onSuccess(ArrayList<Translation> object) {
                            vocabularyViewModel.getTranslationlist().setValue(object);
                            ChoiceDialogCustom choiceDialogCustom= ChoiceDialogCustom.newInstance();
                            assert getFragmentManager() != null;
                            choiceDialogCustom.show(getFragmentManager(),"NoticeDialogFragment");
                            //Log.e(LogTag,"NoticeDialogFragment");
                        }

                        @Override
                        public void onFailure(ArrayList<Translation> object) {
                            editText_ch.setHint(R.string.hint_input2_notfound);
                            editText_ch.setHintTextColor(getResources().getColor(R.color.colorred));
                        }
                    },getContext());
                    queryCH.execute();
                }
            }
        });
        //audio
        playaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText_en.getText())){
                    editText_en.setHint(R.string.hint_input1_impty);
                    editText_en.setHintTextColor(getResources().getColor(R.color.colorred));
                    Log.d(LogTag,"editText_en NULL");
                }
                else{
                    QueryAudio queryAudio=new QueryAudio(editText_en.getText().toString(),new OnEventListener<String>(){

                        @Override
                        public void onSuccess(String url) {
                            playAudio.play(url);
                        }

                        @Override
                        public void onFailure(String e) {
                            Utils.show_toast(getContext(),"ERROR: "+e);
                        }
                    },getContext());
                    queryAudio.execute();
                }
            }
        });
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LogTag,"onDestroy");
        playAudio.stop();
    }

}
