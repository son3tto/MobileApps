package com.example.asus.app3_02;

import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.asus.app3_02.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{
        private List<user> userList = new ArrayList<>();
        private List<user> getUserData(){
            String[] names=new String[]{"One", "Two", "Three", "Four",
                    "Five", "Six", "Seven", "Eight","Nine","Ten"};
            for(int i=0;i<names.length;i++){
                user u = new user(names[i], R.drawable.ic_launcher_foreground);
                userList.add(u);
            }
            return userList;
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
        public MyRecyclerViewAdapter(){
            userList = getUserData();
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
