package com.miomio;

import static com.miomio.NoteActivity.RESULT_NO_NOTES_CHANGE;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {
    private static final int NOTE_ACTIVITY_REQUEST_CODE = 0;
    private NoteRecyclerViewAdapter adapter;
    private NoteModel noteModel;
    private TextView emptyView;
    private TextInputEditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteModel = new NoteModel(this);


        DrawerLayout drawerLayout = findViewById(R.id.mainLayout);
        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setNestedScrollingEnabled(false);
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setItemViewCacheSize(20);
        emptyView = findViewById(R.id.no_notes_text);
        searchInput = findViewById(R.id.input_note_search);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);


        adapter = new NoteRecyclerViewAdapter(this, drawerLayout, noteModel);

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

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(searchInput.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        floatingActionButton.setOnClickListener(view -> {


            Intent intent = new Intent(this, NoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(Note.NOTE, 7);
            intent.putExtra(NoteActivity.NOTE_ACTIVITY_BUNDLE, bundle);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            NoteFragment noteFragment = new NoteFragment(noteModel, null);
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


    private void setEmptyViewVisibility() {
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