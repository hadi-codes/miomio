package com.miomio;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final int NOTE_ACTIVITY_REQUEST_CODE = 0;
    private NoteRecyclerViewAdapter adapter;
    private NoteController controller;
    private TextView emptyView;
    private TextInputEditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new NoteController(new NoteModel(this));


        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setNestedScrollingEnabled(false);
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setItemViewCacheSize(20);
        emptyView = findViewById(R.id.no_notes_text);
        searchInput = findViewById(R.id.input_note_search);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);


        adapter = new NoteRecyclerViewAdapter(this,  controller);

        notesRecyclerView.setAdapter(adapter);
        setEmptyViewVisibility();
        setSearchInputVisibility();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setEmptyViewVisibility();
                setSearchInputVisibility();
            }
        });

        searchInput.addTextChangedListener(new NoteTextWatcher(value -> {
            adapter.filter(searchInput.getText().toString());

        }));

        floatingActionButton.setOnClickListener(view -> {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            NoteFragment noteFragment = new NoteFragment(controller);
            fragmentTransaction.replace(R.id.mainLayout, noteFragment, NoteFragment.TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            searchInput.getText().clear();
            searchInput.clearFocus();

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }

    }


    void setEmptyViewVisibility() {
        if (adapter.getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void setSearchInputVisibility() {


        if (adapter.getItemCount() == 0 && searchInput.getText().toString().isEmpty() && !searchInput.hasFocus()) {
            searchInput.setVisibility(View.GONE);
        } else {
            searchInput.setVisibility(View.VISIBLE);
        }
    }
}