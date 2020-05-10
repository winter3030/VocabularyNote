package com.example.roomvocabularys1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.roomvocabularys1.ui.InsertFragment;
import com.example.roomvocabularys1.ui.RecyclerviewFragment;
import com.example.roomvocabularys1.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private final String LogTag="MainActivity";
    //移到Repository private VocabularyDatabase vocabularyDatabase;
    //移到Repository private VocabularyDao vocabularyDao;
    //private TextView textView;
    //移到Repository private LiveData<List<Vocabulary>> LiveDatalist;
    //private List<Vocabulary> list;
    //private VocabularyViewModel vocabularyViewModel;
    //private LiveData<List<Vocabulary>> LiveDatalist;
    //private RecyclerView recyclerView;
    //private VocabularyAdapter vocabularyAdapter;
    private FragmentManager manager=getSupportFragmentManager();
    private Fragment recyclerviewFragment=new RecyclerviewFragment();
    private Fragment insertFragment=new InsertFragment();
    //private RecyclerviewFragment recyclerviewFragment;
    //private InsertFragment insertFragment;
    private String currentfragment="RFragment";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId  = item.getItemId();
        if(menuItemId==R.id.listbyalphabetized){
            RecyclerviewFragment listmethod=(RecyclerviewFragment) getSupportFragmentManager().findFragmentByTag("RFragment");
            if(listmethod!=null){
                listmethod.listbyalphabetized();
            }
            else{
                Utils.show_toast(getApplicationContext(),"無法重新整理");
            }
        }
        if(menuItemId==R.id.listbyid){
            RecyclerviewFragment listmethod=(RecyclerviewFragment) getSupportFragmentManager().findFragmentByTag("RFragment");
            if(listmethod!=null){
                listmethod.listbyid();
            }
            else{
                Utils.show_toast(getApplicationContext(),"無法重新整理");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title);
        setSupportActionBar(toolbar);
        if(savedInstanceState != null) {
            currentfragment=savedInstanceState.getString("currentfragment");
            recyclerviewFragment=manager.getFragment(savedInstanceState,"saveview1");
            //insertFragment=manager.getFragment(savedInstanceState,"saveview2");
            manager.beginTransaction().show(recyclerviewFragment).commit();
        }
        else{
            manager.beginTransaction().add(R.id.fragment_container, recyclerviewFragment,"RFragment").commit();
        }


        //新增一個Database
        //移到Repository vocabularyDatabase=VocabularyDatabase.getDatabase(this);
        //取得DAO
        //移到Repository vocabularyDao=vocabularyDatabase.getvocabularydao();

        //移到Repository LiveDatalist = vocabularyDao.getAlphabetizedVocabulary();
        //updateview();//用LiveData後不用每次都用updateview()更新 初始化LiveData後LiveData
        //會自己觀察更新
        //35~56用LiveData還是不行
        /*VocabularyDatabase.databaseWriteExecutor.execute(() ->{
            LiveDatalist = vocabularyDao.getAlphabetizedVocabulary();
        });
        LiveDatalist.observe(this, new Observer<List<Vocabulary>>() {
            @Override
            public void onChanged(List<Vocabulary> vocabularies) {
                if(vocabularies!=null){
                for(int i=0;i<vocabularies.size();i++){
                    Vocabulary item=vocabularies.get(i);
                    text.append(item.getId()).append(":").append(item.getVocabulary()).append(" ").append(item.getVocabulary_ch()).append("\n");
                    //UI不能在sub thread執行
                    TextView textView=findViewById(R.id.textView);
                    textView.setText(text);
                }
                String t1=text.toString();
                Log.d("SQL",t1);
            }
            else{
                Log.d("123","456");
            }
            }
        });*/
        //改良版
        //從ViewModel層取得資料
        //關聯ViewModel與Activity
        /*vocabularyViewModel=new ViewModelProvider(this).get(VocabularyViewModel.class);*/
        //新增recyclerview
        /*recyclerView=findViewById(R.id.recyclerview);*/
        /*vocabularyAdapter=new VocabularyAdapter(this,vocabularyViewModel);*/
        /*recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
        /*recyclerView.setAdapter(vocabularyAdapter);*/
        //UI->ViewModel->Repository->Dao 未改良前UI->Dao
        /*LiveDatalist=vocabularyViewModel.getLiveDatalist();*/
        /*LiveDatalist.observe(this, new Observer<List<Vocabulary>>() {*/
            /*@Override*/
            /*public void onChanged(List<Vocabulary> vocabularies) {*/
                //用recyclerview
                /*vocabularyAdapter.setAllvocabulary(vocabularies);//把資料放到Adapter*/
                /*vocabularyAdapter.notifyDataSetChanged();////set完告訴recyclerview資料更新*/
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
            /*}*/
        /*});*/
        //insert
        /*buttoninsert=findViewById(R.id.button_t_insert);*/
        /*buttoninsert.setOnClickListener(new View.OnClickListener() {*/
            /*@Override*/
            /*public void onClick(View view) {*/
                //移到Repository
                /*Vocabulary word1 = new Vocabulary("Hello","嗨!");
                Vocabulary word2 = new Vocabulary("World","世界~");
                VocabularyDatabase.databaseWriteExecutor.execute(() ->
                        vocabularyDao.insert(word1,word2)
                );*/
                //Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
                //vocabularyDao.insert(word1,word2);//不能
                //updateview();用LiveData後不用每次都用updateview()更新 初始化LiveData後LiveData
                //會自己觀察更新
                //改良後
                /*Vocabulary word1 = new Vocabulary("Hello","嗨!");
               Vocabulary word2 = new Vocabulary("World","世界~");
                vocabularyViewModel.insert(word1,word2);*/
                //執行InsertActivity
                //Intent intent=new Intent(MainActivity.this,);
                //startActivityForResult(intent,INSERT_ACTIVITY_REQUEST_CODE);
            /*}*/
        /*});*/
        //clear
        //buttonclear=findViewById(R.id.button_t_clear);
        //buttonclear.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                /*VocabularyDatabase.databaseWriteExecutor.execute(() ->
                        vocabularyDao.deleteAll()
                );*/
                //vocabularyDao.deleteAll();
                //vocabularyViewModel.deleteAll();
            //}
        //});

    }
    //儲存狀態
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentfragment",currentfragment);
        manager.putFragment(outState,"saveview1",recyclerviewFragment);
        //manager.putFragment(outState,"saveview2",insertFragment);
    }
    //一開始是用List 每次更新介面都要呼叫updateview() 但這個方法會有問題
    //如果寫在ExecutorService外 結果還沒查詢完 會先執行setText這時會出錯
    //如果寫在ExecutorService裡面 sub thread不能更新UI
    //改用LiveData後 用oberve的onChanged更新界面 就可解決setText的問題 不透過ViewModel->Repository
    //也可以用

    //更新介面 但不能用 因UI不能在sub thread執行
    //用LIVE DATA updateview()可以不用
    //void updateview(){
        //Lambdas表達式
        //ExecutorService子線程 也可用AsyncTask
        //VocabularyDatabase.databaseWriteExecutor.execute(() ->{
            //LiveDatalist = vocabularyDao.getAlphabetizedVocabulary();
            /*if(list!=null){
                StringBuilder text= new StringBuilder();
                for(int i=0;i<list.size();i++){
                    Vocabulary item=list.get(i);
                    text.append(item.getId()).append(":").append(item.getVocabulary()).append(" ").append(item.getVocabulary_ch()).append("\n");
                    //UI不能在sub thread執行
                    TextView textView=findViewById(R.id.textView);
                    textView.setText(text);
                }
                String t1=text.toString();
                Log.d("SQL",t1);
            }
            else{
                Log.d("123","456");
            }*/
        //});
    //}
    //onActivityResult
    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSERT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Vocabulary vocabulary=new Vocabulary(data.getStringExtra(ertActivity.VocabularyEN),
                    data.getStringExtra(ctivity.VocabularyCH));
            vocabularyViewModel.insert(vocabulary);
            Toast.makeText(this,R.string.saved,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,R.string.save_failed,Toast.LENGTH_LONG).show();
        }
    }*/
    //test
    public void count_fragment(){
        int count = manager.getBackStackEntryCount();
        //String t1=""+count;
        //Log.d(LogTag,t1);
    }
    //RecyclerviewFragment->insertfragment .addToBackStack("R_to_I")
    public void show_insertfragment(){
        currentfragment="IFragment";
        Log.d(LogTag,"now IFragment");
        insertFragment =new InsertFragment();
        manager.beginTransaction().addToBackStack("R_to_I").replace(R.id.fragment_container,insertFragment,"IFragment").commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //int count = manager.getBackStackEntryCount();
        FloatingActionButton floatingActionButton=findViewById(R.id.button_t_insert);
        floatingActionButton.show();
        //String t1=""+count;
        //Log.d(LogTag,t1);
        /*if (count == 0) {
            super.onBackPressed();
        } else {
            Log.d(LogTag,"popBackStack");
            getFragmentManager().popBackStack();
        }*/
    }

    /*public View get_backview(){
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.back_item,null);
        return view1;
    }*/

    }
