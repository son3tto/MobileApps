package com.example.asus.app3_02;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.app3_02.domain.user;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import static com.example.asus.app3_02.R.color.design_default_color_primary;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
//    public List<user> userList = new ArrayList<>();
    List<String> names=new ArrayList<String>(){
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
                if(checked){
                    //adapter变动
                    mAdapter.getItemState()[position] = true;
                    selected++;
                }else{
                    selected--;
                    mAdapter.getItemState()[position] = false;
                }
                if(selected<=0){
                    mode.finish();
                }
                mode.setTitle(selected+" selected");
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
            for(int i=0;i<itemState.length;i++){
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
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.second_items,  parent, false);
            }
            TextView textView = convertView.findViewById(R.id.second_user);
            textView.setText(dataList.get(position));
            if(getItemState()[position]==true){
                convertView.setBackgroundColor(getResources().getColor(design_default_color_primary));
            }else{
                //如果再get一次 那就是白色
                convertView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
            }
            return convertView;
        }


        public int countSelectedItems(){
            int count=0;
            for(boolean i:itemState){
                if(i==true){
                    count++;
                }
            }
            return count;
        }
        public boolean[] getItemState(){
            return itemState;
        }

        public void deleteSelectedItems(){
            for(int i=0;i<itemState.length;i++){
                if(itemState[i]==true){
                    dataList.remove(i);
                }
            }
//            notifyDataSetChanged();
            itemState = new  boolean[dataList.size()];
            for(boolean i:itemState){
                i=false;
            }
        }

    }


}