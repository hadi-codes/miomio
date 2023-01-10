package com.miomio;

import static com.miomio.NoteActivity.RESULT_NO_NOTES_CHANGE;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteModel = new NoteModel(this);


        DrawerLayout drawerLayout = findViewById(R.id.mainLayout);
        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, NoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(Note.NOTE, 7);
            intent.putExtra(NoteActivity.NOTE_ACTIVITY_BUNDLE, bundle);

//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                int code = result.getResultCode();
//                if (code == RESULT_NO_NOTES_CHANGE) return;
//                noteModel.loadNotes();
//
//            }).launch(intent);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            NoteFragment noteFragment = new NoteFragment(noteModel, null);
            fragmentTransaction.replace(R.id.mainLayout, noteFragment, NoteFragment.TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

        adapter = new NoteRecyclerViewAdapter(this, drawerLayout, noteModel);

        notesRecyclerView.setAdapter(adapter);


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
}