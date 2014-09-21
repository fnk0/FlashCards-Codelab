package gabilheri.com.flashcards.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gabilheri.com.flashcards.R;
import gabilheri.com.flashcards.cardStructures.Category;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/8/14.
 */
public class CategoryCard extends Card implements Card.OnSwipeListener, Card.OnCardClickListener, Card.OnUndoSwipeListListener {

    private Category category;

    public CategoryCard(Context context) {
        super(context, R.layout.card_category);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView categoryTitle = (TextView) view.findViewById(R.id.titleCategory);

        if(category != null) {
            categoryTitle.setText(category.getTitle());
        }
    }

    @Override
    public void onClick(Card card, View view) {

    }

    @Override
    public void onSwipe(Card card) {

    }

    @Override
    public void onUndoSwipe(Card card) {

    }
}
