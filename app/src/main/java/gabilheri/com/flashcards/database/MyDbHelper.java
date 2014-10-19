package gabilheri.com.flashcards.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gabilheri.com.flashcards.cardStructures.Category;
import gabilheri.com.flashcards.cardStructures.Deck;
import gabilheri.com.flashcards.cardStructures.FlashCard;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/19/14.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "FlashcardsDbHelper";

    // Database versions are used to internally tell the Framework which version of your DB to use
    // When the database constraints are changed the database them gets updated.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "flashcards.db"; // the .db is optional.


    /**
     * COMMON DATABASE CONSTANTS
     */
    public static final String _ID = "id"; // we need to use _ID because ID is already used by the system.
    public static final String TITLE = "title";
    public static final String BELONGS_TO = "belongs_to";

    /**
     * CATEGORIES DATABASE CONSTANTS
     */
    public static final String CATEGORIES_TABLE = "categories";

    /**
     * DECKS DATABASE CONSTANTS
     */
    public static final String DECKS_TABLE = "decks";

    /**
     * FLASHCARDS DATABASE TABLE
     */
    public static final String FLASHCARDS_TABLE = "flashcards";
    public static final String FLASHCARD_CONTENT = "content";
    public static final String FLASHCARD_ANSWER = "answer";


    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORIES_TABLE + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL " +
                ");";
        final String SQL_CREATE_DECKS_TABLE = "CREATE TABLE " + DECKS_TABLE + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " +
                BELONGS_TO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + CATEGORIES_TABLE + " (" + _ID + ")" +
                ");";

        final String SQL_CREATE_FLASHCARDS_TABLE = "CREATE TABLE " + FLASHCARDS_TABLE + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT NOT NULL, " +
                FLASHCARD_CONTENT + " TEXT NOT NULL, " +
                FLASHCARD_ANSWER + " TEXT NOT NULL, " +
                BELONGS_TO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + DECKS_TABLE + " (" + _ID + ")" +
                ");";

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

    /**
     * This method is used to insert a new Category entry in the database
     *
     * @param category
     *      The category to be inserted
     * @return
     *      The inserted statement for the Database - Used internally by Android
     */
    public long createCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase(); // We get an WritableDatabase so we can insert data
        ContentValues values = new ContentValues(); // Create a new content values with Key Value Pairs
        values.put(TITLE, category.getTitle()); // Insert the TITLE for the Category

        return db.insert(CATEGORIES_TABLE, null, values);
    }

    /**
     * This method is used to insert a new Deck entry in the database
     *
     * @param deck
     *      The deck to be inserted
     * @param categoryId
     *      The category to which this Deck belongs to
     * @return
     *      The inserted statement for the Database - Used internally by Android
     */
    public long createDeck(Deck deck, long categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, deck.getTitle());
        values.put(BELONGS_TO, categoryId); // We get the ID of the categories so we can reference through a foreign key
        return db.insert(DECKS_TABLE, null, values);
    }

    /**
     * This method is used to insert a new Flashcard entry in the database
     *
     * @param flashCard
     *      The flashcard to be inserted
     * @param deckId
     *      The Deck to which this flashcard belongs to
     * @return
     *      The inserted statement for the Database - Used internally by Android
     */
    public long createFlashCard(FlashCard flashCard, long deckId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, "");
        values.put(FLASHCARD_CONTENT, flashCard.getContent());
        values.put(FLASHCARD_ANSWER, flashCard.getAnswer());
        values.put(BELONGS_TO, deckId);

        return db.insert(FLASHCARDS_TABLE, null, values);
    }

    public long undoCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, category.getId());
        values.put(TITLE, category.getTitle());

        return db.insert(CATEGORIES_TABLE, null, values);
    }

    public long undoDeck(Deck deck) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, deck.getId());
        values.put(TITLE, deck.getTitle());
        values.put(BELONGS_TO, deck.getCategory().getId());
        return db.insert(DECKS_TABLE, null, values);
    }

    public long undoFlashcard(FlashCard flashCard) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, flashCard.getId());
        values.put(TITLE, flashCard.getTitle());
        values.put(FLASHCARD_CONTENT, flashCard.getContent());
        values.put(FLASHCARD_ANSWER, flashCard.getAnswer());
        values.put(BELONGS_TO, flashCard.getDeck().getId());

        return db.insert(FLASHCARDS_TABLE, null, values);
    }

    /**
     * Method to get a category based on it's title
     *
     * @param title
     *       The title of the Category
     * @return
     *      A Category object
     */
    public Category getCategoryByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase(); // We get a readable database as we only want to access it
        String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE + " WHERE " + TITLE + " = '" + title + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.i(LOG_TAG, "Cursor is null!!");
            return null;
        }

        Category category = new Category();
        category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        this.closeDB();
        cursor.close();
        return category;
    }

    /**
     * Method to get a category based on it's title
     *
     * @param id
     *       The id of the Category
     * @return
     *      A Category object
     */
    public Category getCategoryByID(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // We get a readable database as we only want to access it
        String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE + " WHERE " + _ID + " = " + id + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.i(LOG_TAG, "Cursor is null!!");
            return null;
        }

        Category category = new Category();
        category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        this.closeDB();
        cursor.close();
        return category;
    }

    /**
     * Method to get a Deck based on it's title
     *
     * @param title
     *        The title for the Deck
     * @return
     *        A Deck Object
     */
    public Deck getDeckByName(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DECKS_TABLE + " WHERE " + TITLE + " = '" + title + "';";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.i(LOG_TAG, "Cursor is null!!");
            return null;
        }

        Deck deck = new Deck();
        deck.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        deck.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));

        Category category = getCategoryByID(cursor.getLong(cursor.getColumnIndex(BELONGS_TO)));

        deck.setCategory(category);

        this.closeDB();
        cursor.close();
        return deck;
    }

    /**
     * Method to get a Deck based on it's title
     *
     * @param id
     *        The id for the Deck
     * @return
     *        A Deck Object
     */
    public Deck getDeckByID(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DECKS_TABLE + " WHERE " + _ID + " = " + id + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.i(LOG_TAG, "Cursor is null!!");
            return null;
        }

        Deck deck = new Deck();
        deck.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        deck.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));

        Category category = getCategoryByID(cursor.getLong(cursor.getColumnIndex(BELONGS_TO)));

        if(category != null) deck.setCategory(category);

        this.closeDB();
        cursor.close();
        return deck;
    }

    /**
     *
     * @param id
     *      The id for the FlashCard
     * @return
     *      A FlashCard object
     */
    public FlashCard getFlashCardByID(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + FLASHCARDS_TABLE + " WHERE " + _ID + " = " + id + ";";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.i(LOG_TAG, "Cursor is null!!");
            return null;
        }

        FlashCard flashCard = new FlashCard();
        flashCard.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        flashCard.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
        flashCard.setContent(cursor.getString(cursor.getColumnIndex(FLASHCARD_CONTENT)));
        flashCard.setContent(cursor.getString(cursor.getColumnIndex(FLASHCARD_ANSWER)));

        Deck deck = getDeckByID(cursor.getLong(cursor.getColumnIndex(BELONGS_TO)));

        if(deck != null) flashCard.setDeck(deck);

        this.closeDB();
        cursor.close();
        return flashCard;
    }

    /**
     *
     * @param id
     *      The desired deck to which we want all the FlashCards
     * @return
     *      A list with all the FlashCards
     */
    public List<FlashCard> getAllFlashCardsForDeckId(Long id) {

        List<FlashCard> flashCards = new ArrayList<FlashCard>();
        String selectQuery = "SELECT * FROM " + FLASHCARDS_TABLE + " WHERE " + BELONGS_TO + " = " + id + ";";

        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                FlashCard flashCard = new FlashCard();
                flashCard.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
                flashCard.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                flashCard.setContent(cursor.getString(cursor.getColumnIndex(FLASHCARD_CONTENT)));
                flashCard.setAnswer(cursor.getString(cursor.getColumnIndex(FLASHCARD_ANSWER)));
                flashCard.setDeck(getDeckByID(id));
                flashCards.add(flashCard);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.closeDB();
        return flashCards;
    }

    /**
     *
     * @return
     *      A list with all the categories
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToNext()) {
            do {
                Category category = new Category();
                category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
                category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                categories.add(category);
            } while (cursor.moveToNext());
        }

        return categories;
    }

    /**
     *
     * @param id
     *      The category to which we want all Decks
     * @return
     *      A list with all Decks for the specific category
     */
    public List<Deck> getAllDecksForCategoryId(long id) {
        List<Deck> decks = new ArrayList<Deck>();
        String selectQuery = "SELECT * FROM " + DECKS_TABLE + " WHERE " + BELONGS_TO + " = " + id + ";";

        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Deck deck = new Deck();
                deck.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
                deck.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                deck.setCategory(getCategoryByID(id));
                decks.add(deck);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.closeDB();
        return decks;
    }

    /**
     * General method to delete an entry from the Database based on it's ID
     * @param id
     *      The ID Of the item to be deleted
     * @param table
     *      The table to which the item should be deleted.
     */
    public void deleteFromDB(long id, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, _ID + " = ?", new String[] {String.valueOf(id)});
        this.closeDB();
    }

    /**
     *
     * @param table
     *      The Table to delete all entries
     */
    public void deleteAllEntriesForTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
        this.closeDB();
    }

    /**
     * Closes the Database Connection.
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
