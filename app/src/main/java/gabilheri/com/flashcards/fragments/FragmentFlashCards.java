package gabilheri.com.flashcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cardStructures.FlashCard;
import gabilheri.com.flashcards.cards.ListFlashCardCard;
import gabilheri.com.flashcards.database.MyDbHelper;
import gabilheri.com.flashcards.fab.FloatingActionButton;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/22/14.
 */
public class FragmentFlashCards extends DefaultFragment implements View.OnClickListener {

    /**
     * Declare the Instance variables that will be used by this fragment
     */
    private List<Card> mCardsList;
    private CardListView mFlashCardsList;
    private CardArrayAdapter mCardAdapter;
    private FloatingActionButton buttonFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_flashcards, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the FloatingActionButton and set it's colors
        buttonFab = (FloatingActionButton) view.findViewById(R.id.addNewFlashCard);
        buttonFab.setColor(getResources().getColor(R.color.action_bar_color));
        buttonFab.setTextColor(getResources().getColor(R.color.action_bar_text_color));
        buttonFab.setOnClickListener(this);

        // We initialize the CardsList and add some Dummy Data for now.
        // We will come back to this point to add our Custom Card matching our App UI as well with real data from a Database.
        mFlashCardsList = (CardListView) view.findViewById(R.id.flashcardList);
        mCardsList = new ArrayList<Card>();

        Bundle b = getArguments();
        getActivity().setTitle(b.getString(MyDbHelper.TITLE));
        MyDbHelper dbHelper = new MyDbHelper(getActivity());
        List<FlashCard> flashcards = dbHelper.getAllFlashCardsForDeckId(b.getLong(MyDbHelper._ID));

        for(int i = 0; i < flashcards.size(); i++) {
            ListFlashCardCard card = new ListFlashCardCard(getActivity());
            card.setId(String.valueOf(flashcards.get(i).getId()));
            card.setFlashCard(flashcards.get(i));
            mCardsList.add(card);
        }

        mCardAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mCardAdapter.setEnableUndo(true);
        mFlashCardsList.setAdapter(mCardAdapter);
    }

    @Override
    public void onClick(View v) {

    }

}
