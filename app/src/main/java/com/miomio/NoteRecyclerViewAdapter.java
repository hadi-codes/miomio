package com.miomio;


import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> implements Observer {
    private List<Note> notes;


    private NoteController controller;

    private final Context context;

    public NoteRecyclerViewAdapter(Context context, NoteController controller) {
        this.context = context;

        this.controller = controller;

        controller.addObserver(this);
        notes = controller.getAllNotes();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the note
        Note note = notes.get(position);
        // set the title and content
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
        // set the click listener
        holder.materialCardView.setOnClickListener(view -> {
            FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager()
                    .beginTransaction();
            controller.onNoteClicked(transaction, note);


        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    @Override
    public void update(Observable observable, Object o) {
        // update the notes
        notes = controller.getAllNotes();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView noteContent;
        private final MaterialCardView materialCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // set material card view
            materialCardView = itemView.findViewById(R.id.material_card_view);
            // set the title and content
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);


        }
    }

    /**
     * Filter the notes based on the query
     * @param text
     */
    public void filter(String text) {
        List filteredNotes = controller.search(text);
        notes.clear();
        notes.addAll(filteredNotes);
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        controller.removeObserver(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
