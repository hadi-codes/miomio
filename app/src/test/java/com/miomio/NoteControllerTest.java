package com.miomio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import androidx.fragment.app.FragmentTransaction;

public class NoteControllerTest {

    private NoteModel noteModel;
    private Note note;
    private FragmentTransaction fragmentTransaction;
    private NoteFragment noteFragment;

    private NoteController noteController;

    @Before
    public void setUp() {
        noteModel = mock(NoteModel.class);
        note = mock(Note.class);
        noteFragment = mock(NoteFragment.class);
        fragmentTransaction = mock(FragmentTransaction.class);
        noteController = new NoteController(noteModel);
    }

    @Test
    public void onTitleChanged_shouldUpdateNoteTitle() {
        String title = "New Title";
        noteController.setNote(note);
        noteController.onTitleChanged(title);
        verify(note).setTitle(title);
        verify(noteModel).updateNote(note);
    }

    @Test
    public void onContentChanged_shouldUpdateNoteContent() {
        String content = "New Content";
        noteController.setNote(note);
        noteController.onContentChanged(content);
        verify(note).setContent(content);
        verify(noteModel).updateNote(note);
    }

    @Test
    public void newNote_shouldCreateNewNote() {
        noteController.newNote();
        verify(noteModel).newNote();
    }

    @Test
    public void deleteNote_shouldDeleteNote() {
        noteController.setNote(note);
        noteController.deleteNote();
        verify(noteModel).deleteNote(note);
        assertNull(noteController.getNote());
    }

    @Test
    public void onAddNoteClicked_shouldReplaceFragment() {
        noteController.onAddNoteClicked(fragmentTransaction);
        verify(fragmentTransaction).replace(R.id.mainLayout, noteFragment, NoteFragment.TAG);
        verify(fragmentTransaction).addToBackStack(null);
        verify(fragmentTransaction).commit();
    }

    @Test
    public void onNoteClicked_shouldReplaceFragment() {
        noteController.onNoteClicked(fragmentTransaction, note);
        verify(fragmentTransaction).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        verify(fragmentTransaction).replace(R.id.mainLayout, noteFragment);
        verify(fragmentTransaction).addToBackStack(null);
        verify(fragmentTransaction).commit();
    }
}
