package gabilheri.com.flashcards;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/20/14.
 */
public class Utils {

    /**
     * Handy function to hide the soft key keyboard
     * @param context
     *        The activity for which the keyboard belongs to
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void saveStudyState(Context mContext, Bundle b) {
        SharedPreferences mPref = mContext.getSharedPreferences("study", Context.MODE_PRIVATE);
    }
}
