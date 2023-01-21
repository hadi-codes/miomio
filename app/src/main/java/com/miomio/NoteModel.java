package com.miomio;


import android.content.Context;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Note model class that represents the note repository
 * and implements the observer pattern
 */
public class NoteModel extends Observable {

    private final NoteRepository noteRepository;
    private List<Note> notes;

    /**
     * Default constructor
     *
     * @param context
     */
    public NoteModel(Context context) {

        noteRepository = new NoteRepository(context);
        loadNotes();
    }

    /**
     * Constructor for testing purposes
     *
     * @param context
     * @param noteRepository
     */
    public NoteModel(Context context, NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        loadNotes();

    }

    /**
     * Creates a new empty note
     *
     * @return Note
     */
    public Note newNote() {

        return addNote(new Note());
    }

    /**
     * Creates a new empty note
     *
     * @return Note
     */
    public Note addNote(Note note) {
        long id = noteRepository.createNote(note);
        Note noteWithId = note;
        noteWithId.setId(id);
        notes.add(noteWithId);
        setChanged();
        notifyObservers();
        return noteWithId;
    }

    /**
     * Deletes a note
     *
     * @param note
     */
    public void deleteNote(Note note) {
        notes.remove(note);
        setChanged();
        notifyObservers();
    }

    /**
     * Updates a note
     *
     * @param note
     */
    public void updateNote(Note note) {
        noteRepository.updateNote(note);
        setChanged();
        notifyObservers();
    }

    /**
     * Gets all loaded notes
     *
     * @return List<Note>
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Loads all notes from the repository
     * and notifies the observers
     */
    private void loadNotes() {
        notes = noteRepository.getAllNotes();
        setChanged();
        notifyObservers();
    }

    /**
     * Finds a note by id
     *
     * @param id
     * @return Note
     */
    public Note getNoteById(long id) {
        // Refactor this to use a map
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    /**
     * Searches for a note by title or content
     *
     * @param query
     * @return List<Note>
     */
    public List<Note> search(String query) {
        return noteRepository.search(query);
    }

    /**
     * Adds an observer to the model
     *
     * @param observer
     */
    public void addNoteObserver(Observer observer) {
        addObserver(observer);
    }

    /**
     * Removes an observer from the model
     *
     * @param observer
     */
    public void removeNoteObserver(Observer observer) {
        deleteObserver(observer);
    }
}
