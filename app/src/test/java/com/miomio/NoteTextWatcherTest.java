package com.miomio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class NoteTextWatcherTest {
    NoteTextWatcher.ValueChanged<String> mockValueChanged=mock(NoteTextWatcher.ValueChanged.class);
    NoteTextWatcher noteTextWatcher = new NoteTextWatcher(mockValueChanged);

    @Test
    void onTextChanged() {
        noteTextWatcher.onTextChanged("test",0,0,0);
        verify(mockValueChanged).onChanged("test");
    }
}