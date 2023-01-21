package com.miomio;


import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.Observer;

public class NoteController {
    private Note note;
    private NoteModel noteModel;

    public NoteController(NoteModel noteModel) {
        this.noteModel = noteModel;
    }


    public void onTitleChanged(String title) {
        note.setTitle(title);
        noteModel.updateNote(note);
    }

    public void onContentChanged(String content) {
        note.setContent(content);
        noteModel.updateNote(note);
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public boolean isEditMode() {
        return note != null;
    }

    public void newNote() {
        note = noteModel.newNote();
    }

    public void deleteNote() {
        noteModel.deleteNote(note);
        note = null;
    }

    public void addObserver(Observer observer) {
        noteModel.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        noteModel.deleteObserver(observer);
    }

    public List<Note> getAllNotes() {
        return noteModel.getNotes();
    }

    public List<Note> search(String query) {
        return noteModel.search(query);
    }

    public void onAddNoteClicked(FragmentTransaction fragmentTransaction) {
        setNote(null);
        NoteFragment noteFragment = new NoteFragment(this);
        fragmentTransaction.replace(R.id.mainLayout, noteFragment, NoteFragment.TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void onNoteClicked(FragmentTransaction fragmentTransaction, Note note) {

        setNote(note);

        NoteFragment noteFragment = new NoteFragment(this);

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.mainLayout, noteFragment)
                .addToBackStack(null)
                .commit();


    }
}
