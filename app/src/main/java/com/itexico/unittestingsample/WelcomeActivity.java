package com.itexico.unittestingsample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by iTexico Developer on 8/10/2016.
 */
public class WelcomeActivity extends AppCompatActivity {
    private TextView mEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mEmailText = (TextView) findViewById(R.id.user_email);
        populateUi();
    }

    private void populateUi() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String userEmail =  sharedPreferences.getString(SharedPreferencesHelper.KEY_EMAIL,"");
        mEmailText.setText(userEmail);
    }


}
