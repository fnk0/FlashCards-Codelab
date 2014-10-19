package gabilheri.com.flashcards.cardStructures;

import java.util.ArrayList;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/17/14.
 */
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
