FlashCards
==========

A simple flash cards app to be used for the GDG Stillwater Android Codelabs.

This tutorial will be assume that you have installed

1. Java SDK >= 1.7
2. AndroiD Studio beta >= 0.8.2
3. Android Built Tools 19.1.0
4. It will also asume that you have a working android device to debug or an emulator. I personally recommend [genymotion](http://www.genymotion.com/) emulator.

Recommended Website to get dependencies: [Gradle Please](http://gradleplease.appspot.com/)

###### Creating a New Project
Open Android Studio and select New Project

![New project screenshot](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.52.49.png)
Type in the name of the project and your default Package name as a reverse URL.

![Selecting minimun SDK](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.52.58.png)
For this project we will use Ice Cream Sandwich (API 15) As our Default SDK.
We only select the Phone and Tablet module as for now those are the only one we use. In a later version of this series of tutorial we will be covering other modules such as the App Engine module for online storage and the Android Wear for integration with Android Wear Watches.  
With the SDK 15 we will cover about 86% of all Android Devices.
``` Tip: To see the coverage of an particular SDK version click on "help me choose" ```

![Blanck activity selection](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.53.10.png)
Select blank activty as we want to start this project clean without too much boylerplate.

![Main Activity name](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.53.21.png)
Name the activity, give it a title and select Finish.

![Project created](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/Screenshot%202014-08-31%2023.55.30.png)
If everything went well the project will now be created and you should be seeing something like this. 


###### Adding Dependencies
Android studio uses the [Gradle](http://www.gradle.org/) with the [Android plugin](http://tools.android.com/tech-docs/new-build-system/user-guide)to manaje it's dependencies and build system.
Each of the modules has it's own build.gradle file to manaje specific dependencies necessary for each module.

With the newest version of Android Studio sometimes Gradle makes the mistake of setting the app to target SDK 20 which is current the Android Wear SDK so let's change it a little bit.

In the build.gradle file inside the module :app
app/build.gradle
```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 19 // The SDK that we want to compile for
    buildToolsVersion "19.1.0" // The build tools that should be installed in the SDK.

    defaultConfig {
        applicationId "gabilheri.com.flashcards" // This should your application ID do not copy mine.
        minSdkVersion 15 // Here is where we change the  minimum SDK
        targetSdkVersion 19 // The target SDK version. SDK 19 = 4.4 Kit kat
        versionCode 1 // The version Code is used by the Play Store. Every time an update is pushed to the play store the version code should be updated as well.
        versionName "1.0" // Visible version name for the users on the Play Store
    }
    buildTypes {
        release {
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar']) // Will compile all the Libraries inside the 'libs' folder
    compile 'com.android.support:support-v4:19.1.0' // We will be using the support library for the Drawer layout
    compile 'com.github.gabrielemariotti.cards:library:1.6.0' // Very good library to use cards 
}

````

#### Getting Started!!

For this app We will be using the standard navigation drawer for the App Navigation and fragments to display the content of each section/card. 

##### Step 1. Create a new layout called drawer_activity.xml.

If you understand android XML you can copy and paste the following snippet otherwise I recommend that you type it paying attention to each line for a better understanding of what is going on. 

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/drawer_layout"
    android:layout_height="match_parent"
    android:layout_width="match_parent"
    >

    <!-- Framelayout to display Fragments -->
    <FrameLayout
        android:id="@+id/frame_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />

    <!-- Listview to display drawer list menu menu -->
    <ListView
        android:id="@+id/list_slidermenu"
        android:layout_width="240dp"
        android:layout_height="match_parent"
        android:layout_gravity="start|bottom"
        android:choiceMode="singleChoice"
        android:divider="@color/drawer_background"
        android:dividerHeight="1dp"
        android:clipToPadding="false"
        android:fitsSystemWindows="true"
        android:listSelector="@drawable/drawer_selector"
        android:background="@color/drawer_background"/>
</android.support.v4.widget.DrawerLayout>
````

##### Step 2. Create a generic Drawer Activity. 
As an Android Developer you might find youserlf in the situation of not being able to use fragments or you may want to use different activities with a Drawer Layout. It can also be tedious every time you start a project to code the Nav Drawer boylerplate.  
For those reasons we will extend Activity to create a Default DrawerLayoutActivity for our App.

``` Hint: Read the code carefully and make sure you understand every single aspect before going to the next step. If you don't understand something raise your hand and someone will explain it to you. ```
```java
public abstract class DrawerLayoutActivity extends Activity {

    private final String LOG_TAG = getLogTag();

    /**
     * Nav Drawer stuff
     */
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle, mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        /**
         * Drawer Layout stuff
         */
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList.setOnItemClickListener(new DrawerListener());
        mDrawerList.setAdapter(getAdapter());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, // Nav drawer Icon
                R.string.app_name, // Nav drawer open - description for accessibility
                R.string.app_name // Nav drawer close
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstance has occurred
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Drawer listener.
     */
    private class DrawerListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            /**
             * A handler with a postDelayed is used so it only changes fragments once the drawer is
             * already closed. This can be adjusted if the timing is not right.
             * Similar behavior that most Google apps offers.
             */
            mDrawerLayout.closeDrawer(mDrawerList);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayView(position, null);
                }
            }, 300);
        }
    }

    /**
     * Method to define what clicking on the drawer will do.
     *
     * @param position
     *          The position of the clicked item
     * @param fragmentBundle
     *          A bundle in case something needs to be passed to a specific fragment
     */
    public abstract void displayView(int position, Bundle fragmentBundle);

    /**
     * Any specific initializations should go here.
     */
    public abstract void init();

    /**
     * Override this method to change the log tag string;
     * @return
     */
    private String getLogTag() {
        return "DrawerActivity";
    }

    /**
     * Override this method in case of need for a different list colors, etc..
     * Should use same Id's to avoid confusion
     * @return
     *      The Activity layout for this drawer activity
     */
    private int getLayout() {
       return R.layout.drawer_activity;
    }

    /**
     * Getter for the drawer toggle
     * @return
     *      The drawer toggle for this activity
     */
    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    /**
     *
     * @return
     *      The List used by the drawer
     */
    public ListView getDrawerList() {
        return mDrawerList;
    }

    /**
     *
     * @return
     *      The Drawer Layout used by this activity
     */
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    /**
     *
     * Method to be Overriden that will return an Adapter that extends Base Adapter
     * The adapter will them be used by the Drawer Layout
     *
     * @return
     */
    protected abstract BaseAdapter getAdapter();
}
```

##### Step 3. Create a Default Fragment

The default fragment is where we gonna put the Default Functions that will be true for all fragments.
For now let's just override ```onInflate() ``` so we avoid errors when inflating an fragment that has already been inflated.
```java
public class DefaultFragment extends Fragment {
    // The default Fragment is used so we avoid errors when trying to inflate a fragment that has already been inflated.
    // All our other Fragments will extend DefaultFragment
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            fm.beginTransaction().remove(this).commit();
        }
        super.onInflate(activity, attrs, savedInstanceState);
    }
}

```
##### Step 4. Creating the Navigation Drawer Adapter. 

Our navigation drawer will have the following format: Icon + Title
![Nav drawer](https://raw.githubusercontent.com/fnk0/FlashCards/master/Screenshots/nav_drawer_item.png)

The top and Bottom padding are both 8dp. From the beginning to the end of the Thumbnail there is 72dp. The text is centered. We follow those guidelines to match the new UI Design guidelines established by google. For more metrics guidelines check the [google design website](http://www.google.com/design/spec/layout/metrics-and-keylines.html#metrics-and-keylines-keylines-and-spacing).

Now let's translate that picture above to see how it will look like in code. 
Nav Drawer Item XML: 
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="72dip"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_vertical_margin"
    android:paddingBottom="8dip"
    android:paddingTop="8dip"
    android:gravity="center_vertical"
    android:background="@drawable/drawer_selector">

    <ImageView
        android:id="@+id/navDrawerIcon"
        android:layout_width="48dip"
        android:layout_height="48dip"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_centerVertical="true" />

    <TextView
        android:id="@+id/navDrawerTitle"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_toRightOf="@id/navDrawerIcon"
        android:layout_toEndOf="@id/navDrawerIcon"
        android:minHeight="?android:attr/listPreferredItemHeightSmall"
        android:textAppearance="?android:attr/listPreferredItemHeightLarge"
        android:gravity="center_vertical"
    />
</RelativeLayout>
```

Now we need an Object that will represent the layout we just create. It will be a simple object with just the title of the item and the thumbnail Icon.

```java
public class NavDrawerItem  {

    private String title;
    private Drawable icon;

    public NavDrawerItem(String title, Drawable icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
```

####### Creating the Adapter. 

When I just started programming for Android creating Adapters was always one of the scariest things for me. They are an odd structure if you are used to other platformms. We gona create a very simple Base Adapter that will use the Layout and the object we just created to populate a ```ListView```.

NavDrawerAdapter:
```java
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
```
