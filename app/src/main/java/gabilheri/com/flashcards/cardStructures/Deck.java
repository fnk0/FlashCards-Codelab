package gabilheri.com.flashcards.cardStructures;

import java.util.ArrayList;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/17/14.
 */
public class Deck {

    private Category category;
    private String deckName;
    private ArrayList<FlashCard> flashCards;

    public Deck() {
    }

    public Deck(Category category, String deckName, ArrayList<FlashCard> flashCards) {
        this.category = category;
        this.deckName = deckName;
        this.flashCards = flashCards;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public ArrayList<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void setFlashCards(ArrayList<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }
}
