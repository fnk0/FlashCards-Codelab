package gabilheri.com.flashcards.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import gabilheri.com.flashcards.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/24/14.
 */
public class FlashCardViewerCard extends Card implements Card.OnCardClickListener {

    public FlashCardViewerCard(Context context) {
        super(context, R.layout.flashcard_content);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
    }

    @Override
    public void onClick(Card card, View view) {

    }


}
