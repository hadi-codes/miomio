package com.miomio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

public class NoteModelTest {


    private NoteRepository mockNoteRepository;

    private NoteModel noteModel;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        mockNoteRepository = mock(NoteRepository.class);
        when(mockNoteRepository.getAllNotes()).thenReturn(new ArrayList<>());
        noteModel = new NoteModel(null, mockNoteRepository);
    }

    @org.junit.jupiter.api.Test
    public void testAddNote() {
        Note note = new Note();
        when(mockNoteRepository.createNote(note)).thenReturn(1L);
        noteModel.addNote(note);
        verify(mockNoteRepository).createNote(note);
    }

    @org.junit.jupiter.api.Test
    public void testDeleteNote() {
        Note note = new Note();
        note.setId(1L);
        noteModel.deleteNote(note);
        verify(mockNoteRepository).deleteNote(note);
    }

    @org.junit.jupiter.api.Test
    public void testUpdateNote() {
        Note note = new Note();
        noteModel.updateNote(note);
        verify(mockNoteRepository).updateNote(note);
    }

    @org.junit.jupiter.api.Test
    public void testGetNotes() {
        List<Note> expectedNotes = new ArrayList<>();
        when(mockNoteRepository.getAllNotes()).thenReturn(expectedNotes);
        List<Note> actualNotes = noteModel.getNotes();
        assertEquals(expectedNotes, actualNotes);
    }

    @org.junit.jupiter.api.Test
    public void testSearch() {
        String query = "test";
        List<Note> expectedNotes = new ArrayList<>();
        when(mockNoteRepository.search(query)).thenReturn(expectedNotes);
        List<Note> actualNotes = noteModel.search(query);
        assertEquals(expectedNotes, actualNotes);
        verify(mockNoteRepository).search(query);
    }
}
