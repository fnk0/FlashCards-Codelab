package gabilheri.com.flashcards.navDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gabilheri.com.flashcards.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/7/14.
 */
public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    /**
     * Default Constructor
     */
    public NavDrawerAdapter() {
    }

    /**
     *
     * @param context
     *      The Context on which this NavDrawer is being created
     * @param navDrawerItems
     *      The ArrayList containing the DrawersItems for the Adapter.
     */
    public NavDrawerAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<NavDrawerItem> getNavDrawerItems() {
        return navDrawerItems;
    }

    public void setNavDrawerItems(ArrayList<NavDrawerItem> navDrawerItems) {
        this.navDrawerItems = navDrawerItems;
    }

    /**
     * Internally used by the framework.
     * @return
     *      The number of elements on this adapter
     */
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    /**
     * The getItem is also necessary. Will be used by the onItemClickListener on the ListView for this adapter
     *
     * @param position
     *      The clicked position
     * @return
     *      The object for the position
     */
    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * The GetView is responsible for inflating and creating the View for each one of the items on the ListView
     * To get different behavior on the Items on a List you can do them so on the getView
     * By Example, for a list with alternate colors we could do.
     * if(position % 2 == 0) {
     *     convertView.setBackgroundColor(Color.BLUE);
     * } else {
     *      convertView.setBackgroundColor(Color.RED);
     * }
     *
     * Other Layouts can also be inflated based on the position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.navDrawerIcon);
        TextView title = (TextView) convertView.findViewById(R.id.navDrawerTitle);
        title.setText(navDrawerItems.get(position).getTitle());
        imageIcon.setImageDrawable(navDrawerItems.get(position).getIcon());
        return convertView;
    }
}
