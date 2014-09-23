package gabilheri.com.flashcards.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gabilheri.com.flashcards.MainActivity;
import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.Utils;
import gabilheri.com.flashcards.cardStructures.Deck;
import gabilheri.com.flashcards.database.MyDbHelper;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/18/14.
 */
public class FragmentNewDeck extends DefaultFragment implements View.OnClickListener{

    private static final String LOG_TAG = FragmentNewDeck.class.getSimpleName();

    private Button btnAddDeck;
    private EditText deckName;
    private Bundle b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_deck, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = getArguments();
        btnAddDeck = (Button) view.findViewById(R.id.addNewDeck);
        btnAddDeck.setOnClickListener(this);

        deckName = (EditText) view.findViewById(R.id.deckTitle);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btnAddDeck.getId()) {

            MyDbHelper dbHelper = new MyDbHelper(getActivity());
            Deck deck = new Deck();

            if(!deckName.getText().toString().isEmpty()) {
                deck.setTitle(deckName.getText().toString());
                try {
                    dbHelper.createDeck(deck, b.getLong(MyDbHelper._ID));
                    Log.i(LOG_TAG, "The id of the category is: " + b.getLong(MyDbHelper._ID));
                    Utils.hideKeyboard(getActivity());
                    ((MainActivity) getActivity()).displayView(MainActivity.DECKS_FRAG, b);
                } catch (Exception ex) {
                    Log.i(LOG_TAG, "Could not create Deck");
                }
            } else {
                Toast.makeText(getActivity(), "Deck MUST have a title.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
