package com.miomio;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class NoteModelTest {
    NoteModelTest() {
        setUp();
    }

    Context context = Mockito.mock(Context.class);
    NoteRepository mockNoteRepository = Mockito.mock(NoteRepository.class);

    NoteModel noteModel;

    private void setUp() {
        List notes = new ArrayList<Note>();
        for (int i = 0; i < 10; i++) {
            Note note = new Note(i, "title" + i, "content" + i, i);
            note.setId(i);
            notes.add(note);
        }
        when(mockNoteRepository.getAllNotes()).thenReturn(notes);
        noteModel = new NoteModel(context, mockNoteRepository);
        mock(NoteModel.class);
        when(mockNoteRepository.getAllNotes()).thenReturn(notes);
        when(mockNoteRepository.createNote(Mockito.any(Note.class))).thenAnswer(invocation -> {
            Note note = new Note(11L, "title11", "content11", 11);
            return note.getId();
        });
        when(mockNoteRepository.updateNote(Mockito.any(Note.class))).thenReturn(true);
        when(mockNoteRepository.deleteNote(Mockito.any(Note.class))).thenReturn(true);


    }

    @Test
    void newNote() {
        Note note = noteModel.newNote();
        assertEquals(11, note.getId());
        verify(mockNoteRepository, atLeastOnce()).createNote(Mockito.any(Note.class));
    }

    @Test
    void addNote() {
        Note note = noteModel.newNote();
        assertEquals(11, note.getId());
        verify(mockNoteRepository, atLeastOnce()).createNote(Mockito.any(Note.class));
    }

    @Test
    void deleteNote() {
    }

    @Test
    void updateNote() {
    }

    @Test
    void getNotes() {
    }

    @Test
    void loadNotes() {
    }

    @Test
    void getNoteById() {
    }

    @Test
    void search() {
    }

    @Test
    void addNoteObserver() {
    }

    @Test
    void removeNoteObserver() {
    }
}