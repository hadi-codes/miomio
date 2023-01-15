package com.miomio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class NoteRepository extends SQLiteOpenHelper implements NoteDataSource {


    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_CONTENT = "CONTENT";
    public static final String COLUMN_CREATED_AT = "CREATED_AT";


    private transient Context context;

    public NoteRepository(@Nullable Context context) {
        super(context, "notes.db", null, 1);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NOTES_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_CREATED_AT + " LONG)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public List<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        boolean hasNext = cursor.moveToFirst();
        while (hasNext) {

            Note note = getNoteFromCursor(cursor);
            notes.add(note);
            hasNext = cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return notes;

    }

    @Override
    public List<Note> search(String query) {
        ArrayList<Note> notes = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTES_TABLE + " WHERE " + COLUMN_TITLE + " LIKE '%" + query + "%' OR " + COLUMN_CONTENT + " LIKE '%" + query + "%'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        boolean hasNext = cursor.moveToFirst();
        while (hasNext) {

            Note note = getNoteFromCursor(cursor);
            notes.add(note);
            hasNext = cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return notes;
    }


    /**
     * INSERTS A NEW NOTE INTO THE DATABASE
     *
     * @param note to be inserted
     * @return identifier of the inserted note
     */
    public long createNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(COLUMN_TITLE, note.getTitle());
        content.put(COLUMN_CONTENT, note.getContent());
        content.put(COLUMN_CREATED_AT, note.getCreatdAt());


        final long insert = db.insert(NOTES_TABLE, null, content);
        String queryString = "SELECT " + COLUMN_ID + " FROM " + NOTES_TABLE + " WHERE rowid = " + insert;
        final Cursor cursor = db.rawQuery(queryString, null);

        cursor.moveToFirst();

        final long noteId = cursor.getLong(0);
        db.close();
        cursor.close();

        if (insert == -1) {
            return -1;
        }
        return noteId;
    }

    /**
     * RETURNS A NOTE OBJECT FROM THE DATABASE
     *
     * @param noteId noteIdentifier of desired note
     * @return resulting note object
     */
    public Note getNote(long noteId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + NOTES_TABLE + " WHERE " + COLUMN_ID + " = " + noteId;
//        String queryString = "SELECT * FROM " + NOTES_TABLE + " WHERE rowid = " + noteId;
        final Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
Note note = getNoteFromCursor(cursor);
        cursor.close();
        sqLiteDatabase.close();
        return note;
    }

    /**
     * UPDATES A NOTE'S TITLE AND CONTENT
     *
     * @param note to update
     * @return true if update was successful
     * false if update was unsuccessful
     */
    public boolean updateNote(Note note) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put(COLUMN_TITLE, note.getTitle());
            content.put(COLUMN_CONTENT, note.getContent());
            content.put(COLUMN_CREATED_AT, note.getCreatdAt());
            db.update(NOTES_TABLE, content, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
            db.close();
            return true;
        } catch (Exception e) {
            return false;

        }

    }

    /**
     * DELETES A NOTE FROM THE DATABASE
     *
     * @param note
     * @return true if delete was successful
     * false if delete was unsuccessful
     */
    public boolean deleteNote(Note note) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(NOTES_TABLE, "ID = ?", new String[]{String.valueOf(note.getId())});
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Note getNoteFromCursor(Cursor cursor) {
        long id = cursor.getLong(0);
        String title = cursor.getString(1);
        String content = cursor.getString(2);
        long createdAt = cursor.getLong(3);
        return new Note(id, title, content, createdAt);
    }

}








