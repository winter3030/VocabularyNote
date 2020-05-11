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

import com.example.roomvocabularys1.NoteBookAdapter;
import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.VocabularyViewModel;
import com.example.roomvocabularys1.database.NoteBook;

import java.util.List;

public class NoteBookFragment extends Fragment {
    private VocabularyViewModel vocabularyViewModel;
    private RecyclerView recyclerView_notebook;
    private NoteBookAdapter noteBookAdapter;
    private Context context;
    private LiveData<List<NoteBook>> LiveDataNotebook;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NoteBookFragment", "onCreate");
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // view=inflater.inflate(R.layout.insert_fragment,container,false);
        return inflater.inflate(R.layout.notebook_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar =view.findViewById(R.id.toolbar_notebook);
        toolbar.setTitle(R.string.toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        //從ViewModel層取得資料
        //關聯ViewModel與Activity
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        //新增recyclerview
        recyclerView_notebook = view.findViewById(R.id.recyclerview_notebook);
        noteBookAdapter=new NoteBookAdapter(context,vocabularyViewModel);
        recyclerView_notebook.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_notebook.setAdapter(noteBookAdapter);
        LiveDataNotebook=vocabularyViewModel.getLiveDatanotebookid();
        LiveDataNotebook.observe(getViewLifecycleOwner(), new Observer<List<NoteBook>>() {
            @Override
            public void onChanged(List<NoteBook> noteBooks) {
                noteBookAdapter.setAllnotebook(noteBooks);//把資料放到Adapter
                noteBookAdapter.notifyDataSetChanged();////set完告訴recyclerview資料更新
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notebook_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId  = item.getItemId();
        if(menuItemId==R.id.menu1){
            InsertNoteBookDialog insertNoteBookDialog= InsertNoteBookDialog.newInstance();
            assert getFragmentManager() != null;
            insertNoteBookDialog.show(getFragmentManager(),"NoteBookDialogFragment");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
