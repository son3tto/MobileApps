# **Android UI**组件

Author: [@sonettofightuing](https://github.com/sonettofighting)

This is an academic assignment.

[TOC]



## 写在前头

因为其他科目的作业实在太多了，忙到现在才有空写文档。

前两题感觉可能写了有几个星期了，现在回头看一下自己写的代码，感觉可以写的更简洁一些。但是时间有限，之后多多加油继续完善吧。

但是最后一题是前面才写完的。因为，之前没有理解adapter的概念，不习惯写重载方法。读代码容易，写起来还真不是那回事儿。

## 1. ListView的用法

### （1）实现页面布局

chatlist.xml 里面只有一个ListView，用于之后绑定adapter显示数据。

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/ListView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:dividerHeight="1dp"
        />
</android.support.constraint.ConstraintLayout>

```

chatitems.xml 是列表项内的布局。感觉这里用相对布局和约束布局都差不多。按照实验要求，我用的是约束布局。

放了一个ImageView和一个TextView。在ListView绑定adapter时，会用到这个文件作为参数。

```XML
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">
<ImageView
    android:layout_width="50dp"
    android:layout_height="50dp"
    android:id="@+id/header"
    app:layout_constraintEnd_toEndOf="parent"
    android:paddingEnd="10dp"
    android:gravity="center"
    />
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/name"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    android:paddingStart="10dp"
    android:paddingTop="10dp"
    android:textSize="20sp"
    android:textColor="#000"
    android:gravity="center"
    />

</android.support.constraint.ConstraintLayout>

```



### （2）创建Activity文件

ChatActivity.java 主要做几件事情：

1. 定义绑定的资源，并把资源存入map中。

   这里包括头像、姓名。现在看，感觉可以更简洁一些，把头像和姓名两个属性放到一个bean里面。

2. 为ListView绑定adapter。注意其中的几个参数分别是：上下文、map、每条数据的布局、keyset、根据key的顺序，指定放入的布局中的组件ID。

3. 为ListView设置监听事件。当点击item的时候，根据item的positon弹出toast，显示不同的item名称。

```JAVA
package com.example.asus.app3_02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    ListView listView;
    SimpleAdapter simpleAdapter;

    //名字、头像资源
    String[] names = new String[]{"Lion", "Tiger", "Cat", "Monkey", "Dog", "Elephant"};
    int[] imgids = new int[]{R.drawable.lion, R.drawable.tiger, R.drawable.cat,
            R.drawable.monkey, R.drawable.dog, R.drawable.elephant};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        //map的list 存放每一条数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("header", imgids[i]);
            map.put("name", names[i]);
            list.add(map);
        }
        //设置adapter
        //apdater参数: 上下文， 实现map的数据list， 每一条数据的布局， map的key， 放入的布局的指定组件id
        simpleAdapter = new SimpleAdapter(this, list, R.layout.chatitems,
                new String[]{"header", "name"}, new int[]{R.id.header, R.id.name});
        listView = findViewById(R.id.ListView);
        //将adapter和listview绑定
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), names[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

}

```

### 结果

* 界面

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/1-2.png)

* 点击item，出现Toast

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/1-3.png)

## 2. 自定义布局的AlertDialog 配合intent跳转到题1页面

#### 这题我结合了第1题和第3题的布局文件一起做！！！

### （1）dialog布局

login.xml 我用了4个组件，从上到下分别是：黄色的view作为标题的底色、两个文本框和一个TextView作为标题。因为约束布局比较灵活，所以可以把TextView和view结合做到这样的效果。

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <View
        android:id="@+id/appname"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@android:color/holo_orange_light"
        android:contentDescription="@string/app_name"
        app:layout_constraintBottom_toTopOf="@id/usrname"
        />

    <EditText
        android:id="@+id/usrname"
        android:inputType="text"
        android:maxLength="15"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:hint="@string/usrname"
        app:layout_constraintTop_toTopOf="@id/appname"
        />

    <EditText
        android:id="@+id/passwd"
        android:maxLength="15"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:ems="10"
        android:inputType="textPassword"
        android:hint="@string/passwd"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/usrname"
        />

    <TextView
        android:id="@+id/appname1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:text="ANDROID APP"
        android:textSize="25dp"
        android:textColor="#fff"
        android:fontFamily="monospace"
        app:layout_constraintBottom_toTopOf="@id/usrname"
        app:layout_constraintEnd_toEndOf="@id/appname"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/appname" />
</android.support.constraint.ConstraintLayout>

```

