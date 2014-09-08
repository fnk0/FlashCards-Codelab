package gabilheri.com.flashcards;

import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import gabilheri.com.flashcards.fragments.DefaultFragment;
import gabilheri.com.flashcards.fragments.FragmentCategories;
import gabilheri.com.flashcards.navDrawer.NavDrawerAdapter;
import gabilheri.com.flashcards.navDrawer.NavDrawerItem;


public class MainActivity extends DrawerLayoutActivity {

    // We use this to know which of the items has been selected.
    // We name the items so we know which one is which.
    public static final int CATEGORIES_FRAG = 0;
    public static final int SETTINGS_FRAG = 1;
    private DefaultFragment activeFragment = null;

    private NavDrawerAdapter mNavDrawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;


    @Override
    public void init() {
        // Retrieve the typedArray from the XML. Notice the weird Syntax "obtain"
        TypedArray navIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_titles); // Retrieve the titles
        navDrawerItems = new ArrayList<NavDrawerItem>(); // Initialize the ArrayList

        // Now let's add add items to the ArrayList of NavDrawer items.
        for(int i = 0; i < navMenuTitles.length; i++) {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navIcons.getDrawable(i)));
        }
        // An typed array can be recycled to avoid waste of System Resources. In our case it wouldn't matter because we only have 2 items.. but is still a good practice.
        navIcons.recycle();

        mNavDrawerAdapter = new NavDrawerAdapter(this, navDrawerItems);
    }

    @Override
    public void displayView(int position, Bundle fragmentBundle) {

        switch (position) {
            case CATEGORIES_FRAG:
                activeFragment = new FragmentCategories(); // Set the ActiveFragment to our selected item on the list
                break;
            case SETTINGS_FRAG:
                break;
            default:
                break;
        }
        if(activeFragment != null) {
            FragmentManager fragmentManager = getFragmentManager(); // Get the fragmentManager for this activity
            fragmentManager.beginTransaction() // Start the transaction of fragment change
                    .setCustomAnimations(R.animator.alpha_in, R.animator.alpha_out, // Animations for the fragment in...
                            R.animator.alpha_in, R.animator.alpha_out) // Animations for the fragment out...
                    .replace(R.id.frame_container, activeFragment) // We then replace whatever is inside FrameLayout to our activeFragment
                    .commit(); // Commit the change
            // update selected item and title
            getDrawerList().setItemChecked(position, true); // We now set the item on the drawer that has been cliced as active
            getDrawerList().setSelection(position); // Same concept as above...
            setTitle(navMenuTitles[position]); // We not change the title of the Action Bar to match our fragment.
        } else {
            Log.i(getLogTag(), "Error creating fragment"); // if the fragment does not create we Log an error.
        }

    }

    /**
     * Override this method to change the log tag string;
     *
     * @return
     */
    @Override
    public String getLogTag() {
        return "MainActivity";
    }

    @Override
    protected BaseAdapter getAdapter() {
        return mNavDrawerAdapter;
    }
}
