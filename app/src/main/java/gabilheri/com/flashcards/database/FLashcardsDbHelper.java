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
    public static final String DECKS_FLASHCARDS = "flashcards";

    /**
     * FLASHCARDS DATABASE TABLE
     */
    public static final String FLASHCARDS_TABLE = "flashcards";
    public static final String FLASHCARD_CONTENT = "content";


    public FLashcardsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_CATEGORY_TABLE = "";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
