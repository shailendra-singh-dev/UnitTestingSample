package com.itexico.unittestingsample.robolectric;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.itexico.unittestingsample.BuildConfig;
import com.itexico.unittestingsample.service.RobolectricIntentService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by iTexico Developer on 8/11/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class RobolectricIntentServiceTest {

    static {
        ShadowLog.stream = System.out;
    }

    private ShadowApplication shadowApplication;
    private Context applicationContext;

    @Before
    public void setup() {
        shadowApplication = ShadowApplication.getInstance();
        applicationContext = shadowApplication.getApplicationContext();
    }

    @Test
    public void testServiceStarted() {
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentService.class);
        shadowApplication.startService(serviceIntent);
        final Intent peekNextStartedService = shadowApplication.peekNextStartedService();
        Assert.assertTrue("Service not yet started !!", peekNextStartedService.getComponent().getClassName().equalsIgnoreCase(RobolectricIntentService.class.getCanonicalName()));
    }

    //Mocking service class since onHandleIntent() doesn't get called if we use shadowApplication.startService(serviceIntent).
    // When we call startService() ,onStartCommand associated Handler invokes background thread. then only onHandleIntent() can be called.
    @Test
    public void testOnHandleIntent() {
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentServiceMock.class);
        serviceIntent.putExtra("ACTION", "action_data");

        RobolectricIntentServiceMock robolectricIntentServiceMock = new RobolectricIntentServiceMock();
        robolectricIntentServiceMock.onCreate();
        robolectricIntentServiceMock.onHandleIntent(serviceIntent);

    }

    private class RobolectricIntentServiceMock extends RobolectricIntentService {

        public RobolectricIntentServiceMock() {
            super("RobolectricIntentServiceMock");
        }

        @Override
        public void onHandleIntent(Intent intent) {
            super.onHandleIntent(intent);
        }
    }
}
