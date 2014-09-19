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

    private ArrayList<Deck> decks;

    public Category() {
    }

    public Category(String categoryName, ArrayList<Deck> decks) {
        super(categoryName);
        this.decks = decks;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }
}
