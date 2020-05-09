# Intent - 通过WebView定义内嵌浏览器

Author: [@sonettofighting](https://github.com/sonettofighting)

## 1. 获取URL地址并启动隐式Intent的调用

### 实验步骤

隐式调用intent，需要修改manifest.xml文件

实现浏览器功能，设置web页面的intent-filter的用户action为view（浏览），类别为可以上网，数据模式为https。如果是http，之后我的页面会无法显示。

```xml
        <activity android:name=".WebActivity" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE"/>
                <!--响应所有的http协议的Intent-->
                <data android:scheme="https"/>
            </intent-filter>
        </activity>
```

与此同时，在application标签之外，设置app的访问权限，否则可能会遇到不能上网的问题。

```xml
   <uses-permission android:name="android.permission.INTERNET" />
    <!-- 获取当前WiFi接入的状态以及WLAN热点的信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <!-- 获取网络信息状态，如当前的网络连接是否有效 -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Main Activity主要就是通过button调用intent，没啥好讲的。

```java
package com.example.asus.app04_webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBrowser();
    }

    boolean startBrowser() {
        mButton = findViewById(R.id.button);
        mEditText = findViewById(R.id.editText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://" + mEditText.getText().toString(); //不加上协议的话，manifest上的scheme无法对应
//                intent.putExtra("url", uwww.rl); //传递给WebView
                intent.setData(Uri.parse(url)); //data类型为http 对应scheme
                startActivity(intent);
            }
        });
        return true; //表示btn接收了动作
    }
}

```

MainActivity的页面布局文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:text="@string/browse"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/editText" />

    <EditText
        android:id="@+id/editText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:ems="15"
        android:hint="@string/inputUrl"
        android:inputType="textUri"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
```

### 实验结果

在代码中已经定义好https协议，这里输入网址

![图片加载中](https://github.com/sonettofighting/MobileApps/blob/master/APP04_WebView/images/1.png)

通过隐式intent，选择需要调用的app

![图片加载中](https://github.com/sonettofighting/MobileApps/blob/master/APP04_WebView/images/2-1.png)

## 2. 自定义WebView来加载URL

### 实验步骤

跳转的页面文件

1. 通过 getIntent, intent.getData获得前一个页面输入的数据

2. getSettings().setJavaScriptEnabled是为了页面浏览能够正常显示

3. 自定义了一个类myClient继承webViewClient,重写里面的方法，配合重写当前页面的onKeyDown方法，使得webView可以在内部进行页面跳转。

```java
package com.example.asus.app04_webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initBrowser(mWebView);
    }

    void initBrowser(WebView mWebView) {
        Intent intent = getIntent();
//        String url = intent.getStringExtra("url");
        Uri url = intent.getData();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(String.valueOf(url));
        mWebView.setWebViewClient(new myClient());
    }

    class myClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(String.valueOf(request.getUrl()));//跳转页面，在内部实现
            return super.shouldOverrideUrlLoading(view, request);
        }

//        @Override
//        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
//            return super.shouldOverrideKeyEvent(view, event);
//        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

```

web页面的布局文件

我加了一个toolbar，想让它看起来更像浏览器app一点。

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <android.support.v7.widget.Toolbar
        android:id="@+id/my_toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="#000"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
        app:title="@string/browserBar"
        app:titleTextColor="#FFF"/>

    <WebView
        android:id="@+id/webView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/my_toolbar"></WebView>

</android.support.constraint.ConstraintLayout>

```

### 实验结果

成功输入，实现浏览网页

![图片加载中](https://github.com/sonettofighting/MobileApps/blob/master/APP04_WebView/images/2-2.png)

点击浏览器其中的一个url，跳转到另一个页面

![图片加载中](https://github.com/sonettofighting/MobileApps/blob/master/APP04_WebView/images/2-3.png)

点击返回键，返回到上一个页面

![图片加载中](https://github.com/sonettofighting/MobileApps/blob/master/APP04_WebView/images/2-4.png)

------



以上就是intent实验的全部内容。