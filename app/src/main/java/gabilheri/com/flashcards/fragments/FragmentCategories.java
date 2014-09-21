package gabilheri.com.flashcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gabilheri.com.flashcards.MainActivity;
import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cardStructures.Category;
import gabilheri.com.flashcards.cards.CategoryCard;
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
 * @since 9/7/14.
 */
public class FragmentCategories extends DefaultFragment implements View.OnClickListener {

    /**
     * Declare the Instance variables that will be used by this fragment
     */
    private List<Card> mCardsList;
    private CardListView mCategoriesList;
    private CardArrayAdapter mCardAdapter;
    private FloatingActionButton buttonFab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true); // We use this so we can have specific ActionBar actions/icons for this fragment
        // We return the inflated view to be used by onViewCreated.
        // The first argument is the XML layout to be inflated
        // The second argument is the root to which this layout is being attached.
        // The third argument we specify if we want the fragment to be attached to it's root.
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the FloatingActionButton and set it's colors
        buttonFab = (FloatingActionButton) view.findViewById(R.id.addNewCategory);
        buttonFab.setColor(getResources().getColor(R.color.action_bar_color));
        buttonFab.setTextColor(getResources().getColor(R.color.action_bar_text_color));
        buttonFab.setOnClickListener(this);

        // We initialize the CardsList and add some Dummy Data for now.
        // We will come back to this point to add our Custom Card matching our App UI as well with real data from a Database.
        mCategoriesList = (CardListView) view.findViewById(R.id.categoriesList);
        mCardsList = new ArrayList<Card>();

        MyDbHelper dbHelper = new MyDbHelper(getActivity());
        List<Category> categories = dbHelper.getAllCategories();

        for (int i = 0; i < categories.size(); i++) {
            CategoryCard card = new CategoryCard(getActivity());
            card.setCategory(categories.get(i));
            mCardsList.add(card);
        }

        mCardAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mCardAdapter.setEnableUndo(true);
        mCategoriesList.setAdapter(mCardAdapter);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == buttonFab.getId()) {
            ((MainActivity) getActivity()).displayView(MainActivity.NEW_CATEGORY_FRAG, null);
        }
    }
}
