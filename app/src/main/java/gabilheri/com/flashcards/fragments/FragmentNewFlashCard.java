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
import gabilheri.com.flashcards.cardStructures.FlashCard;
import gabilheri.com.flashcards.database.MyDbHelper;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/18/14.
 */
public class FragmentNewFlashCard extends DefaultFragment implements View.OnClickListener{

    private static final String LOG_TAG = FragmentNewFlashCard.class.getSimpleName();

    private Button addNewFlashcard;
    private EditText flashcardContent, flashcardAnswer;
    private Bundle b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_flashcard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        b = getArguments();
        addNewFlashcard = (Button) view.findViewById(R.id.createFlashCard);
        addNewFlashcard.setOnClickListener(this);

        flashcardContent = (EditText) view.findViewById(R.id.flashcard_content);
        flashcardAnswer = (EditText) view.findViewById(R.id.flashcard_answer);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == addNewFlashcard.getId()) {

            MyDbHelper dbHelper = new MyDbHelper(getActivity());
            FlashCard flashCard = new FlashCard();

            if(!flashcardContent.getText().toString().isEmpty() && !flashcardAnswer.getText().toString().isEmpty()) {
                flashCard.setContent(flashcardContent.getText().toString());
                flashCard.setAnswer(flashcardAnswer.getText().toString());
                try {
                    dbHelper.createFlashCard(flashCard, b.getLong(MyDbHelper._ID));
                    Log.i(LOG_TAG, "The id of the deck is: " + b.getLong(MyDbHelper._ID));
                    Utils.hideKeyboard(getActivity());
                    ((MainActivity) getActivity()).displayView(MainActivity.FLASHCARDS_FRAG, b);
                } catch (Exception ex) {
                    Log.i(LOG_TAG, "Could not create Flashcard");
                }
            } else {
                Toast.makeText(getActivity(), "Flashcard MUST have content and answer.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
