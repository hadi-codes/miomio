package com.miomio;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * NoteTextWatcher implements TextWatcher
 * and is used to update the title and content of a note
 */
public class NoteTextWatcher implements TextWatcher {
    private ValueChanged<String> onTextChanged;

    /**
     * Constructor for NoteTextWatcher
     *
     * @param onTextChanged ValueChanged<String>
     */
    NoteTextWatcher(ValueChanged<String> onTextChanged) {
        this.onTextChanged = onTextChanged;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onTextChanged.onChanged(charSequence.toString());

    }


    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * ValueChanged is an interface that is used to update the title and content of a note
     *
     * @param <T>
     */
    interface ValueChanged<T> {

        /**
         * onChanged is used to update the title and content of a note
         *
         * @param value
         */
        void onChanged(T value);
    }
}