activity_main.xml 作为测试页面。实现点击按钮，弹出dialog的效果。

*之后的菜单用的也是这个页面*

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="34dp"
        android:id="@+id/text"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/loginbtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="148dp"
        android:layout_marginEnd="148dp"
        android:layout_marginBottom="197dp"
        android:text="@string/click_here_to_login"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</android.support.constraint.ConstraintLayout>
```

### （2）Activity文件

MainActivity.java

自定义两个方法：initLogin()和LoginDialog()。

initLogin()实现点击按钮，调用LoginDialog()方法。LoginDialog()使用LayoutInflater创建前面的页面布局文件。

在AlertDialog.builder使用setView方法将其与inflater创建的布局文件配置，并用一系列set方法设置属性。值得注意的是，每次set方法调用返回的都是一个AlertDialog.Builder对象，所以可以在一行代码中连续地进行set操作。

我重写了setNegativeButton方法，当点击NegativeButton时，弹出toast。又尝试在setPositiveButton方法中，使用匿名内部类，实现用Intent进行页面跳转。

顺便提一下，用Intent要在AndroidManifest.xml中配置Activity以及其中的intent-filter。

```JAVA
package com.example.asus.app3_02;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLogin();
    }

    private void initLogin() {
        findViewById(R.id.loginbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginDialog();
                    }
                }
        );
    }

    private void LoginDialog() {
        //打开login布局
        LayoutInflater layoutInflater = getLayoutInflater();
        View login = layoutInflater.inflate(R.layout.login, null);
        //设置dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(login)
                .setIcon(R.drawable.lemon)
                .setMessage(R.string.verify);
        //重写一个方法
        setNegativeButton(builder);
        //直接在函数里面set 不重写 顺便点击确认的时候可以跳转到下一个界面
        builder.setPositiveButton(R.string.sign_in, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        })
                .create()
                .show();
    }

    //重写的setNegativeButton方法
    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
            }
        });
    }
//下面这部分是下一题的代码
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//        item.setIcon(R.drawable.ic_launcher_foreground);
        TextView text = findViewById(R.id.text);
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.menu_item1:
                Toast.makeText(getApplicationContext(), R.string.menu_item, Toast.LENGTH_SHORT).show();
                break;
            case R.id.font_big:
                text.setTextSize(Float.parseFloat(String.valueOf(30)));
                break;
            case R.id.font_medium:
                text.setTextSize(20);
                break;
            case R.id.font_small:
                text.setTextSize(10);
                break;
            case R.id.font_red:
                text.setTextColor(Color.RED);
                break;
            case R.id.font_black:
                text.setTextColor(Color.BLACK);
                break;
        }
        return true;
    }
}


```



### 结果

* 界面

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/2-1.png)

* 点击button

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/2-2.png)

* 点击sign in

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/2-3.png)

## 3. 使用XML定义菜单

### (1)页面布局文件

option_menu.xml 放在res/menu中。感觉没啥好说的，就是菜单group、item，以及item里面可以继续套menu和item。

直接在XML文件里面同时设置icon和title是不能同时显示的，需要在JAVA代码里面调用method，set....才行。

```XML
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >
    <group
        android:id="@+id/menu_group">
        <item
            android:id="@+id/menu_item1"
            android:title="@string/menu_item"
            app:showAsAction="never" />
        <item
            android:id="@+id/menu_item2"
            android:visible="true"
            app:showAsAction="never"
            android:title="@string/font_size">
            <menu>
                <item
                    android:id="@+id/font_big"
                    android:title="@string/big" />
                <item
                    android:id="@+id/font_medium"
                    android:title="@string/medium" />
                <item
                    android:id="@+id/font_small"
                    android:title="@string/small" />
            </menu>
        </item>
        <!-- 设置了icon就没办法显示文本 -->
        <item
            android:id="@+id/menu_item3"
            android:icon="@drawable/lemon"
            android:title="@string/color">
            <menu>
                <item
                    android:id="@+id/font_red"
                    android:title="@string/red" />
                <item
                    android:id="@+id/font_black"
                    android:title="@string/black" />
            </menu>
        </item>
    </group>
</menu>

