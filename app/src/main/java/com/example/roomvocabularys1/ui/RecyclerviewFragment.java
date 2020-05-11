package com.example.roomvocabularys1.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomvocabularys1.MainActivity;
import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.database.Vocabulary;
import com.example.roomvocabularys1.VocabularyAdapter;
import com.example.roomvocabularys1.VocabularyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerviewFragment extends Fragment {
    private FloatingActionButton buttoninsert;
    private Button buttondelete;
    private StringBuilder text= new StringBuilder();
    private VocabularyViewModel vocabularyViewModel;
    private LiveData<List<Vocabulary>> LiveDatalist;
    private RecyclerView recyclerView;
    private VocabularyAdapter vocabularyAdapter;
    private Context RecyclerviewFragmentContext;
    private View view;
    private String current_notebook;

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }*/
    //return Layout文件
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RecyclerviewFragment", "onCreate");
        setRetainInstance(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId  = item.getItemId();
        if(menuItemId==R.id.listbyalphabetized){
            listbyalphabetized();
            /*RecyclerviewFragment listmethod=(RecyclerviewFragment) getSupportFragmentManager().findFragmentByTag("RFragment");
            if(listmethod!=null){
                listmethod.listbyalphabetized();
            }
            else{
                Utils.show_toast(getApplicationContext(),"無法重新整理");
            }*/
        }
        if(menuItemId==R.id.listbyid){
            listbyid();
            /*RecyclerviewFragment listmethod=(RecyclerviewFragment) getSupportFragmentManager().findFragmentByTag("RFragment");
            if(listmethod!=null){
                listmethod.listbyid();
            }
            else{
                Utils.show_toast(getApplicationContext(),"無法重新整理");
            }*/
        }
        return super.onOptionsItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //沒有setRetainInstance View view=inflater.inflate(R.layout.recyclerview_fragment,container,false);
        if(view==null){
            view=inflater.inflate(R.layout.recyclerview_fragment,container,false);
            Log.d("RecyclerviewFragment", "RCreateView");
        }
        //float animationscale = getResources().getDisplayMetrics().density;
        //viewback=((MainActivity) requireActivity()).get_backview();
        //改良版
        //從ViewModel層取得資料
        //關聯ViewModel與Activity
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        current_notebook=vocabularyViewModel.getCurrent_notebook().getValue();
        //新增recyclerview
        recyclerView=view.findViewById(R.id.recyclerview);
        vocabularyAdapter=new VocabularyAdapter(RecyclerviewFragmentContext,vocabularyViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerviewFragmentContext));
        recyclerView.setAdapter(vocabularyAdapter);
        //UI->ViewModel->Repository->Dao 未改良前UI->Dao
        LiveDatalist=vocabularyViewModel.queryLiveDatalistid_type(current_notebook);
        LiveDatalist.observe(getViewLifecycleOwner(), new Observer<List<Vocabulary>>() {
            @Override
            public void onChanged(List<Vocabulary> vocabularies) {
                //用recyclerview
                vocabularyAdapter.setAllvocabulary(vocabularies);//把資料放到Adapter
                vocabularyAdapter.notifyDataSetChanged();////set完告訴recyclerview資料更新
                //用recyclerview後不用寫
                /*text.delete(0,text.length());
                if(vocabularies!=null){
                    for(int i=0;i<vocabularies.size();i++){
                        Vocabulary item=vocabularies.get(i);
                        text.append(item.getId()).append(":").append(item.getVocabulary()).append(" ").append(item.getVocabulary_ch()).append("\n");
                    }
                    String t1=text.toString();
                    Log.d("SQL",t1);
                }
                else{
                    Log.d(LogTag,"is null");
                }
                TextView textView=findViewById(R.id.textView_id);
                textView.setText(text);*/
            }
        });
        //insert
        //buttoninsert= getActivity().findViewById(R.id.button_t_insert);
        buttoninsert=view.findViewById(R.id.button_t_insert);
        buttoninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).show_insertfragment();
            }
        });
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
    //view建立後回調方法
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.setTitle(current_notebook);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RecyclerviewFragmentContext=context;
    }

    public void listbyid(){
        LiveDatalist=vocabularyViewModel.queryLiveDatalistid_type(current_notebook);
        LiveDatalist.observe(getViewLifecycleOwner(), new Observer<List<Vocabulary>>() {
            @Override
            public void onChanged(List<Vocabulary> vocabularies) {
                //用recyclerview
                vocabularyAdapter.setAllvocabulary(vocabularies);//把資料放到Adapter
                vocabularyAdapter.notifyDataSetChanged();////set完告訴recyclerview資料更新
                //用recyclerview後不用寫
                /*text.delete(0,text.length());
                if(vocabularies!=null){
                    for(int i=0;i<vocabularies.size();i++){
                        Vocabulary item=vocabularies.get(i);
                        text.append(item.getId()).append(":").append(item.getVocabulary()).append(" ").append(item.getVocabulary_ch()).append("\n");
                    }
                    String t1=text.toString();
                    Log.d("SQL",t1);
                }
                else{
                    Log.d(LogTag,"is null");
                }
                TextView textView=findViewById(R.id.textView_id);
                textView.setText(text);*/
            }
        });
    }

    public void listbyalphabetized(){
        LiveDatalist=vocabularyViewModel.getLiveDatalist();
        LiveDatalist.observe(getViewLifecycleOwner(), new Observer<List<Vocabulary>>() {
            @Override
            public void onChanged(List<Vocabulary> vocabularies) {
                //用recyclerview
                vocabularyAdapter.setAllvocabulary(vocabularies);//把資料放到Adapter
                vocabularyAdapter.notifyDataSetChanged();////set完告訴recyclerview資料更新
                //用recyclerview後不用寫
                /*text.delete(0,text.length());
                if(vocabularies!=null){
                    for(int i=0;i<vocabularies.size();i++){
                        Vocabulary item=vocabularies.get(i);
                        text.append(item.getId()).append(":").append(item.getVocabulary()).append(" ").append(item.getVocabulary_ch()).append("\n");
                    }
                    String t1=text.toString();
                    Log.d("SQL",t1);
                }
                else{
                    Log.d(LogTag,"is null");
                }
                TextView textView=findViewById(R.id.textView_id);
                textView.setText(text);*/
            }
        });
    }
}
