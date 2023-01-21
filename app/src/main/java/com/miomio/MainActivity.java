package com.miomio;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private NoteRecyclerViewAdapter adapter;
    private NoteController controller;
    private TextView emptyView;
    private TextInputEditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // create a new NoteController with a new NoteModel
        controller = new NoteController(new NoteModel(this));

        /* set up the notes recycler view */
        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setNestedScrollingEnabled(false);
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setItemViewCacheSize(20);

        /* set up the empty view */
        emptyView = findViewById(R.id.no_notes_text);

        /* set up the search input */
        searchInput = findViewById(R.id.input_note_search);

        /* set up the FAB for adding new notes */
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

        /* initialize the adapter and set it to the recycler view */
        adapter = new NoteRecyclerViewAdapter(this, controller);
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

        /* adding note text watcher to update search results */
        searchInput.addTextChangedListener(new NoteTextWatcher(value -> {
            adapter.filter(searchInput.getText().toString());

        }));


        /* adding on click listener to push NoteFragment */
        floatingActionButton.setOnClickListener(view -> {


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            controller.onAddNoteClicked(fragmentTransaction);
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

    /**
     * set the visibility of the empty view
     * if there are no notes, show the empty view
     * otherwise, hide the empty view
     */
    void setEmptyViewVisibility() {
        if (adapter.getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * set the visibility of the search input
     * if there are no notes, hide the search input
     * otherwise, show the search input
     */
    private void setSearchInputVisibility() {


        if (adapter.getItemCount() == 0 && searchInput.getText().toString().isEmpty() && !searchInput.hasFocus()) {
            searchInput.setVisibility(View.GONE);
        } else {
            searchInput.setVisibility(View.VISIBLE);
        }
    }
}