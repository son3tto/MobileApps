# NotePad - 扩展功能实现	

Author: [@sonettofighting](https://github.com/sonettofighting)

This is an Android Studio rebuild of google SDK sample NotePad.

### What's new!

1. Add timestamp for each of the note items.
2. Fuzzy search is now available.
3. Activate Dark Mode.

[TOC]



### Setting up the XML files

​	本次实验没有用到除sample源文件外的其他组件，所以只需要做小的调整即可。

#### 1. New Vector Assets

新建的Vector Asset将会作为菜单中查找功能和之后设置dark mode的图标

![image-20200517135245491](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200517135245491.png)

ps: 做到一半给Android Studio换了个主题，好看多了！IDEA真好啊！比eclipse👍

![image-20200531034849759](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200531034849759.png)

#### 2. list_options_menu.xml

```xml
    <item
        android:id="@+id/menu_search"
        android:icon="@drawable/ic_search_black_24dp"
        android:showAsAction="always"
        android:title="@string/search"
      android:actionViewClass="android.widget.SearchView"/>

    <item
        android:id="@+id/menu_setting"
        android:icon="@drawable/ic_settings_black_24dp"
        android:title="@string/setting" />
```

如下两句结合使用，可以使图标保持显示，而不会被折叠（collapse）。

>  android:showAsAction="always"
> android:icon="@drawable/ic_search_black_24dp"

为menu item添加一个SearchView的widget。

>  android:actionViewClass="android.widget.SearchView"

#### 3. noteslist_item.xml

此处为了实现时间戳，我在原有的基础上进行了比较大的修改，嵌套了约束布局。

但是这样应该会比较消耗资源。

约束布局中添加2个TextView。一个用于显示笔记标题，另一个用于显示时间戳。

```XML
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <TextView
        android:id="@android:id/text1"
        android:layout_width="match_parent"
        android:layout_height="?android:attr/listPreferredItemHeight"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:gravity="center_vertical"
        android:singleLine="true">

    </TextView>
    <TextView
        android:id="@+id/text3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"/>
</android.support.constraint.ConstraintLayout>
```

#### 4. mode_setting.xml

新增一个设置的布局文件，配合新的Activity使用，实现主题的切换。

本来是想用约束布局的，但是感觉这里用线性布局比较好操作啊？

```xml
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/mode_switch_img"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:src="@drawable/ic_brightness_4_black_24dp"
            android:contentDescription="TODO" />


        <TextView
            android:id="@+id/mode_switch_text"
            android:layout_width="77dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:text="@string/mode_switch" />


        <Switch
            android:id="@+id/mode_switch_switch"
            android:layout_width="251dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:layout_marginEnd="30dp"
            android:clickable="true" />
    </LinearLayout>
    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:foreground="@android:color/darker_gray" />
</LinearLayout>

```

#### 5. Manifest.xml

因为要增加一个设置页面 ,所以在manifest文件中对activity注册.

```xml
        <activity android:name=".SettingActivity"
            android:theme="@style/Theme.AppCompat.Light.DarkActionBar">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

```



### Modifying JAVA class files

#### 1.NotePadProvider

要想实现时间戳，首先得把数据库中长整型的时间属性转换为人可以阅读的时间模式。

根据google sample精美的注释，我们发现这个类直接与数据库的底层有关，所以从这里入手，修改属性类型。

> ```javad
> /**
>  * Provides access to a database of notes. Each note has a title, the note
>  * itself, a creation date and a modified data.
>  */
> ```

用快捷键很快就可以找到关于时间的函数。

重写获取时间的函数，并返回String类型的日期。

```java
// Gets the current system time in milliseconds
//Long now = Long.valueOf(System.currentTimeMillis());
  String now = this.reformatCurrentDate();
```

模仿sample，我也写下一个精美的注释，仿佛明天就可以去google上班。

```JAVA
    /**
     * reformat the date
     */
    public String reformatCurrentDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdFormatter.format(nowTime);
    }
```

最后，这个文件还有一处需要修改。

那就是创建表的时候，需要把创建时间和修改时间的属性改为TEXT类型。

```java
       /**
        *
        * Creates the underlying database with table name and column names taken from the
        * NotePad class.
        */
       @Override
       public void onCreate(SQLiteDatabase db) {
           db.execSQL("CREATE TABLE " + NotePad.Notes.TABLE_NAME + " ("
                   + NotePad.Notes._ID + " INTEGER PRIMARY KEY,"
                   + NotePad.Notes.COLUMN_NAME_TITLE + " TEXT,"
                   + NotePad.Notes.COLUMN_NAME_NOTE + " TEXT,"
                   + NotePad.Notes.COLUMN_NAME_CREATE_DATE + " TEXT,"
                   + NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE + " TEXT"
                   + ");");
       }
```

做到这里，我本以为很完美，没想到，竟然还有一处没有修改。

接上下一个文件。

#### 2. NoteEditor

设想业务背景: 有时间戳，那一定是新建笔记/修改笔记，提交之后才能生成。

所以单单修改数据库相关的操作是不够的，使用者需要在编辑并提交之后，数据才会转交到数据库。

so，在编辑笔记的类中，需要修改updateNote方法，把时间字符串传入（put）ContentValues里面，再由ContentResolver通过uri把values交给数据库。

```java
   private final void updateNote(String text, String title) {
        // Sets up a map to contain values to be updated in the provider.
        ContentValues values = new ContentValues();
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, this.reformatCurrentDate());

					...................

        getContentResolver().update(
                mUri,    // The URI for the record to update.
                values,  // The map of column names and new values to apply to them.
                null,    // No selection criteria are used, so no where columns are necessary.
                null     // No where columns are used, so no where arguments are necessary.
            );

    }

```

这样，大部分的工作都完成了，只剩下adapter需要增加显示的地方做出修改即可。

#### 3. NoteList

再次根据sample精美的注释和本人的推测，这一定就是主界面了。

在这个类中设置这么几个公有的变量。

``` JAVA
    SearchView mSearchView;  //用于搜索功能
    Cursor searchCursor; //用于搜索功能的查询
    SimpleCursorAdapter adapter; //用于绑定数据
```
为了Cursor类能够找到数据库需要映射（projection）的modification_date，需要在PROJECTION中增加一个数据。

``` java
    /**
     * The columns needed by the cursor adapter
     */
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE 
    };
```
结合使用managedQuery()方法，就可以获得查找的索引。
>         Cursor cursor = managedQuery(
            getIntent().getData(),            // Use the default content URI for the provider.
            PROJECTION,                       // Return the note ID, title and modified date for each note.
            null,                             // No where clause, return all records.
            null,                             // No where clause, therefore no where column values.
            NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );
为了列表可以显示数据，设置SimpleCursorAdapter。

dataColumns是src, viewIDs是des。

``` java
        // The names of the cursor columns to display in the view, initialized to the title column
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE, NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
        // The view IDs that will display the cursor columns, initialized to the TextView in
        // noteslist_item.xml
        int[] viewIDs = { android.R.id.text1, R.id.text3};
```

配合使用

> ```
> // Creates the backing adapter for the ListView.
> adapter
>     = new SimpleCursorAdapter(
>               this,                             // The Context for the ListView
>               R.layout.noteslist_item,          // Points to the XML for a list item
>               cursor,                           // The cursor to get items from
>               dataColumns,
>               viewIDs
>       );
> ```

完成以上工作，就可以完整的显示时间戳了。

剩下的搜索功能，在这个类中也能很快的实现。

在onCreateOptionsMenu中自定义initSearch方法，并传入menu。

用getActionView方法找到之前定义过actionViewClass的item，并设置监听事件。

onQueryTextSubmit是用户点击提交的时候查询；onQueryTextChange是只要输入有变化就能够查询。

这里我实现onQueryTextChange，不用点击查询，部分字符匹配就可以查询到。

为用户输入的newText重新定义selection，通过ContentResolver代理查询，并返回一个新的cursor。

把新的cursor交给adapter，再通知adapter数据改变了，重新显示信息。

``` java
public void initSearch(Menu menu){
        //menu item - search
        MenuItem mSearch = menu.findItem(R.id.menu_search);
        //get action view class
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("search ...");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    String selection = NotePad.Notes.COLUMN_NAME_TITLE+" GLOB '*"+newText+"*'";
                    searchCursor = getContentResolver().query(
                            getIntent().getData(),
                            PROJECTION,
                            selection,
                            null,
                            NotePad.Notes.DEFAULT_SORT_ORDER
                    );
                }

                //TODO
                adapter.swapCursor(searchCursor);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
```

#### 4.SecondActivity.xml

这是设置的activity文件,其实写的有点乱乱的. 因为遇到了一点问题.

我的设想是当switch组件checked以后, 用setTheme方法打开护眼模式(aka. dark mode), 

遇到了俩问题, 最后马马虎虎解决吧

1. 用自己写的主题文件(attr,xml + style.xml + 布局文件配合使用)跑不起来, 所以最后干脆直接升级了androidX, 用自带的主题..成功!

2. 重新开启之后页面并不会改变主题. 看了网上的分析, 要把setTheme放在setContentView方法之前执行,才能生效.

但是还是有遗留的问题, 当前页面生效了, 可是notelist页面还是不能生效, 这咋整呢


``` java
package com.example.android.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Switch modeSwitch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(sharedPreferences.getBoolean("nightMode", false)){
            setTheme(R.style.ThemeOverlay_MaterialComponents_Dark_ActionBar);
        }
        setContentView(R.layout.mode_setting);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        initModeSwitch();
    }

    void initModeSwitch(){
        modeSwitch = findViewById(R.id.mode_switch_switch);

        modeSwitch.setChecked(sharedPreferences.getBoolean("nightMode", false));
        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setNightModePreference(isChecked);
                setNightModeImmediately();
            }
        });
    }

    void setNightModePreference(boolean isChecked){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean b = sharedPreferences.getBoolean("nightMode", false);

        editor = sharedPreferences.edit();
        editor.putBoolean("nightMode", isChecked);
        Toast.makeText(getApplicationContext(), String.valueOf(b), Toast.LENGTH_LONG).show();
        editor.apply();
    }

    void setNightModeImmediately(){
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setTheme(R.style.ThemeOverlay_MaterialComponents_Dark_ActionBar);
        Intent intent = new Intent(this, this.getClass());
        startActivity(intent);
        finish();
    }
}

```

至此，所有的修改都完成了。

### Results

**时间戳展示**

![image-20200517145017520](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200517145017520.png)

**模糊查询**

![image-20200517145036611](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200517145036611.png)

**匹配唯一**

![image-20200517145048958](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200517145048958.png)

**设置页面**

![image-20200531040015006](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200531040015006.png)

**打开护眼模式**

![image-20200531040117306](https://github.com/sonettofighting/MobileApps/blob/master/MidTerm_App05_NotePad/README.assets/image-20200531040117306.png)