package com.miomio;

import java.util.List;

public interface NoteDataSource {
    long createNote(Note note);
    Note getNote(long id);
    void deleteNote(Note id);
    void updateNote(Note note);
    List<Note> getAllNotes();
}
