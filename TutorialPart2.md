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