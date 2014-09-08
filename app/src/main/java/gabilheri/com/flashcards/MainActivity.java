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

    private NavDrawerAdapter mNavDrawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;
    private DefaultFragment activeFragment = null;

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
                activeFragment = new FragmentCategories();
                break;
            case SETTINGS_FRAG:
                break;
            default:
                break;
        }

        if(activeFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.alpha_in, R.animator.alpha_out)
                    .replace(R.id.frame_container, activeFragment)
                    .commit();
            // update selected item and title, then close the drawer
            getDrawerList().setItemChecked(position, true);
            getDrawerList().setSelection(position);
            setTitle(navMenuTitles[position]);
        } else {
            Log.i(getLogTag(), "Error creating fragment");
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
