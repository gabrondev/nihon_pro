package com.example.nihon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    // creating constant variables for the database.
    private static final String DB_NAME = "nihondb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "flashcards";
    private static final String ID_COL = "id";
    private static final String WORD_JAPANESE_COL = "wordJapanese";
    private static final String WORD_ROMAJI_COL = "wordRomaji";
    private static final String WORD_TRANSLATED_COL = "wordTranslated";
    private static final String DECK_NAME_COL = "deckName";

    // creating a constructor for the database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // method for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DECK_NAME_COL + " TEXT,"
                + WORD_JAPANESE_COL + " TEXT,"
                + WORD_ROMAJI_COL + " TEXT,"
                + WORD_TRANSLATED_COL + " TEXT)";

        // exec sql method to execute above sql query
        db.execSQL(query);
    }

    // method to add a new word to the sqlite database.
    public void addCard(String deckName, String wordJapanese, String wordRomaji, String wordTranslated) {

        // creating a variable for the sqlite database and calling writable method
        // to write data into the database.
        SQLiteDatabase db = this.getWritableDatabase();

        // creating variable for content values.
        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair.
        values.put(DECK_NAME_COL, deckName);
        values.put(WORD_JAPANESE_COL, wordJapanese);
        values.put(WORD_ROMAJI_COL, wordRomaji);
        values.put(WORD_TRANSLATED_COL, wordTranslated);

        // after adding all values, pass the content values to the table.
        db.insert(TABLE_NAME, null, values);

        // closing database
        db.close();
    }

    // method for reading all the words.
    public ArrayList<Flashcard> readCards()
    {
        // creating a database object for reading the database.
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from  the database.
        Cursor cursorCards
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // creating a new array list.
        ArrayList<Flashcard> cardsArrayList
                = new ArrayList<>();

        // moving the cursor to first position.
        if (cursorCards.moveToFirst()) {
            do {
                // adding the data from cursor to our array list.
                cardsArrayList.add(new Flashcard(
                        cursorCards.getString(1),
                        cursorCards.getString(2),
                        cursorCards.getString(3),
                        cursorCards.getString(4)));
            } while (cursorCards.moveToNext());
            // moving cursor to next.
        }
        // closing cursor and returning the array list.
        cursorCards.close();
        return cardsArrayList;
    }

    public ArrayList<Flashcard> readDecks()
    {
        // creating a database object for reading the database.
        SQLiteDatabase db = this.getReadableDatabase();

        // creating a cursor with query to read data from  the database.
        Cursor cursorDecks
                = db.rawQuery("SELECT DISTINCT deckName FROM " + TABLE_NAME, null);

        // creating a new array list.
        ArrayList<Flashcard> decksArrayList
                = new ArrayList<>();

        // moving the cursor to first position.
        if (cursorDecks.moveToFirst()) {
            do {
                // adding the data from cursor to our array list.
                decksArrayList.add(new Flashcard(
                        cursorDecks.getString(0)));
            } while (cursorDecks.moveToNext());
            // moving cursor to next.
        }
        // closing cursor and returning the array list.
        cursorDecks.close();
        return decksArrayList;
    }

    // method for updating the cards
    public void updateCard(String originalWordJapanse, String deckName,
                           String wordJapanese, String wordRomaji, String wordTranslated) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // passing all values along with its key and value pair.
        values.put(WORD_JAPANESE_COL, wordJapanese);
        values.put(DECK_NAME_COL, deckName);
        values.put(WORD_ROMAJI_COL, wordRomaji);
        values.put(WORD_TRANSLATED_COL, wordTranslated);

        // calling a update method to update the database and passing the values.
        // also comparing the new value with the original variable.
        db.update(TABLE_NAME, values, "wordJapanese=?", new String[]{originalWordJapanse});
        db.close();
    }

    // below is the method for deleting our course.
// below is the method for deleting our course.
    public void deleteCard(String wordJapanese) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, "wordJapanese=?", new String[]{wordJapanese});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
