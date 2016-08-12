package com.itexico.unittestingsample.robolectric;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;

import com.itexico.unittestingsample.BuildConfig;
import com.itexico.unittestingsample.receiver.RobolectricBroadcastReceiver;
import com.itexico.unittestingsample.service.RobolectricIntentService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

/**
 * Created by iTexico Developer on 8/11/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class RobolectricBroadcastReceiverTest {
    private static final String ACTION = "com.itexico.unittestingsample.intent.action.UNIT_TEST";

    static {
        // redirect the Log.x output to stdout. Stdout will be recorded in the test result report
        ShadowLog.stream = System.out;
    }

    private ShadowApplication shadowApplication;
    private Intent intent;

    @Before
    public void setUp() {
        shadowApplication = ShadowApplication.getInstance();
        intent = new Intent(ACTION);
    }

    @Test
    public void testBroadcastReceiverRegistered() {
        List<ShadowApplication.Wrapper> registeredReceivers = shadowApplication.getRegisteredReceivers();
        Assert.assertFalse(registeredReceivers.isEmpty());

        boolean receiverFound = false;
        for (final ShadowApplication.Wrapper wrapper : registeredReceivers) {
            if (wrapper.broadcastReceiver.getClass().getSimpleName().equalsIgnoreCase(RobolectricBroadcastReceiver.class.getSimpleName())) {
                receiverFound = true;
            }
        }
        Assert.assertTrue(receiverFound);
    }


    /*
    * We defined the Broadcast receiver with a certain action, so we should check if we have
         receivers listening to the defined action*/
    @Test
    public void testReceiverForIntent() {
        Assert.assertTrue(shadowApplication.hasReceiverForIntent(intent));
    }

    /*
    * Lets be sure that we only have a single receiver assigned for this intent
    * */
    @Test
    public void testReceiverListSizeForIntent() {
        List<BroadcastReceiver> broadcastReceiverList = shadowApplication.getReceiversForIntent(intent);
        Assert.assertTrue("Expected only one receiver !!!", broadcastReceiverList.size() == 1);
    }

    /*
    * Fetch the Broadcast receiver and cast it to the correct class.
      Next call the "onReceive" method and check if the MyBroadcastIntentService was started
    * */
    @Test
    public void testOnReceiveCall() {
        List<BroadcastReceiver> broadcastReceiverList = shadowApplication.getReceiversForIntent(intent);
        RobolectricBroadcastReceiver robolectricBroadcastReceiver = (RobolectricBroadcastReceiver) broadcastReceiverList.get(0);
        robolectricBroadcastReceiver.onReceive(shadowApplication.getApplicationContext(), intent);

        final Intent serviceIntent = shadowApplication.peekNextStartedService();
        Assert.assertTrue("Service not yet started !!", serviceIntent.getComponent().getClassName().equalsIgnoreCase(RobolectricIntentService.class.getCanonicalName()));
        Assert.assertTrue("Service intent extras doesn't match !!!", serviceIntent.getStringExtra("data").equalsIgnoreCase("receiver_data"));

    }


}
