package com.example.asus.app3_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String[] names=new String[]{"Lion", "Tiger", "Cat", "Monkey", "Dog", "Elephant"};
    int[] imgids = new int[]{R.drawable.lion, R.drawable.tiger, R.drawable.cat,
            R.drawable.monkey, R.drawable.dog, R.drawable.elephant};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        //map的list 存放每一条数据
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0; i<names.length; i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("header", imgids[i]);
            map.put("name",names[i]);
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
