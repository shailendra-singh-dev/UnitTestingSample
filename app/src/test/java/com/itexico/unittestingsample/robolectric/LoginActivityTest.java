package com.itexico.unittestingsample.robolectric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;

import com.itexico.unittestingsample.BuildConfig;
import com.itexico.unittestingsample.LoginActivity;
import com.itexico.unittestingsample.R;
import com.itexico.unittestingsample.WelcomeActivity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

// Static imports for assertion methods

/**
 * Created by iTexico Developer on 8/9/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class LoginActivityTest {

    private LoginActivity loginActivity;
    private ActivityController activityController;

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run LoginActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activityController = Robolectric.buildActivity(LoginActivity.class);
        createWithIntent("setupextra");
    }

    @After
    public void tearDown() {
        loginActivity = null;
        activityController = null;
    }

    private void createWithIntent(String intentExtra) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Bundle extras = new Bundle();
        extras.putString("intentExtra", intentExtra);
        intent.putExtras(extras);
        loginActivity = (LoginActivity) activityController
                .withIntent(intent)
                .create()
                .start()
                .visible()
                .get();
    }

    @Test
    public void createsAndDestroysActivity() {
        activityController.destroy();
        // Assertions go here
    }

    @Test
    public void pausesAndResumesActivity() {
        activityController.pause().resume();
        // Assertions go here
        validateTextViewContent();
    }

    @Test
    public void recreatesActivity() {
        loginActivity.recreate();
        // Assertions go here
        validateTextViewContent();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(loginActivity);
    }

    // @Test => JUnit 4 annotation specifying this is a test to be run
    @Test
    public void validateTextViewContent() {
        Button saveButton = (Button) loginActivity.findViewById(R.id.saveButton);
        assertNotNull("Button could not be found", saveButton);
        assertTrue("TextView contains incorrect text",
                "Save".equals(saveButton.getText().toString()));
    }

    @Test
    public void secondActivityStartedOnClick() {
        loginActivity.findViewById(R.id.saveButton).performClick();
        // An Android "Activity" doesn't expose a way to find out about activities it launches
        // Robolectric's "ShadowActivity" keeps track of all launched activities and exposes this information
        // through the "getNextStartedActivity" method.
        ShadowActivity shadowActivity = Shadows.shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertEquals(WelcomeActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }


    @Test
    public void isLocalBroadcastReceiverRegistered() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(loginActivity);
        final Intent intent = new Intent("com.itexico.unittestingsample.intent.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
        Assert.assertEquals("BroadcastReceiver#onReceive Toast Called", ShadowToast.getTextOfLatestToast());
    }
}
