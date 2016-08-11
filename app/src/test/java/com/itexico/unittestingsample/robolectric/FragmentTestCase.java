package com.itexico.unittestingsample.robolectric;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.itexico.unittestingsample.LoginActivity;

import org.junit.After;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

/**
 * Created by iTexico Developer on 8/10/2016.
 */
public class FragmentTestCase<T extends Fragment> {

    private static final String FRAGMENT_TAG = "TestFragment";

    private ActivityController activityController;
    protected LoginActivity loginActivity;
    private T mFragment;

    /**
     * Adds the mFragment to a new blank loginActivity, thereby fully
     * initializing its view.
     */
    public void addFragment(T fragment) {
        System.out.println("addFragment");
        mFragment = fragment;
        activityController = Robolectric.buildActivity(LoginActivity.class);
        loginActivity = (LoginActivity) activityController.create().start().visible().get();
        FragmentManager manager = loginActivity.getSupportFragmentManager();
        manager.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
    }

    @After
    public void removeFragment() {
        System.out.println("removeFragment");
        if (mFragment != null) {
            FragmentManager manager = loginActivity.getSupportFragmentManager();
            manager.beginTransaction().remove(mFragment).commit();
        }
    }

    public void pauseAndResumeFragment() {
        System.out.println("pauseAndResumeFragment");
        activityController.pause().resume();
    }


}
