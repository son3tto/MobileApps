# Android界面布局

Anthor: [@sonettofighting](https://github.com/sonettofighting) 

This is an academic assginment.

## 任务一

### ![image-20200316212807201](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200316212807201.png)

### 实验思路

- 根linearlayout布局标签orientation设置为vertical，在其中分别套四个包含四个button的，orientation设置为horizontal的子linearlayout布局。不过套接过多的布局不利于实际运行。
- 第三行（第三个linearlayout）weight为1：1：1：1，其余行设置第二列button的weight为1，或width=“0”。
- 第一次学习layout的时候，要注意学会使用@引用values和id。
  - 第一次使用id，需要声明@+id/*id_name*，之后引用去掉+号即可。
  - 在res-values路径下，可以加入string、color等文件。
  - 通过改变每个linearlayout中的weight，可以调整布局内view的比例。
  - 把width/height修改成0dp，可以使view填充剩余空间。

```xml
<!--文件名 linear_layout.xml -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorBlack"
    android:orientation="vertical">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <!-- 父布局要改成match_parent 才可以实现权重的效果 -->
        <!-- 把width改成0dp 可达到同样效果 -->
        <!-- 后期字符串没有放入strings.xml，因为觉得好麻烦 -->
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/btn1"
    android:text="@string/btn1"/>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn2"
        android:layout_weight="1"
        android:text="@string/btn2"/>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn3"
        android:text="@string/btn3"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn4"
        android:text="@string/btn4"/>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn1"
            android:text="(2,1)"/>
        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:id="@id/btn2"
            android:layout_weight="1"
            android:text="(2,2)"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn3"
            android:text="(2,3)"/>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn4"
            android:text="(2,4)"/>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:id="@id/btn1"
            android:text="(3,1)"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn2"
            android:layout_weight="1"
            android:text="(3,2)"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:id="@id/btn3"
            android:text="(3,3)"/>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:id="@id/btn4"
            android:text="(3,4)"/>
    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn1"
            android:text="(4,1)"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn2"
            android:layout_weight="1"
            android:text="(4,2)"/>
        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn3"
            android:text="(4,3)"/>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@id/btn4"
            android:text="(4,4)"/>
    </LinearLayout>
</LinearLayout>

```

### 实验结果

![image-20200317074608720](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200317074608720.png)

## 任务二

### ![image-20200316212738895](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200316212738895.png)

#### 实验思路

其实constraintlayout通过design的方式直接使用我觉得就很简单了。但是具体的操作比较利于之后的深入学习。

|  控件  |                             位置                             |
| :----: | :----------------------------------------------------------: |
|  RED   | start位于父布局的start位置即：layout_constraint**Start**_**toStartOf**=**"parent"** 。top位于父布局的top，同理app:layout_constraintTop_toTopOf="parent"。 |
| ORANGE | start位于布局中间的上方，所以设置三个属性：start位于父布局start，top位于父布局top，end位于父布局end。 |
| YELLOW | end位于父布局的end，top位于父布局的top。两个坐标可以确定它的位置。 |
|  BLUE  | 这一行都水平居中，所以都要设置top和bottom位于父布局的top和bottom。BLUE位于父布局的正中间，很好拿来做相对位置，所以先设置它的end和start也位于父布局的end和start。 |
| GREEN  | 相对于隔壁控件BLUE的左边，设置GREEN's END is to the START of BLUE。并且margin end=大约20dp大小。 |
| INDOGO | INDIGO's START is to the END of BLUE. Margin START is about 20 dp. |
| VIOLET | VIOLET's END is to the BOTTOM of Parent Layout tag.选择width为match_parent直接占满整行。 |

- TextView中的gravity设置为center，文字内容居中。
- 如果想要每行文字对齐（,which is excatly the same as we've already achieve），可以设置*layout*_*constraintBaseline*_to*Baseline*Of 属性。
- background可以设置标签内部的背景颜色，text设置文字颜色。
- 颜色的属性可以通过values来定义，也可以直接@命名空间中的颜色。

```xml
<!-- constraint_layout.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="55dp"
        android:layout_height="55dp"
        android:background="@color/colorRed"
        android:text="Red"
        android:gravity="center"
        android:textColor="@color/colorBlack"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>

    <TextView
        android:layout_width="90dp"
        android:layout_height="55dp"
        android:id="@+id/textView2"
        android:text="ORANGE"
        android:textColor="@color/colorBlack"
        android:gravity="center"
        android:background="@android:color/holo_orange_dark"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        />
    <!--水平居中-->
    <TextView
        android:layout_width="90dp"
        android:layout_height="55dp"
        android:id="@+id/textView3"
        android:text="YELLOW"
        android:textColor="@color/colorBlack"
        android:gravity="center"
        android:background="@color/colorYellow"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        />
    <!-- blue 水平居中-->
    <TextView
        android:layout_width="55dp"
        android:layout_height="55dp"
        android:id="@+id/textView5"
        android:text="BLUE"
        android:textColor="@color/colorBlack"
        android:gravity="center"
        android:background="@android:color/holo_blue_light"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        />
    <TextView
        android:layout_width="90dp"
        android:layout_height="55dp"
        android:id="@+id/textView4"
        android:text="GREEN"
        android:textColor="@color/colorBlack"
        android:gravity="center"
        android:background="@color/colorGreen"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintEnd_toStartOf="@id/textView5"
        android:layout_marginEnd="20dp"
        android:layout_marginStart="20dp"
        app:layout_constraintBottom_toBottomOf="parent"
        />
    <TextView
        android:layout_width="90dp"
        android:layout_height="55dp"
        android:id="@+id/textView6"
        android:text="INDIGO"
        android:textColor="@color/colorBlack"
        android:gravity="center"
        android:background="@color/colorIndigo"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toEndOf="@id/textView5"
        android:layout_marginEnd="20dp"
        android:layout_marginStart="20dp"
        app:layout_constraintBottom_toBottomOf="parent"/>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:text="VIOLET"
        android:textColor="@color/colorBlack"
        android:background="@color/colorViolet"
        android:gravity="center"
        app:layout_constraintBottom_toBottomOf="parent"
        />
</androidx.constraintlayout.widget.ConstraintLayout>

```

#### 实验结果

![image-20200317075945985](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200317075945985.png)

## 任务三

![image-20200316213043323](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200316213043323.png)

#### 实验思路

- Table为一行三列的布局。
- 第一列只有“  ”和“×”，而且占比很小。做法是在根标签tablelayout中设置shrinkcolunmns=“0”，并在第一个tablerow的第一个textview设置text=“  ”。则接下来的几列就算没有内容也会留白。
- 第二列和第三列的weight都设置为1：1，才会足够大。将第三列控件中的gravity设置为right，文字实现右边对齐。
- 分割线的实现，我直接使用了在每个tablerow之间添加一个height为1dp的View。backgroundcolor设置为灰色，看起来就是分割线的效果了。
- 表格布局的控件不能指定width，默认是match_parent。但是可以定义height，默认为wrap_content，我就没写。tableRow的高度一直是wrap_content。

```xml
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:background="@android:color/background_dark"
    android:shrinkColumns="0"
    >
    <TableRow>
        <TextView
            android:id="@+id/blank"
            android:text="   "
            android:textColor="@color/colorGray" />
        <TextView
            android:id="@+id/text1"
            android:layout_weight="1"
            android:text="Open..."
            android:textColor="@color/colorGray" />

        <TextView
            android:id="@+id/text2"
            android:gravity="right"
            android:layout_weight="1"
            android:text="Ctrl - O"
            android:textColor="@color/colorGray" />
    </TableRow>
    <TableRow>
        <TextView
            android:id="@id/blank"
            android:text=""
            android:textColor="@color/colorGray" />
    <TextView
        android:id="@+id/text3"
        android:layout_weight="1"
        android:text="Save..."
        android:textColor="@color/colorGray" />

    <TextView
        android:id="@+id/text4"
        android:layout_weight="1"
        android:gravity="right"
        android:text="Ctrl - S"
        android:textColor="@color/colorGray" />
    </TableRow>
    <TableRow>
        <TextView
            android:id="@id/blank"
            android:text=""
            android:textColor="@color/colorGray" />
        <TextView
            android:id="@+id/text5"
            android:layout_weight="1"
            android:text="Save as..."
            android:textColor="@color/colorGray" />

        <TextView
            android:id="@id/text6"
            android:layout_weight="1"
            android:gravity="right"
            android:text="Ctrl - Shift - S"
            android:textColor="@color/colorGray"
        />
    </TableRow>

    <View android:id="@+id/divider"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/colorGray"
        ></View>

    <TableRow>
        <TextView
            android:id="@id/blank"
            android:text="×"
            android:textColor="@color/colorGray" />
        <TextView
            android:id="@id/text6"
            android:layout_weight="1"
            android:text="Import ..."
            android:textColor="@color/colorGray" />
    </TableRow>
    <TableRow>
        <TextView
            android:id="@id/blank"
            android:text="×"
            android:textColor="@color/colorGray" />
        <TextView
            android:id="@+id/text8"
            android:layout_weight="1"
            android:text="Emport ...."
            android:textColor="@color/colorGray" />

        <TextView
            android:id="@+id/text9"
            android:layout_weight="1"
            android:gravity="right"
            android:text="Ctrl - E"
            android:textColor="@color/colorGray"
            />
    </TableRow>
<View android:id="@id/divider"
    android:layout_width="match_parent"
    android:layout_height="1dp"
    android:background="@color/colorGray"
    />
    <!-- id不能直接引用? 好像只能在限制约束时候用到id-->
    <TableRow>
        <TextView
            android:id="@id/blank"
            android:text=""
            android:textColor="@color/colorGray" />
        <TextView
            android:id="@+id/text6"
            android:layout_weight="1"
            android:text="Quit"
            android:textColor="@color/colorGray" />


    </TableRow>
</TableLayout>
```

#### 实验结果

![image-20200317083425895](https://github.com/sonettofighting/MobileApps/blob/master/App2_Layout/README.assets/image-20200317083425895.png)