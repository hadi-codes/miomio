package com.miomio;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class NoteFragment extends Fragment {
    public static final String TAG = "NoteFragment";
    private NoteModel noteModel;
    private Note note;
    private boolean isEditMode;
    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;
    private MaterialToolbar toolbar;

    NoteFragment(NoteModel noteModel, @Nullable Note note) {
        this.noteModel = noteModel;
        this.note = note;
        this.isEditMode = note != null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        titleEditText = view.findViewById(R.id.input_note_title);
        contentEditText = view.findViewById(R.id.input_note_content);
        toolbar = view.findViewById(R.id.note_tool_bar);
        toolbar.setNavigationOnClickListener(x -> getActivity().onBackPressed());

        if (isEditMode) {
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
        }
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = titleEditText.getText().toString();


                note.setTitle(value);
                noteModel.updateNote(note);
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
                note.setContent(contentEditText.getText().toString());
                noteModel.updateNote(note);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            note = (Note) savedInstanceState.getSerializable(Note.NOTE);
            isEditMode = note != null;
        }

        if (!isEditMode) {
            note = noteModel.newNote();
        } else {
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
        }
        // setup toolbar on menu item click
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.note_menu_delete:
                    noteModel.deleteNote(note);
                    getActivity().onBackPressed();
                    return true;


            }

            return false;
        });


    }

    private void deleteNoteIfEmpty() {
        if (note.isEmpty()) {
            noteModel.deleteNote(note);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        deleteNoteIfEmpty();
    }
}
