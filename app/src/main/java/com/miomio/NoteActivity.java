package com.miomio;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.textfield.TextInputEditText;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_ACTIVITY_BUNDLE = "NOTE_ACTIVITY_BUNDLE";
    public static final int RESULT_NOTES_CHANGE = 1;
    public static final int RESULT_NO_NOTES_CHANGE = 0;


    private NoteModel noteModel;
    private Note currentNote;
    private CoordinatorLayout parent;
    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note);
        parent = findViewById(R.id.note_layout);
        titleEditText = (TextInputEditText) findViewById(R.id.input_note_title);
        contentEditText = (TextInputEditText) findViewById(R.id.input_note_content);
        Intent intent = getIntent();
        if (intent == null) return;
        Bundle bundle = intent.getBundleExtra(NOTE_ACTIVITY_BUNDLE);
        if (bundle == null) return;
        noteModel = (NoteModel) bundle.getSerializable(NoteModel.NOTE_MODEL);
        long noteId = bundle.getLong(Note.NOTE, -1);
        if (noteId != -1) {
            currentNote = noteModel.getNoteById(noteId);
            if (currentNote != null) {
                titleEditText.setText(currentNote.getTitle());
                contentEditText.setText(currentNote.getContent());
            }

        } else {
            currentNote = noteModel.newNote();


        }


        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentNote.setTitle(titleEditText.getText().toString());
                noteModel.updateNote(currentNote);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentNote.setContent(contentEditText.getText().toString());
                noteModel.updateNote(currentNote);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


    }

}

