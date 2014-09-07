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

###### Step 1. Create a new layout called drawer_activity.xml.

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

###### Step 2. Create a generic Drawer Activity. 
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


