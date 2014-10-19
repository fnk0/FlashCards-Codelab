package gabilheri.com.flashcards.cardStructures;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/17/14.
 */
public class FlashCard extends CardItem {

    private Category category;
    private Deck deck;
    private String content, answer;

    /**
     * Basic Constructor
     */
    public FlashCard() {
    }

    /**
     *
     * @param category
     *      The category to which this flashcard belongs to
     * @param deck
     *      The deck to which this flashcard belongs to
     * @param title
     *      The title for this FlashCard
     * @param content
     *      The content of this flashcard
     * @param answer
     *      The answer for this flashcard
     */
    public FlashCard(Category category, Deck deck, String title, String content, String answer) {
        super(title);
        this.category = category;
        this.deck = deck;
        this.content = content;
        this.answer = answer;
    }

    // Standard Getters and Setters

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
