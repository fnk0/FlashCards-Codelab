FlashCards Tutorial Part 2
==========

* If you haven't finished [Part 1](https://github.com/fnk0/FlashCards-Codelab/blob/master/TutorialPart1.md) yet I strongly recommend so.
* Optionally if you already understand the basics of Android you can start this tutorial from part 1 by downloading Part 1 code from here.

#### Part 1. Creating or models.

* Let's start up by creating our models. This will be a basic Flashcards app with the following models.
  * Categories --> A flashcard will have decks
  * Decks --> A deck will belong to a category and have flashcards
  * Flashcards --> Wil belong to a deck and have no direct children.

* To make things a little easier we will make a basic structure to which all the other models will extend.

###### Basic Structure --> CardItem

```java
// CardItem.java
public abstract class CardItem {

    private String title; // All Items must have a title so we can display on a list
    private long id; // Each card will also have an ID so we can find it inside our SQLite DB
    private int color; // Optionally you might want separate cards by color.

    /**
     * Basic Constructor
     */
    protected CardItem() {
    }

    /**
     * @param title
     *       The title for this Item
     */
    protected CardItem(String title) {
        this.title = title;
    }

    // STANDARD GETTERS AND SETTERS

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
```

###### Categories Structure extends CardItem

```java
// Category.java
public class Category extends CardItem {

    private ArrayList<Deck> decks; // The list of decks that belong to this category

    /**
     * Basic Constructor
     */
    public Category() {
    }

    /**
     *
     * @param categoryName
     *          The Name for this category
     * @param decks
     *          The list of decks for this category
     */
    public Category(String categoryName, ArrayList<Deck> decks) {
        super(categoryName);
        this.decks = decks;
    }

    // STANDARD GETTER AND SETTERS

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }
}

```

###### Deck and Flashcards code..

To keep things a bit short on this file...
* get the code for Deck [here](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cardStructures/Deck.java)
* get the code for FlashCard [here](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cardStructures/FlashCard.java)


#### Part 2. Database!

The first level of persistence data is to create a SQLite3 Database that will store our app information.
The first step to get a functional Database in Android is making ourselves a DatabaseHelper. In the Android framework a Database helper is a class that interacts with a specific database.
The easiest way of handling all database interactions is by extending SQLiteOpenHelper.

###### Creating our Helper:

``` java
// MyDbHelper.java
public class MyDbHelper extends SQLiteOpenHelper {

    // Database versions are used to internally tell the Framework which version of your DB to use
    // When the database constraints are changed the database them gets updated.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "flashcards.db"; // the .db is optional.

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // This method will be called whenever the Database needs to be created.
        // Will only Run if the Database does not exist
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is called whenever we need to update our Database
        // Examples of updating a DB is adding new tables or new columns to a existing table
    }

    /**
    * Closes the Database Connection.
    */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
```

###### Adding Our database constants to our Helper:

We are going to create a few constants to avoid typos as we gonna have to re-use it to create our tables.

```java
// Inside MyDbHelper.java

/**
 * COMMON DATABASE CONSTANTS
 */
public static final String _ID = "id"; // we need to use _ID because ID is already used by the system.
public static final String TITLE = "title";
public static final String BELONGS_TO = "belongs_to";

/**
 * CATEGORIES DATABASE CONSTANTS
 */
public static final String CATEGORIES_TABLE = "categories";

/**
 * DECKS DATABASE CONSTANTS
 */
public static final String DECKS_TABLE = "decks";

/**
 * FLASHCARDS DATABASE TABLE
 */
public static final String FLASHCARDS_TABLE = "flashcards";
public static final String FLASHCARD_CONTENT = "content";
public static final String FLASHCARD_ANSWER = "answer";

```

###### Creating our Database:

