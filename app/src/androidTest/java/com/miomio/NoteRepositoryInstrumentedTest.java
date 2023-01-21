package com.miomio;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class NoteRepositoryInstrumentedTest {
    private NoteRepository noteRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();

        noteRepository = new NoteRepository(context);


    }

    @After
    public void closeDb() throws IOException {
        noteRepository.close();
    }


    @Test
    public void testCreateNote() {
        Note note = new Note();
        note.setTitle("test1");
        note.setContent("test1");


        long id = noteRepository.createNote(note);
        System.out.println(id);
        assertNotEquals(-1, id);
    }

    @Test
    public void testGetNote() {
        Note note = new Note();
        note.setTitle("test1");
        note.setContent("test1");
        long id = noteRepository.createNote(note);
        System.out.println(id);

        Note actualNote = noteRepository.getNote(id);
        assertEquals(note.getTitle(), actualNote.getTitle());
        assertEquals(note.getContent(), actualNote.getContent());
    }

    @Test
    public void testDeleteNote() {
        Note note = new Note();
        note.setTitle("test1");
        note.setContent("test1");
        long id = noteRepository.createNote(note);
        note.setId(id);
        boolean success = noteRepository.deleteNote(note);
        assertTrue(success);
        assertNull(noteRepository.getNote(id));
    }

    @Test
    public void testUpdateNote() {
        Note note = new Note();
        note.setTitle("test1");
        note.setContent("test1");
        long id = noteRepository.createNote(note);
        note.setId(id);
        note.setTitle("title2");
        note.setContent("content2");

        boolean success = noteRepository.updateNote(note);
        assertTrue(success);
        Note actualNote = noteRepository.getNote(id);
        assertEquals(note.getTitle(), actualNote.getTitle());
        assertEquals(note.getContent(), actualNote.getContent());

    }


}
