FlashCards Tutorial Part 2
==========

* If you haven't finished [Part 1](https://github.com/fnk0/FlashCards-Codelab/blob/master/TutorialPart1.md) yet I strongly recommend so.
* Optionally if you already understand the basics of Android you can start this tutorial from part by downloading Part 1 code from here.

#### Part 1. Creating or models.

* Let's start up by creating or models. This will be a basic Flashcards app with the following models.
  * Categories --> A flashcard will have decks
  * Decks --> A deck will belong to a category and have flashcards
  * Flashcards --> Wil belong to a deck and have no direct children.

* To make things a little easier we will make a basic structure to which all the other models will extend.

###### Basic Structure --> CardItem

```java
// CardItem.java
public abstract class CardItem {

    private String title; // All Items must have a title so we can display on a list
    private long id; // Each card will also have an ID so we can find it inside our SQLite DB
    private int color; // Optionally you might want separate cards by color.

    /**
     * Basic Constructor
     */
    protected CardItem() {
    }

    /**
     * @param title
     *       The title for this Item
     */
    protected CardItem(String title) {
        this.title = title;
    }

    // STANDARD GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
```

###### Categories Structure extends CardItem

```java
// Category.java
public class Category extends CardItem {

    private ArrayList<Deck> decks; // The list of decks that belong to this category

    /**
     * Basic Constructor
     */
    public Category() {
    }

    /**
     *
     * @param categoryName
     *          The Name for this category
     * @param decks
     *          The list of decks for this category
     */
    public Category(String categoryName, ArrayList<Deck> decks) {
        super(categoryName);
        this.decks = decks;
    }

    // STANDARD GETTER AND SETTERS

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }
}

```

###### Deck and Flashcards code..

To keep things a bit short on this file...
* get the code for Deck [here](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cardStructures/Deck.java)
* get the code for FlashCard [here](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cardStructures/FlashCard.java)


#### Part 2. Database!

The first level of persistence data is to create a SQLite3 Database that will store our app information.
The first step to get a functional Database in Android is making ourselves a DatabaseHelper. In the Android framework a Database helper is a class that interacts with a specific database.
The easiest way of handling all database interactions is by extending SQLiteOpenHelper.

###### Creating our Helper:

``` java
// MyDbHelper.java
public class MyDbHelper extends SQLiteOpenHelper {

    // Database versions are used to internally tell the Framework which version of your DB to use
    // When the database constraints are changed the database them gets updated.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "flashcards.db"; // the .db is optional.

    public SomeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method will be called whenever the Database needs to be created.
        // Will only Run if the Database does not exist
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called whenever we need to update our Database
        // Examples of updating a DB is adding new tables or new columns to a existing table
    }
}
```

###### Adding Our database constants to our Helper:

We gonna create a few constants to avoid typos as we gonna have to re-use it to create our tables.

```java
// Inside MyDbHelper.java

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

```

###### Creating our Database:

Now that we have our constants ready we will update our onCreate method.
I will not bother too much explaining the SQL statement itself. If you want to learn more about SQLite refer to its [documentantion](http://www.sqlite.org/docs.html)

```java
// Inside MyDbHelper.onCreate()

  final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORIES_TABLE + " (" 
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

```  