```

用到的页面就是上题的Actvity_main.xml，不再赘述。

### （2）代码实现

Activity_main.java 

onCreateOptionsMenu和onCreate方法都是在页面被launch的时候调用的方法。所以在onCreateOptionsMenu里面调用MenuInflater生成前面的menu布局的menu。

重写onOptionsItemSelected文件，】根据点击不同item的id进行switch匹配，设置不同的属性实现。

```JAVA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//        item.setIcon(R.drawable.ic_launcher_foreground);
        TextView text = findViewById(R.id.text);
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.menu_item1:
                Toast.makeText(getApplicationContext(), R.string.menu_item, Toast.LENGTH_SHORT).show();
                break;
            case R.id.font_big:
                text.setTextSize(Float.parseFloat(String.valueOf(30)));
                break;
            case R.id.font_medium:
                text.setTextSize(20);
                break;
            case R.id.font_small:
                text.setTextSize(10);
                break;
            case R.id.font_red:
                text.setTextColor(Color.RED);
                break;
            case R.id.font_black:
                text.setTextColor(Color.BLACK);
                break;
        }
        return true;
    }
}

```

### 结果

*结果太多*，*不一一列举*。我觉得我应该学一下怎么截动图。

* 主菜单

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/main_menu.png)

* 点击第一项“普通菜单项”，出现toast

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/menu_toast.png)

* 点击第二项“字体大小”，修改为大

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/font_big.png)

* 点击第三项“字体颜色”，分别修改为红色、黑色

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/font_red.png)

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/font_black.png)

## 4. 创建上下文操作模式ActionMode的上下文菜单

### （1）创建布局文件

题中用到的是列表的形式，所以还是需要一个ListView的布局。和第一题一样，好像是因为divider的颜色不一样，我有点强迫症，所以创建了两个。

second_list.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
<ListView
    android:id="@+id/second_list"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:divider="@android:color/darker_gray"
    android:dividerHeight="1dp"
    ></ListView>
</android.support.constraint.ConstraintLayout>
```

页面内部的布局，我用的是LinearLayout。纠结过很久，其实现在看来是没啥关系，刚开始做的时候没懂adapter的概念，不晓得可以自己重写一个MyAdapter这样的东西，就在究竟是用ArrayAdapter还是用SimpleAdapter上纠结了好久。所以这里才用的是LinearLayout（参考了老师GitHub上很久远的代码）。

second_item.xml 放了图片和数据。图片都是同一张。

```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/LinerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">

    <ImageView
        android:id="@+id/second_img"
        android:layout_width="90dp"
        android:layout_height="90dp"
        android:src="@drawable/star5"
        />
    <TextView
        android:id="@+id/second_user"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="top"
        android:textSize="20sp"
        android:textColor="@android:color/black"
        />
</LinearLayout>
```

### （2）Activity文件

做到这里卡住了，因为稍微开始有一些复杂了起来，必须要知道里面的逻辑才能继续做下去。

我原先纠结的问题是：如何在action mode下删除item呢？

哦！原来要从adapter下手！把与adapter配套的第选中条的data删掉！再通知adapter数据更新了！

总结一下几个注意的地方：

1. listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL)

   设置多选模式

2. onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)

   在进入action mode之后，点击item的操作。checked表示是否选择。

3. onActionItemClicked(ActionMode mode, final MenuItem item)

   设计点击菜单中的item（比如：button）之后要做的事情。这里是删除。

4. 重写adapter！！！！！

   重写adapter继承了baseAdapter接口。创建一个boolean类型数组itemState，同步action mode的onItemCheckedStateChanged方法checked参数。就可以获取到其状态，之后进行删除的操作。

5. 重写adapter的getView方法，设置点击之后会变色。再点击一次变回原来的白色。

SecondActivity.java

