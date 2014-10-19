package gabilheri.com.flashcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cards.FlashCardViewerCard;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/26/14.
 */
public class FragmentFlashcard extends DefaultFragment {

    private FlashCardViewerCard card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flashcard_content_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.flashcardViewerFrag);
        CardView cardView = new CardView(getActivity());
        cardView.setCard(card);
        layout.addView(cardView);
    }

    public FlashCardViewerCard getCard() {
        return card;
    }

    public void setCard(FlashCardViewerCard card) {
        this.card = card;
    }
}
