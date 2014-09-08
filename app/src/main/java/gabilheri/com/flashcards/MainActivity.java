package gabilheri.com.flashcards;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import gabilheri.com.flashcards.navDrawer.NavDrawerAdapter;
import gabilheri.com.flashcards.navDrawer.NavDrawerItem;


public class MainActivity extends DrawerLayoutActivity {

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

    }


    @Override
    protected BaseAdapter getAdapter() {
        return mNavDrawerAdapter;
    }
}