```JAVA
package com.example.asus.app3_02;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.asus.app3_02.R.color.design_default_color_primary;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    //    public List<user> userList = new ArrayList<>();
    //初始化ArrayList的方法👇
    List<String> names = new ArrayList<String>() {
        {
            this.add("one");
            add("two");
            add("three");
            add("four");
            add("five");
            add("six");
            add("seven");
            add("eight");
            add("nine");
            add("ten");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_list);
        listView = findViewById(R.id.second_list);
        //上下文 行间布局 text的id 数据
        final SecondAdapter mAdapter = new SecondAdapter(getApplicationContext(), names);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            //记录几个选中
            private int selected = 0;

            @Override
            //list item操作: 当点击列表项的时候判断
            //判断 checked/取消checked 则 更新adapter
            //因为可能有取消的操作，所以目前不能删除listView中的数据，把结果暂时存在mAdapter的list中
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    //adapter变动
                    mAdapter.getItemState()[position] = true;
                    selected++;
                } else {
                    selected--;
                    mAdapter.getItemState()[position] = false;
                }
                if (selected <= 0) {
                    mode.finish();
                }
                mode.setTitle(selected + " selected");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            //按钮操作 进行删除
            public boolean onActionItemClicked(ActionMode mode, final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.am_test1:
                        mAdapter.deleteSelectedItems();
                        mAdapter.notifyDataSetChanged();
                        //                        selected=0;
//                        //下次进入则是新的map
//                        mAdapter.deleteSelection();
//                        mode.finish();
                        selected = 0;
                        mode.setTitle("successfully deleted!");
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mode.finish();
            }
        });

    }

    private class SecondAdapter extends BaseAdapter {
        private Context context;
        private List<String> dataList;
        //存放点击的状态
        private boolean[] itemState;

        public SecondAdapter(Context context, List<String> dataList) {
            this.context = context;
            this.dataList = dataList;
            itemState = new boolean[dataList.size()];
            for (int i = 0; i < itemState.length; i++) {
                itemState[i] = false;
            }
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.second_items, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.second_user);
            textView.setText(dataList.get(position));
            if (getItemState()[position] == true) {
                convertView.setBackgroundColor(getResources().getColor(design_default_color_primary));
            } else {
                //如果再get一次 那就是白色
                convertView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
            }
            return convertView;
        }


        public int countSelectedItems() {
            int count = 0;
            for (boolean i : itemState) {
                if (i == true) {
                    count++;
                }
            }
            return count;
        }

        public boolean[] getItemState() {
            return itemState;
        }

        public void deleteSelectedItems() {
            for (int i = 0; i < itemState.length; i++) {
                if (itemState[i] == true) {
                    dataList.remove(i);
                }
            }
//            notifyDataSetChanged();
            itemState = new boolean[dataList.size()];
            for (boolean i : itemState) {
                i = false;
            }
        }

    }


}
```



### 结果

* 界面

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/3-1.png)

* 长按进入Action Mode，并选择items

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/3-2.png)

* 点击右上角确定，删除item

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/3-3.png)

## 5. 附加: RecyclerView的实现

这是自学部分的内容。本来打算用RecyclerView实现action mode的，没有采用。但是写都写了，不传上来感觉有点可惜的嘛。食之无味，弃之可惜！（也没有）

加深了我对这些组件的理解！

RecyclerView要用到ViewHolder，这个神奇的东西把每一条item的view存起来，回收、调用、回收、调用、回收……

```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
<!--
choiceMode="multipleChoiceModal"可以在java里面写
 -->
    <android.support.v7.widget.RecyclerView
        android:id="@+id/my_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scrollbars="vertical" />
</LinearLayout>

```

```
package com.example.asus.app3_02;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.app3_02.domain.user;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(myRecyclerViewAdapter);

    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        private List<user> userList = new ArrayList<>();

        public MyRecyclerViewAdapter() {
            userList = getUserData();
        }

        private List<user> getUserData() {
            String[] names = new String[]{"One", "Two", "Three", "Four",
                    "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
            for (int i = 0; i < names.length; i++) {
                user u = new user(names[i], R.drawable.ic_launcher_foreground);
                userList.add(u);
            }
            return userList;
        }

        @NonNull
        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.second_items,
                    viewGroup, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override //赋值
        public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder viewHolder, int position) {
            user user = userList.get(position);
            viewHolder.mText.setText(user.getUserName());
            viewHolder.mImage.setImageResource(user.getImgId());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mText;
            ImageView mImage;

            ViewHolder(View itemView) {
                super(itemView);
                mText = itemView.findViewById(R.id.second_user);
                mImage = itemView.findViewById(R.id.second_img);
            }
        }


    }
/*
    private class MyCallback implements ActionMode.Callback{
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.am_test1:

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
*/

}

```



### 结果

* 界面

![IMG](https://github.com/sonettofighting/MobileApps/blob/master/APP3_02/imgs/recycler.png)