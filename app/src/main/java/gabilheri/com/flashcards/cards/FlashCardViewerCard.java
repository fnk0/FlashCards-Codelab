package gabilheri.com.flashcards.cards;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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

    private static String LOG_TAG = FlashCardViewerCard.class.getCanonicalName();
    private FlashCard card;
    private TextSwitcher flashCardContentAnswer;
    private boolean isAnswer = false;

    public FlashCardViewerCard(Context context) {
        super(context, R.layout.flashcard_content);
        setOnClickListener(this);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        
        flashCardContentAnswer = (TextSwitcher) view.findViewById(R.id.flashcardContentAnswer);
        flashCardContentAnswer.setFactory(mFactory);
        if(card != null) {
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
        if(isAnswer) {
            flashCardContentAnswer.setText(getCard().getContent());
            isAnswer = false;
        } else {
            flashCardContentAnswer.setText(getCard().getAnswer());
            isAnswer = true;
        }
    }

    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            TextView t = new TextView(getContext());
            t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
            return t;
        }
    };
}
