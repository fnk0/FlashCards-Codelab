package gabilheri.com.flashcards.cardStructures;

import android.graphics.Bitmap;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/17/14.
 */
public class FlashCard {

    private Category category;
    private Deck deck;
    private String title, content, answer;
    private Bitmap imageContent, imageAnswer;
    private boolean hasImageContent, hasImageAnswer = false;

    public FlashCard() {
    }

    public FlashCard(String title, Bitmap imageContent, Bitmap imageAnswer) {
        this.title = title;
        this.imageContent = imageContent;
        this.imageAnswer = imageAnswer;
    }

    public FlashCard(Category category, Deck deck, String title, Bitmap imageAnswer, String content) {
        this.category = category;
        this.deck = deck;
        this.title = title;
        this.imageAnswer = imageAnswer;
        this.content = content;
    }

    public FlashCard(Category category, Deck deck, String title, String answer, Bitmap imageContent) {
        this.category = category;
        this.deck = deck;
        this.title = title;
        this.answer = answer;
        this.imageContent = imageContent;
    }

    public FlashCard(Category category, Deck deck, String title, String content, String answer) {
        this.category = category;
        this.deck = deck;
        this.title = title;
        this.content = content;
        this.answer = answer;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Bitmap getImageContent() {
        return imageContent;
    }

    public void setImageContent(Bitmap imageContent) {
        this.imageContent = imageContent;
    }

    public Bitmap getImageAnswer() {
        return imageAnswer;
    }

    public void setImageAnswer(Bitmap imageAnswer) {
        this.imageAnswer = imageAnswer;
    }

    public boolean isHasImageContent() {
        return hasImageContent;
    }

    public void setHasImageContent(boolean hasImageContent) {
        this.hasImageContent = hasImageContent;
    }

    public boolean isHasImageAnswer() {
        return hasImageAnswer;
    }

    public void setHasImageAnswer(boolean hasImageAnswer) {
        this.hasImageAnswer = hasImageAnswer;
    }
}
