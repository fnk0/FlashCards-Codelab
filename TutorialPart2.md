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


#### Part 2. Database! The first level of persistence data is to create a SQLite3 Database that will store our app information.

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
