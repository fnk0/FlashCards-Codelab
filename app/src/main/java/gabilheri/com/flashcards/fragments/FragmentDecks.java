package gabilheri.com.flashcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gabilheri.com.flashcards.MainActivity;
import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cardStructures.Deck;
import gabilheri.com.flashcards.cards.DeckCard;
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
public class FragmentDecks extends DefaultFragment implements View.OnClickListener{

    /**
     * Declare the Instance variables that will be used by this fragment
     */
    private List<Card> mCardsList;
    private CardListView mDecksList;
    private CardArrayAdapter mCardAdapter;
    private FloatingActionButton buttonFab;
    private String title;
    private long id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_decks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the FloatingActionButton and set it's colors
        buttonFab = (FloatingActionButton) view.findViewById(R.id.addNewDeck);
        buttonFab.setColor(getResources().getColor(R.color.action_bar_color));
        buttonFab.setTextColor(getResources().getColor(R.color.action_bar_text_color));
        buttonFab.setOnClickListener(this);

        // We initialize the CardsList and add some Dummy Data for now.
        // We will come back to this point to add our Custom Card matching our App UI as well with real data from a Database.
        mDecksList = (CardListView) view.findViewById(R.id.deckList);
        mCardsList = new ArrayList<Card>();

        Bundle b = getArguments();
        title = b.getString(MyDbHelper.TITLE);
        id = b.getLong(MyDbHelper._ID);
        getActivity().setTitle(title);
        MyDbHelper dbHelper = new MyDbHelper(getActivity());
        List<Deck> decks = dbHelper.getAllDecksForCategoryId(id);
        
        for (int i = 0; i < decks.size(); i++) {
            DeckCard card = new DeckCard(getActivity());
            card.setId(String.valueOf(decks.get(i).getId()));
            card.setDeck(decks.get(i));
            mCardsList.add(card);
        }

        mCardAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mCardAdapter.setEnableUndo(true);
        mDecksList.setAdapter(mCardAdapter);
    }

    @Override
    public void onClick(View v) {
        Bundle b = new Bundle();
        b.putLong(MyDbHelper._ID, id);
        b.putString(MyDbHelper.TITLE, title);
        ((MainActivity) getActivity()).displayView(MainActivity.NEW_DECK_FRAG, b);
    }
}
