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

    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;
    private MaterialToolbar toolbar;
    private NoteController controller;

    NoteFragment(NoteController controller) {

        this.controller = controller;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        titleEditText = view.findViewById(R.id.input_note_title);
        contentEditText = view.findViewById(R.id.input_note_content);
        toolbar = view.findViewById(R.id.note_tool_bar);
        toolbar.setNavigationOnClickListener(x -> getActivity().onBackPressed());

        if (controller.isEditMode()) {
            setContentOfTextFields();

        }
        titleEditText.addTextChangedListener(
                new NoteTextWatcher(value -> controller.onTitleChanged(value))
        );
        contentEditText.addTextChangedListener(
                new NoteTextWatcher(value -> controller.onContentChanged(value))
        );

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            controller.setNote((Note) savedInstanceState.getSerializable(Note.NOTE));

        }

        if (!controller.isEditMode()) {
            controller.newNote();
        } else {
            setContentOfTextFields();
        }
        // setup toolbar on menu item click
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.note_menu_delete:
                    controller.deleteNote();
                    getActivity().onBackPressed();
                    return true;


            }

            return false;
        });


    }

    private void setContentOfTextFields() {
        titleEditText.setText(controller.getNote().getTitle());
        contentEditText.setText(controller.getNote().getContent());
    }

    private void deleteNoteIfEmpty() {
        if (controller.getNote() != null && controller.getNote().isEmpty()) {
            controller.deleteNote();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        deleteNoteIfEmpty();
    }
}
