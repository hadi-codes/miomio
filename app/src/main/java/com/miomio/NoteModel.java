package com.miomio;


import android.content.Context;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class NoteModel extends Observable {

    private final NoteRepository noteRepository;
    private ArrayList<Note> notes;

    public NoteModel(Context context) {
        noteRepository = new NoteRepository(context);
        notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        noteRepository.createNote(note);
        notes.add(note);
        setChanged();
        notifyObservers();
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
        notes.remove(note);
        setChanged();
        notifyObservers();
    }

    public void updateNote(Note note) {
        noteRepository.updateNote(note);
        setChanged();
        notifyObservers();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void loadNotes() {
        notes = noteRepository.getAllNotes();
        setChanged();
        notifyObservers();
    }

    public void addNoteObserver(Observer observer) {
        addObserver(observer);
    }

    public void removeNoteObserver(Observer observer) {
        deleteObserver(observer);
    }
}
