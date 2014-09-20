package gabilheri.com.flashcards;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Map;
import java.util.Set;

import gabilheri.com.flashcards.database.MyDbHelper;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/20/14.
 */
public class TestDB extends AndroidTestCase {

    private static final String LOG_TAG = TestDB.class.getSimpleName();

    private static final String CATEGORY_TITLE = "Geography";
    private static final String DECK_TITLE = "Chapter 1";

    /**
     * FlashCard 1
     */
    private static final String FLASHCARD_1_TITLE = "globalization";
    private static final String FLASHCARD_1_CONTENT = "core countries";
    private static final String FLASHCARD_1_ANSWER = "Ex: USA, France, Germany, Japan";

    /**
     * FlashCard 2
     */
    private static final String FLASHCARD_2_TITLE = "globalization";
    private static final String FLASHCARD_2_CONTENT = "semi-periphery countries";
    private static final String FLASHCARD_2_ANSWER = "Ex: Brazil, China, South-Korea";


    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(MyDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MyDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }


    public void testInsertReadCategory() {

        MyDbHelper dbHelper = new MyDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testCategoryValues = new ContentValues();
        testCategoryValues.put(MyDbHelper.TITLE, CATEGORY_TITLE);

        long locationRowId;
        locationRowId = db.insert(MyDbHelper.CATEGORIES_TABLE, null, testCategoryValues);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                MyDbHelper.CATEGORIES_TABLE,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(testCategoryValues, cursor);

        dbHelper.close();
    }

    public void testInsertReadDeck() {

        MyDbHelper dbHelper = new MyDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testDeckValues = new ContentValues();
        testDeckValues.put(MyDbHelper.TITLE, DECK_TITLE);
        testDeckValues.put(MyDbHelper.BELONGS_TO, 1);

        long locationRowId;
        locationRowId = db.insert(MyDbHelper.DECKS_TABLE, null, testDeckValues);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                MyDbHelper.DECKS_TABLE,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(testDeckValues, cursor);

        dbHelper.close();

    }

    public void testInserReadFlashCards() {
        MyDbHelper dbHelper = new MyDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testFlashcard1Values = new ContentValues();
        testFlashcard1Values.put(MyDbHelper.TITLE, FLASHCARD_1_TITLE);
        testFlashcard1Values.put(MyDbHelper.FLASHCARD_CONTENT, FLASHCARD_1_CONTENT);
        testFlashcard1Values.put(MyDbHelper.FLASHCARD_ANSWER, FLASHCARD_1_ANSWER);
        testFlashcard1Values.put(MyDbHelper.BELONGS_TO, 1);

        long locationRowId;
        locationRowId = db.insert(MyDbHelper.FLASHCARDS_TABLE, null, testFlashcard1Values);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(
                MyDbHelper.FLASHCARDS_TABLE,  // Table to Query
                null, // all columns
                "id=?", // Columns for the "where" clause
                new String[] {"1"}, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(testFlashcard1Values, cursor);

        ContentValues testFlashcard2Values = new ContentValues();
        testFlashcard2Values.put(MyDbHelper.TITLE, FLASHCARD_2_TITLE);
        testFlashcard2Values.put(MyDbHelper.FLASHCARD_CONTENT, FLASHCARD_2_CONTENT);
        testFlashcard2Values.put(MyDbHelper.FLASHCARD_ANSWER, FLASHCARD_2_ANSWER);
        testFlashcard2Values.put(MyDbHelper.BELONGS_TO, 1);

        locationRowId = db.insert(MyDbHelper.FLASHCARDS_TABLE, null, testFlashcard2Values);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d(LOG_TAG, "New row id: " + locationRowId);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        cursor = db.query(
                MyDbHelper.FLASHCARDS_TABLE,  // Table to Query
                null, // all columns
                "id=?", // Columns for the "where" clause
                new String[] {"2"}, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        validateCursor(testFlashcard2Values, cursor);

        dbHelper.close();
    }

    static public void validateCursor(ContentValues expectedValues, Cursor valueCursor) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();

    }

}
