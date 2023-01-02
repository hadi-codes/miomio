package com.miomio;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.miomio.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteModel noteModel = new NoteModel(getApplicationContext());
        noteModel.addObserver(this);
        DrawerLayout drawerLayout = findViewById(R.id.mainLayout);
        RecyclerView notesRecyclerView = findViewById(R.id.notesRecyclerView);


    }


    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof NoteModel) {
            NoteModel noteModel = (NoteModel) observable;


        }

    }

}