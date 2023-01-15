package com.miomio;

import java.util.List;

public interface NoteDataSource {
    long createNote(Note note);

    Note getNote(long id);

    boolean deleteNote(Note id);

    boolean updateNote(Note note);

    List<Note> getAllNotes();

    List<Note> search(String query);

}
