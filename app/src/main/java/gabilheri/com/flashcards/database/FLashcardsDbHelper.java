package gabilheri.com.flashcards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/19/14.
 */
public class FLashcardsDbHelper extends SQLiteOpenHelper {

    // Database versions are used to internally tell the Framework which version of your DB to use
    // When the database constraints are changed the database them gets updated.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flashcards.db"; // the .db is optional.


    /**
     * COMMON DATABASE CONSTANTS
     */
    public static final String _ID = "id"; // we need to use _ID because ID is already used by the system.
    public static final String TITLE = "title";

    /**
     * CATEGORIES DATABASE CONSTANTS
     */
    public static final String CATEGORIES_TABLE = "categories";
    public static final String CATEGORY_DECKS = "decks";


    /**
     * DECKS DATABASE CONSTANTS
     */
    public static final String DECKS_TABLE = "decks";
    public static final String DECKS_CATEGORY = "category";

    /**
     * FLASHCARDS DATABASE TABLE
     */
    public static final String FLASHCARDS_TABLE = "flashcards";
    public static final String FLASHCARD_CONTENT = "content";
    public static final String FLASHCARD_ANSWER = "answer";
    public static final String FLASHCARD_BELONGS_TO = "belongs_to";


    public FLashcardsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORIES_TABLE + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + CATEGORY_DECKS + ") REFERENCES " + DECKS_TABLE + " (" + _ID + ")" +
                ";";

        final String SQL_CREATE_DECKS_TABLE = "CREATE TABLE " + DECKS_TABLE + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + DECKS_CATEGORY + ") REFERENCES + " + CATEGORIES_TABLE + " (" + _ID + ")" +
                ";";

        final String SQL_CREATE_FLASHCARDS_TABLE = "CREATE TABLE " + FLASHCARDS_TABLE +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT NOT NULL, " +
                FLASHCARD_CONTENT + " TEXT NOT NULL, " +
                FLASHCARD_ANSWER + " TEXT NOT NULL, " +
                "FOREIGN KEY (" + FLASHCARD_BELONGS_TO + ") REFERENCES " + DECKS_TABLE + " (" + _ID + ")" +
                ";";

        // We now create our tables.
        // If the tables already exist Android will them ignore this statement.
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_DECKS_TABLE);
        db.execSQL(SQL_CREATE_FLASHCARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For the purpose of this tutorial we will have the simplest method of upgrading a Database.
        // When dealing within your own apps use ALTER TABLE instead...
        // The reason of not using Alter Table right now is because it can be very specific for each case

        // This statements will delete the current tables and call onCreate with the new create table queries.
        // We will never be using this in this tutorial but keep in mind that onUpgrade will be called whenever you want
        // to add a field to the Database.
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DECKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FLASHCARDS_TABLE);
        onCreate(db);
    }
}