Now that we have our constants ready we will update our onCreate method.
I will not bother too much explaining the SQL statement itself. If you want to learn more about SQLite refer to its [documentantion](http://www.sqlite.org/docs.html)

```java
// Inside MyDbHelper.onCreate()

  final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE " + CATEGORIES_TABLE + " (" 
          _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          TITLE + " TEXT NOT NULL " +
          ");";
  final String SQL_CREATE_DECKS_TABLE = "CREATE TABLE " + DECKS_TABLE + " (" +
          _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          TITLE + " TEXT NOT NULL, " +
          BELONGS_TO + " INTEGER NOT NULL, " +
          "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + CATEGORIES_TABLE + " (" + _ID + ")" +
          ");";

  final String SQL_CREATE_FLASHCARDS_TABLE = "CREATE TABLE " + FLASHCARDS_TABLE + " (" +
          _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
          TITLE + " TEXT NOT NULL, " +
          FLASHCARD_CONTENT + " TEXT NOT NULL, " +
          FLASHCARD_ANSWER + " TEXT NOT NULL, " +
          BELONGS_TO + " INTEGER NOT NULL, " +
          "FOREIGN KEY (" + BELONGS_TO + ") REFERENCES " + DECKS_TABLE + " (" + _ID + ")" +
          ");";

  // We now create our tables.
  // If the tables already exist Android will them ignore this statement.
  db.execSQL(SQL_CREATE_CATEGORY_TABLE);
  db.execSQL(SQL_CREATE_DECKS_TABLE);
  db.execSQL(SQL_CREATE_FLASHCARDS_TABLE);

```  

###### onUpgrade()

For the purpose of this tutorial we will have the simplest method of upgrading a Database.
When dealing within your own apps use ALTER TABLE instead...
The reason of not using Alter Table right now is because it can be very specific for each case

```java
// Inside MyDbHelper.onUpgrade()

// This statements will delete the current tables and call onCreate with the new create table queries.
// We will never be using this in this tutorial but keep in mind that onUpgrade will be called whenever you want
// to add a field to the Database.
db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
db.execSQL("DROP TABLE IF EXISTS " + DECKS_TABLE);
db.execSQL("DROP TABLE IF EXISTS " + FLASHCARDS_TABLE);
onCreate(db);

```

###### CRUD:

The 4 verbs for database management are:
* C - Create
* R - Read
* U - Update
* D - Delete

I will be showing how to implement 3 of those and leave 1 for you to implement.

###### Create:
```java
/**
* This method is used to insert a new Category entry in the database
*
* @param category
*      The category to be inserted
* @return
*      The inserted statement for the Database - Used internally by Android
*/
public long createCategory(Category category) {
    SQLiteDatabase db = this.getWritableDatabase(); // We get an WritableDatabase so we can insert data
    ContentValues values = new ContentValues(); // Create a new content values with Key Value Pairs
    values.put(TITLE, category.getTitle()); // Insert the TITLE for the Category

    return db.insert(CATEGORIES_TABLE, null, values);
}

```

###### Read: 2 Formats --> Read 1 Item or read all Items.
* Read 1 item

```java
// This method gets the category by ID. Optionally we can query other tables such as title, etc..
/**
* Method to get a category based on it's title
*
* @param id
*       The id of the Category
* @return
*      A Category object
*/
public Category getCategoryByID(long id) {
    SQLiteDatabase db = this.getReadableDatabase(); // We get a readable database as we only want to access it
    String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE + " WHERE " + _ID + " = " + id + ";";

    Cursor cursor = db.rawQuery(selectQuery, null);
    if(cursor != null) {
        cursor.moveToFirst();
    } else {
        Log.i(LOG_TAG, "Cursor is null!!");
        return null;
    }

    Category category = new Category();
    category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
    category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
    this.closeDB();
    cursor.close();
    return category;
}

```

* Read all Items

```java
 /**
*
* @return
*      A list with all the categories
*/
public List<Category> getAllCategories() {
    List<Category> categories = new ArrayList<Category>();
    String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if(cursor.moveToNext()) {
        do {
            Category category = new Category();
            category.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            category.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            categories.add(category);
        } while (cursor.moveToNext());
    }
    return categories;
}
```

* Read all items related to another item. For this example we will read all decks for a specific category

```java
/**
*
* @param id
*      The category to which we want all Decks
* @return
*      A list with all Decks for the specific category
*/
public List<Deck> getAllDecksForCategoryId(long id) {
    List<Deck> decks = new ArrayList<Deck>();
    String selectQuery = "SELECT * FROM " + DECKS_TABLE + " WHERE " + BELONGS_TO + " = " + id + ";";

    SQLiteDatabase db  = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if(cursor.moveToFirst()) {
        do {
            Deck deck = new Deck();
            deck.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            deck.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            deck.setCategory(getCategoryByID(id));
            decks.add(deck);
        } while (cursor.moveToNext());
    }
    cursor.close();
    this.closeDB();
    return decks;
}
```

###### Update: Your job to implement :)

###### Delete: 

For deletion we will have a more generic method as we don't care about inserting the right element into the Database.

```java
/**
* General method to delete an entry from the Database based on it's ID
* @param id
*      The ID Of the item to be deleted
* @param table
*      The table to which the item should be deleted.
*/
public void deleteFromDB(long id, String table) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(table, _ID + " = ?", new String[] {String.valueOf(id)});
    this.closeDB();
}
```

* For the rest of database items please check out the [full impementation of MyDbHelper](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/database/MyDbHelper.java)

* Testing our Database... Most of the time the time, in between the DbHelper creation and the implementantion inside the app can take a while... that is why we want to know that the Database works properly. The best way to do it is Testing it. I will not go into detail of explaining how the test works as it would require an entired separate tutorial. 
* Once you have finished the MyDbHelper feel free to copy [my tests](https://github.com/fnk0/FlashCards-Codelab/tree/master/app/src/androidTest/java/gabilheri/com/flashcards) and implement in your app. 
* Create a new Run configuration for the tests and run it. The tests will create a Database with some dummy data and make sure everything works fine.

#### Part 3. Creating our custom Cards to be used by the CardsLib:

###### Adding Assets icon: 
Before continuing I strongly recommend that you add the Icon assets that will be used by the following XML elements. In the assets folder that you downloaded use the *category_icon*, *deck_icon*, and *flashcard_icon* to create:
* ic_category_tumb
* ic_decks
* ic_flashcard

Note: to create the icons right click the res folder and select *New > Image Asset*. If you are having troubles with this part there is a detailed guide on the [part 1](https://github.com/fnk0/FlashCards-Codelab/blob/master/TutorialPart1.md) of the tutorial on how to do it. 

Our list items will be almost the same, except for the Icon or the actions made by the DbHelper.
I will put the code here for the Category card and a link for the rest of them.
###### Category Card:

Layout file: create a xml file named card_category.xml

```xml
<!-- Inside card category.xml -->
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:paddingTop="8dip"
    android:paddingBottom="8dip"
    android:paddingLeft="@dimen/activity_vertical_margin"
    android:paddingRight="@dimen/activity_vertical_margin"
    android:gravity="center_vertical"
    android:layout_height="88dip">

    <ImageView
        android:src="@drawable/ic_category_thumb"
        android:layout_width="72dip"
        android:layout_height="72dip" />

    <TextView
        android:id="@+id/titleCategory"
        android:text="@string/category_name"
        android:layout_marginLeft="@dimen/activity_vertical_margin"
        android:layout_marginStart="@dimen/activity_vertical_margin"
        android:textSize="28sp"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:fontFamily="sans-serif-light"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```

We are going to use the built in features of the CardsLib to extend Card and create our own custom cards.
For more information about creating custom cards refer to the [CardsLib documentantion](https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARD.md#extending-card-class)

Side Note: Bundle:
Bundle is an Android object that maps a Name to a Value. It is used by Android to pass prcelable objects in between fragments and activities. 
For more information refer to the [Bundle documentantion](http://developer.android.com/reference/android/os/Bundle.html)

I would also recommend reading about the [Parcelable interface](http://developer.android.com/reference/android/os/Parcelable.html) in Android. 

```java
// Inside CategoryCard.java
public class CategoryCard extends Card implements Card.OnSwipeListener, Card.OnCardClickListener, Card.OnUndoSwipeListListener {

    private Category category;
    private MyDbHelper helper;

    public CategoryCard(Context context) {
        super(context, R.layout.card_category);
        helper = new MyDbHelper(context);
        this.setSwipeable(true); 
        this.setOnClickListener(this);
        this.setOnSwipeListener(this);
        this.setOnUndoSwipeListListener(this);
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
        Bundle bundle = new Bundle();
        bundle.putLong(MyDbHelper._ID, category.getId());
        bundle.putString(MyDbHelper.TITLE, category.getTitle());

        ((MainActivity) getContext()).displayView(MainActivity.DECKS_FRAG, bundle);
    }

    @Override
    public void onSwipe(Card card) {
        helper.deleteFromDB(category.getId(), MyDbHelper.CATEGORIES_TABLE);
    }

    @Override
    public void onUndoSwipe(Card card) {
        helper.undoCategory(category);
    }
}
```

###### XML Resources for the custom cards:

* [Deck](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/res/layout/card_deck.xml)
* [FlashCard](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/res/layout/card_flashcard.xml)
* [FlashCard Content](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/res/layout/flashcard_content.xml)

##### Java code for the Custom cards:

* [Deck](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cards/DeckCard.java)
* [ListFlashCard](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/cards/ListFlashCardCard.java)
* [FlashCardViewer](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/fragments/FragmentFlashCardViewer.java)

#### Part 4. Displaying elements from the DB. 

###### Categories: 
It is time now to have our categories fragment display elements Dynamically! We gonna delete the part thar displays dummy data and make it query a list to our DB.

Luckily for us we just need to update a few lines of code to handle that :) 

```java
// Inside FragmentCategories.onViewCreated()
// We gonan update our current loop with this new loop.

MyDbHelper dbHelper = new MyDbHelper(getActivity()); // Get a reference to our DbHelper object
List<Category> categories = dbHelper.getAllCategories(); // Query all the existend categories

for (int i = 0; i < categories.size(); i++) { // Loop through the categories 1 by 1 and make a new category card.
    CategoryCard card = new CategoryCard(getActivity());
    card.setId(String.valueOf(categories.get(i).getId()));
    card.setCategory(categories.get(i));
    mCardsList.add(card);
}

```

###### Decks: 

Decks is very similar to Categories. So I will leave a link here to the code.
* [FragmentDecks](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/fragments/FragmentDecks.java)

###### FlashCards List:

Same for the flashcards list.. 
* [FragmentFlashCardsList](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/fragments/FragmentFlashCardsList.java)


#### Part 5. Creating Elements...

So far we can view our lists.. but besides the dummy data injected by the tests we can't see anything there... Well.. let's change that!!

The form to create a new element is quite simple.. We will have an XML element for the UI part and a Fragment to display it. Optionally we could use a DialogFragment for that also.

* Code for the fragment_new_category.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingRight="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_vertical_margin"
    android:paddingTop="@dimen/activity_horizontal_margin"
    android:paddingBottom="@dimen/activity_horizontal_margin"
    >

    <EditText
        android:id="@+id/categoryTitle"
        android:hint="@string/category_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <Button
        android:id="@+id/addNewCategory"
        android:layout_marginTop="@dimen/activity_vertical_margin"
        android:text="@string/create_category"
        android:textColor="@android:color/white"
        android:background="@drawable/default_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>

```

* Very similar for New Deck and New Flashcard:
  * [fragment_new_deck.xml](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/res/layout/fragment_new_deck.xml)
  * [fragment_new_flashcard](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/res/layout/fragment_new_flashcard.xml)

###### The Create Categories fragment code.

There is not much going on with this fragment. Basic code that will get the imput from the fields and create a entry in our Database.

```java
public class FragmentNewCategory extends DefaultFragment implements View.OnClickListener{

    private static final String LOG_TAG = FragmentNewCategory.class.getSimpleName();

    private Button btnAddCategory;
    private EditText categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnAddCategory = (Button) view.findViewById(R.id.addNewCategory);
        btnAddCategory.setOnClickListener(this);

        categoryName = (EditText) view.findViewById(R.id.categoryTitle);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btnAddCategory.getId()) {

            MyDbHelper dbHelper = new MyDbHelper(getActivity());
            Category category = new Category();

            if(!categoryName.getText().toString().isEmpty()) {
                category.setTitle(categoryName.getText().toString());
                try {
                    dbHelper.createCategory(category);
                    Utils.hideKeyboard(getActivity());
                    ((MainActivity) getActivity()).displayView(MainActivity.CATEGORIES_FRAG, null);
                } catch (Exception ex) {
                    Log.i(LOG_TAG, "Could not create category");
                }
            } else {
                Toast.makeText(getActivity(), "Category MUST have a title.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
```

* Very similar for New Deck and New Flashcard:
  * [New Deck](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/fragments/FragmentNewDeck.java)
  * [New Flashcard](https://github.com/fnk0/FlashCards-Codelab/blob/master/app/src/main/java/gabilheri/com/flashcards/fragments/FragmentNewFlashCard.java)


#### Part 6. Updating our Activities...

Now that we have a how bunch of new fragments let's update our displayView method to handle those new fragments.

First let's add our constants. Optionally we could substitute these with a Enum or placing those numbers ina XML file for integers.

```java
// Inside MainActivity.java
// We use this to know which of the items has been selected.
// We name the items so we know which one is which.
// For the fragments that will be OUTSIDE of the drawer layout we use negative numbers so we avoid a conflict.

public static final int FLASHCARDS_FRAG = -5;
public static final int DECKS_FRAG = -4;
public static final int NEW_FLASHCARD_FRAG = -3;
public static final int NEW_DECK_FRAG = -2;
public static final int NEW_CATEGORY_FRAG = -1;
```

The next step is to update our displayView to handle those new cases.
We will also add a couple new things in here. Such as adding the ability to handle the backstack. 

```java
//Inside DrawerLayoutActivity.java

/**
* Handy method to clear the back stack. We want to do this to avoid back stack bugs.
*/
public void clearBackStack() {
    FragmentManager manager = getFragmentManager();
    if (manager.getBackStackEntryCount() > 0) {
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

@Override
public void onBackPressed() {
    // If the back stack is empty we let android handle the back button
    if(getFragmentManager().getBackStackEntryCount() == 0) {
        super.onBackPressed();
    } else {
        // Otherwise we remove it from the back stack and the framework will handle the
        // fragment change for us :)
        getFragmentManager().popBackStack();
        getActionBar().setTitle(mTitle);
    }
}

```

With the DrawerLayoutActivity updated let's update our displayView() method.

```java

FragmentManager fragmentManager = getFragmentManager(); // Get the fragmentManager for this activity
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

switch (position) {
    case CATEGORIES_FRAG:
        activeFragment = new FragmentCategories(); // Set the ActiveFragment to our selected item on the list
        clearBackStack(); // Clear the back stack to avoid back presses bugs
        break;
    case SETTINGS_FRAG:
        break;
    case NEW_CATEGORY_FRAG:
        activeFragment = new FragmentNewCategory();
        fragmentTransaction.addToBackStack(null); // null = name of the fragment on the stack.
        break;
    case NEW_DECK_FRAG:
        activeFragment = new FragmentNewDeck();
        fragmentTransaction.addToBackStack(null);
        break;
    case DECKS_FRAG:
        activeFragment = new FragmentDecks();
        fragmentTransaction.addToBackStack(null); 
        break;
    case FLASHCARDS_FRAG:
        activeFragment = new FragmentFlashCardsList();
        fragmentTransaction.addToBackStack(null);
        break;
    case NEW_FLASHCARD_FRAG:
        activeFragment = new FragmentNewFlashCard();
        fragmentTransaction.addToBackStack(null);
        break;
    default:
        break;
}

if(activeFragment != null) {
    if(fragmentBundle != null) {
        currentBundle = fragmentBundle;
        activeFragment.setArguments(fragmentBundle);
    }
    fragmentTransaction.setCustomAnimations(R.animator.alpha_in, R.animator.alpha_out, // Animations for the fragment in...
                  R.animator.alpha_in, R.animator.alpha_out) // Animations for the fragment out...
            .replace(R.id.frame_container, activeFragment, TAG_ACTIVE_FRAGMENT) // We then replace whatever is inside FrameLayout to our activeFragment
            .commit(); // Commit the change

    // update selected item and title
    if(position >= 0) {
        getDrawerList().setItemChecked(position, true); // We now set the item on the drawer that has been cliced as active
        getDrawerList().setSelection(position); // Same concept as above...
        setTitle(navMenuTitles[position]); // We not change the title of the Action Bar to match our fragment.
    } else {
        if(fragmentBundle == null) {
            setTitle(fragmentTitles.get(position)); // We not change the title of the Action Bar to match our fragment.
        } else {
            setTitle(fragmentBundle.getString(MyDbHelper.TITLE));
        }
    }
} else {
    Log.i(getLogTag(), "Error creating fragment"); // if the fragment does not create we Log an error.
}

```

To handle our backstack changed we also have to update DefaultFragment. 

* First we implement FragmentManager.OnBackStackChangedListener to lsiten to changes in the backstack.

```java

public abstract class DefaultFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {}

```

* Next  we will add a reference to MainActivity:

```java

private MainActivity mainActivity; 

// Now we change onCreate to look like this: 

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setHasOptionsMenu(true);
    mainActivity = (MainActivity) getActivity();
    getFragmentManager().addOnBackStackChangedListener(this);
    // Retain this fragment across configuration changes.
    setRetainInstance(true);
    shouldDisplayHomeUp();
}

// Somewhere else we add these 2 methods... 
@Override
public void onBackStackChanged() {
    shouldDisplayHomeUp();
}

/**
* Handles if we should display the Drawer hamburger icon or if we should display the back navigation button. 
*/
public boolean shouldDisplayHomeUp() {
    //Enable Up button only  if there are entries in the back stack
    boolean canback = false;
    try {
        canback = getFragmentManager().getBackStackEntryCount() > 0;
    } catch (Exception ex) {};
    if (canback) {
        mainActivity.getDrawerToggle().setDrawerIndicatorEnabled(false);
    } else {
        mainActivity.getDrawerToggle().setDrawerIndicatorEnabled(true);
    }
    return canback;
}

```

* And finally we should update our onOptionsItemSelected to handle the back up navigation.

```java
// Still inside DefaultFragment

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if(shouldDisplayHomeUp()) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mainActivity.onBackPressed();
                return true;
        }
    }
    return super.onOptionsItemSelected(item);
}

```

#### Part 7. Implementing the Study Fragment.

The study fragment is where we will be able to see our FlashCards.

In this fragment we will be using a ViewPager. 
The basic structure of the Study fragment is that we will Have a list of FlashCardViewer fragments.
Each FlashCardViewer fragment will have a basic structure of a TextSwatcher to animate the change in between content and answer.

* First we will make the Layout for the Fragment FlashCard Viewer:

```xml
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginLeft="@dimen/activity_horizontal_margin"
    android:layout_marginStart="@dimen/activity_horizontal_margin"
    android:layout_marginRight="@dimen/activity_horizontal_margin"
    android:layout_marginEnd="@dimen/activity_horizontal_margin"
    android:layout_marginTop="@dimen/activity_vertical_margin"
    android:layout_marginBottom="@dimen/activity_vertical_margin"
    >

    <android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="gabilheri.com.flashcards.fragments.FragmentFlashCardViewer" />

</LinearLayout>
```

* Next we will make the Layout that will display the content for each fragment.

```xml

<?xml version="1.0" encoding="utf-8"?>

<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginLeft="@dimen/activity_horizontal_margin"
    android:layout_marginStart="@dimen/activity_horizontal_margin"
    android:layout_marginRight="@dimen/activity_horizontal_margin"
    android:layout_marginEnd="@dimen/activity_horizontal_margin"
    android:layout_marginTop="@dimen/activity_vertical_margin"
    android:layout_marginBottom="@dimen/activity_vertical_margin"
    >

    <android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="gabilheri.com.flashcards.fragments.FragmentFlashCardViewer" />

</LinearLayout>

```

* Next we will have to create the code to handle the Fragment FlashCard Viewer. 

```java

public class FragmentFlashCardViewer extends DefaultFragment implements View.OnClickListener {

    private static final String LOG_TAG = FragmentFlashCardViewer.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<FlashCardViewerCard> mCardsList;
    private MyDbHelper dbHelper;
    private Bundle activeBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return inflater.inflate(R.layout.fragment_flashcard_viewer, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        activeBundle = getArguments();

        if(activeBundle == null) {
            activeBundle = savedInstanceState;
        }

        dbHelper = new MyDbHelper(getActivity());
        mCardsList = new ArrayList<FlashCardViewerCard>();
        List<FlashCard> flashcards = dbHelper.getAllFlashCardsForDeckId(activeBundle.getLong(MyDbHelper._ID));

        for(int i = 0; i < flashcards.size(); i++) {
            FlashCardViewerCard card = new FlashCardViewerCard(getActivity());
            card.setId(String.valueOf(flashcards.get(i).getId()));
            card.setCard(flashcards.get(i));
            mCardsList.add(card);
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
      // We are not using this right now but is always handy to override the click listener in case we want to add something else.
    }

    /**
     * /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment mFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            mFragment = new FragmentFlashcard();
            ((FragmentFlashcard) mFragment).setCard(mCardsList.get(position));

            return mFragment;
        }

        @Override
        public int getCount() {
            return mCardsList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }
}

```

* Now we add it to our list in mainAxtivity and to displayView()

```java

public static final int FLASHCARDS_VIEWER = -6;

// them inside the switch in displayView()
case FLASHCARDS_VIEWER:
  activeFragment = new FragmentFlashCardViewer();
  fragmentTransaction.addToBackStack(null);
  break;

```

Now test it out... and see if works!! I will be changing this ending once it has been proofread and tested. :)








