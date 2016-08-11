package com.itexico.unittestingsample.robolectric;

import android.os.Build;

import com.itexico.unittestingsample.BuildConfig;
import com.itexico.unittestingsample.fragment.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by iTexico Developer on 8/10/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class FragmentTest extends FragmentTestCase<LoginFragment>{

    private LoginFragment fragment;

    @Before
    public void setUp() {
        System.out.println("setUp");
        fragment = new LoginFragment();
    }

    @Test
    public void createsAndDestroysFragment() {
        System.out.println("Test createsAndDestroysFragment");
        addFragment(fragment);
        // Assertions go here
    }

    @Test
    public void pausesAndResumesFragment() {
        System.out.println("Test pausesAndResumesFragment");
        addFragment(fragment);
        pauseAndResumeFragment();
        // Assertions go here
    }

}
