package com.miomio;


import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class NoteModel extends Observable implements Serializable {
    public static final String NOTE_MODEL = "NOTE_MODEL";

    private final NoteRepository noteRepository;
    private List<Note> notes;

    public NoteModel(Context context) {
        noteRepository = new NoteRepository(context);
        notes = noteRepository.getAllNotes();
    }

    public NoteModel(Context context, NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        notes = noteRepository.getAllNotes();
    }

    public Note newNote() {

        return addNote(new Note());
    }

    public Note addNote(Note note) {
        long id = noteRepository.createNote(note);
        Note noteWithId = note;
        noteWithId.setId(id);
        notes.add(noteWithId);
        setChanged();
        notifyObservers();
        return noteWithId;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void loadNotes() {
        notes = noteRepository.getAllNotes();
        setChanged();
        notifyObservers();
    }

    public Note getNoteById(long id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    public List<Note> search(String query) {
        return noteRepository.search(query);
    }

    public void addNoteObserver(Observer observer) {
        addObserver(observer);
    }

    public void removeNoteObserver(Observer observer) {
        deleteObserver(observer);
    }
}
