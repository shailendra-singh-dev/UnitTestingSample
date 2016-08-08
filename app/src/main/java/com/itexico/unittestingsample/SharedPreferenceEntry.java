package com.itexico.unittestingsample;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
public class SharedPreferenceEntry {
    // Email address of the user.
    private final String mEmail;
    private final String mPassword;

    public SharedPreferenceEntry(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }
}
