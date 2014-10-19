package gabilheri.com.flashcards.cardStructures;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/19/14.
 */
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
