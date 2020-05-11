package com.example.roomvocabularys1.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.roomvocabularys1.R;
import com.example.roomvocabularys1.VocabularyViewModel;
import com.example.roomvocabularys1.database.NoteBook;

public class InsertNoteBookDialog extends DialogFragment {
    private Context context;
    private VocabularyViewModel vocabularyViewModel;
    public static InsertNoteBookDialog newInstance() {
        InsertNoteBookDialog f = new InsertNoteBookDialog();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularyViewModel=new ViewModelProvider(requireActivity()).get(VocabularyViewModel.class);
        context=getContext();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.insert_notebook_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText notebook_name=view.findViewById(R.id.editText_notebook);
        Button button_ok=view.findViewById(R.id.button_ok_notebook);
        Button button_cancel=view.findViewById(R.id.button_cancel_notebook);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(notebook_name.getText())){
                    notebook_name.setHint("請輸入筆記本名稱");
                    notebook_name.setHintTextColor(getResources().getColor(R.color.colorred));
                }
                else{
                    String notebookname=notebook_name.getText().toString();
                    NoteBook noteBook=new NoteBook(notebookname);
                    vocabularyViewModel.insert_notebook(noteBook);
                    getDialog().dismiss();
                }
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null) {
            return;
        }

        int dialogWidth = 1000;
        //int dialogHeight = 1200;
        int dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        // ... other stuff you want to do in your onStart() method
    }
}
