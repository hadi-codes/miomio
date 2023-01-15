package com.miomio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> implements Observer {
    private List<Note> notes = new ArrayList<>();


    private NoteModel noteModel;
    protected static ArrayList<Note> selectedNotes = new ArrayList<>();
    protected static ArrayList<MaterialCardView> checkedCards = new ArrayList<>();
    private final Context context;
    private final DrawerLayout drawerLayout;

    public NoteRecyclerViewAdapter(Context context, DrawerLayout drawerLayout, NoteModel noteModel) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.noteModel = noteModel;

        noteModel.addObserver(this);
        noteModel.loadNotes();

        selectedNotes.clear();
        checkedCards.clear();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
        holder.materialCardView.setOnClickListener(view -> {
            NoteFragment noteFragment = new NoteFragment(noteModel, note);
            ((MainActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.mainLayout, noteFragment)
                    .addToBackStack(null)
                    .commit();


        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    @Override
    public void update(Observable observable, Object o) {
        notes = noteModel.getNotes();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView noteContent;
        private final MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            materialCardView = itemView.findViewById(R.id.material_card_view);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);


        }
    }

    public void filter(String text) {
        List filteredNotes = noteModel.search(text);
        notes.clear();
        notes.addAll(filteredNotes);
        notifyDataSetChanged();
    }
}
