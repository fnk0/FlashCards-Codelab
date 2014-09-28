package gabilheri.com.flashcards.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cardStructures.FlashCard;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/24/14.
 */
public class FlashCardViewerCard extends Card implements Card.OnCardClickListener {

    private FlashCard card;
    private TextView flashCardTitle, flashCardContentAnswer;


    public FlashCardViewerCard(Context context) {
        super(context, R.layout.flashcard_content);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        flashCardTitle = (TextView) view.findViewById(R.id.titleFlashcard);
        flashCardContentAnswer = (TextView) view.findViewById(R.id.flashcardContentAnswer);

        if(card != null) {
            flashCardTitle.setText(card.getTitle());
            flashCardContentAnswer.setText(card.getContent());
        }
    }


    public FlashCard getCard() {
        return card;
    }

    public void setCard(FlashCard card) {
        this.card = card;
    }

    @Override
    public void onClick(Card card, View view) {

    }
}
