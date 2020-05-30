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
