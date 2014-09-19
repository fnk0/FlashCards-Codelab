package gabilheri.com.flashcards.cardStructures;

import java.util.ArrayList;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/17/14.
 */
public class Deck extends CardItem {

    private Category category;
    private ArrayList<FlashCard> flashCards;

    public Deck() {
    }

    public Deck(Category category, String deckName, ArrayList<FlashCard> flashCards) {
        super(deckName);
        this.category = category;
        this.flashCards = flashCards;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void setFlashCards(ArrayList<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }
}
