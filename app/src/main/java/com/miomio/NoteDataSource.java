package com.miomio;

import java.util.List;

/**
 * Interface for the Note repository
 */
public interface NoteDataSource {

    /**
     * Create a new note
     *
     * @param note
     * @return long id
     */
    long createNote(Note note);

    /**
     * Get a note from the repository
     *
     * @param id
     * @return Note
     */
    Note getNote(long id);

    /**
     * Delete a note from the repository
     *
     * @param id
     * @return boolean true if the note was deleted
     * false if the note was not found
     */

    boolean deleteNote(Note id);

    /**
     * Update a note in the repository
     *
     * @param note
     * @return boolean true if the note was updated
     * false if the note was not found
     */
    boolean updateNote(Note note);

    /**
     * Get all notes from the repository
     *
     * @return List<Note>
     */
    List<Note> getAllNotes();

    /**
     * Search for notes in the repository
     *
     * @param query
     * @return List<Note>
     */
    List<Note> search(String query);

    /**
     * Delete all notes from the repository
     */
    void deleteAllNotes();

}
