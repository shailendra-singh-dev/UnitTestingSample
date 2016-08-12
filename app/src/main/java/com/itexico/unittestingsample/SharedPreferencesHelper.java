package com.itexico.unittestingsample;

import android.content.SharedPreferences;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
public class SharedPreferencesHelper {
    // Keys for saving values in SharedPreferences.
    public static final String KEY_EMAIL = "key_email";
    public static final String KEY_PASSWORD = "key_password";

    // The injected SharedPreferences implementation to use for persistence.
    private final SharedPreferences mSharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The {@link SharedPreferences} that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * Saves the given {@link SharedPreferenceEntry} that contains the user's settings to
     * {@link SharedPreferences}.
     *
     * @param sharedPreferenceEntry contains data to save to {@link SharedPreferences}.
     * @return @{code true} if writing to {@link SharedPreferences} succeeded. @{code false}
     * otherwise.
     */
    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry) {
        // Start a SharedPreferences transaction.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail());
        editor.putString(KEY_PASSWORD, sharedPreferenceEntry.getPassword());
        // Commit changes to SharedPreferences.
        return editor.commit();
    }

    /**
     * Retrieves the {@link SharedPreferenceEntry} containing the user's personal information from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link SharedPreferenceEntry}.
     */
    public SharedPreferenceEntry getPersonalInfo() {
        // Get data from the SharedPreferences.
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        String password = mSharedPreferences.getString(KEY_PASSWORD, "");

        // Create and fill a SharedPreferenceEntry model object.
        return new SharedPreferenceEntry(email, password);
    }


}
