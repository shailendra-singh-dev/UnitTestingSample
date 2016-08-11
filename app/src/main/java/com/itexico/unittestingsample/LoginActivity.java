package com.itexico.unittestingsample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    // The validator for the email input field.
    private EmailValidator mEmailValidator;
    private PasswordValidator mPasswordValidator;
    private EditText mEmailText;
    // The helper that manages writing to SharedPreferences.
    private SharedPreferencesHelper mSharedPreferencesHelper;
    private EditText mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailText = (EditText) findViewById(R.id.emailInput);
        mPasswordText = (EditText) findViewById(R.id.passwordInput);
        // Setup field validators.
        mEmailValidator = new EmailValidator();
        mEmailText.addTextChangedListener(mEmailValidator);

        mPasswordValidator = new PasswordValidator();
        mPasswordText.addTextChangedListener(mPasswordValidator);

        final Button save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(this);

        // Instantiate a SharedPreferencesHelper.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        populateUi();
    }

    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        mEmailText.setText(sharedPreferenceEntry.getEmail());
        mPasswordText.setText(sharedPreferenceEntry.getPassword());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                onSaveClick();
                break;
        }
    }

    /**
     * Called when the "Save" button is clicked.
     */
    public void onSaveClick() {
        //UnComment for LoginActivityTest secondActivityStartedOnClick
//        Intent intent = new Intent(this, WelcomeActivity.class);
//        startActivity(intent);

        // Don't save if the fields do not validate.
        if (!mEmailValidator.isValid()) {
            mEmailText.setError("Invalid email");
            Log.w(TAG, "Not saving personal information: Invalid email");
            return;
        }
        if (!mPasswordValidator.isValid()) {
            mPasswordText.setError("Invalid Password. Minimum password length is 8.It can have numeral,alphabates.");
            Log.w(TAG, "Not saving personal information: Invalid Password");
            return;
        }

        // Get the text from the input fields.
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        // Create a Setting model class to persist.
        SharedPreferenceEntry sharedPreferenceEntry =
                new SharedPreferenceEntry(email, password);

        // Persist the personal information.
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if (isSuccess) {
            Toast.makeText(this, "Personal information saved", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Personal information saved");
        } else {
            Log.e(TAG, "Failed to write personal information to SharedPreferences");
        }
        if (isSuccess) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

}
