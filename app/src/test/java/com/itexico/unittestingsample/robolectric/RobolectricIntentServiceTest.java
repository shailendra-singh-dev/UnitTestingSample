package com.itexico.unittestingsample.robolectric;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.itexico.unittestingsample.BuildConfig;
import com.itexico.unittestingsample.service.RobolectricIntentService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowNotificationManager;
import org.robolectric.util.ServiceController;

/**
 * Created by iTexico Developer on 8/11/2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class RobolectricIntentServiceTest {

    static {
        ShadowLog.stream = System.out;
    }

    private ShadowApplication shadowApplication;
    private Context applicationContext;
    private ServiceController<RobolectricIntentServiceMock> intentServiceServiceController;
    private RobolectricIntentServiceMock robolectricIntentService;


    @Before
    public void setup() {
        shadowApplication = ShadowApplication.getInstance();
        applicationContext = shadowApplication.getApplicationContext();
//        intentServiceServiceController = Robolectric.buildService(RobolectricIntentServiceMock.class);
//        robolectricIntentService = intentServiceServiceController.attach().create().get();
    }

    @Test
    public void testServiceStarted() {
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentService.class);
        shadowApplication.startService(serviceIntent);
        final Intent peekNextStartedService = shadowApplication.peekNextStartedService();
        Assert.assertTrue("Service not yet started !!", peekNextStartedService.getComponent().getClassName().equalsIgnoreCase(RobolectricIntentService.class.getCanonicalName()));
    }

    @Test
    public void testPreferencesData() {
        Context applicationContext = RuntimeEnvironment.application.getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentService.class);
        shadowApplication.startService(serviceIntent);
        Assert.assertNotNull(sharedPreferences.getString("data", ""));
    }

    @Test
    public void testAddData(){
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentServiceMock.class);

        RobolectricIntentServiceMock robolectricIntentServiceMock = new RobolectricIntentServiceMock();
        robolectricIntentServiceMock.onCreate();
        robolectricIntentServiceMock.onHandleIntent(serviceIntent);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        Assert.assertNotNull(sharedPreferences.getString("data", ""));
    }

    @Test
    public void testServiceIntentNoBundle() {
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentServiceMock.class);
        NotificationManager notificationManager = (NotificationManager) RuntimeEnvironment.application.getSystemService(Context.NOTIFICATION_SERVICE);
//        shadowApplication.startService(serviceIntent);
        RobolectricIntentServiceMock robolectricIntentServiceMock = new RobolectricIntentServiceMock();
        robolectricIntentServiceMock.onCreate();
        robolectricIntentServiceMock.onHandleIntent(serviceIntent);

        Assert.assertEquals("Expected no notifications", 0, Shadows.shadowOf(notificationManager).size());
    }

    @Test
    public void testWithBundleExtrasFound() {
        Intent serviceIntent = new Intent(applicationContext, RobolectricIntentServiceMock.class);
        serviceIntent.putExtra("ACTION", "action_data");

        NotificationManager notificationManager = (NotificationManager) RuntimeEnvironment.application.getSystemService(Context.NOTIFICATION_SERVICE);
//        shadowApplication.startService(serviceIntent);
//        intentServiceServiceController.startCommand(0, 0);

        RobolectricIntentServiceMock robolectricIntentServiceMock = new RobolectricIntentServiceMock();
        robolectricIntentServiceMock.onCreate();
        robolectricIntentServiceMock.onHandleIntent(serviceIntent);

        final Intent peekNextStartedService = shadowApplication.peekNextStartedService();
        Assert.assertTrue("Service not yet started !!", peekNextStartedService.getComponent().getClassName().equalsIgnoreCase(RobolectricIntentService.class.getCanonicalName()));

        ShadowNotificationManager manager = Shadows.shadowOf(notificationManager);
        Assert.assertEquals("Expected one notification", 1, manager.size());
//
//        Notification notification = manager.getNotification(RobolectricIntentService.NOTIFICATION_TAG, RobolectricIntentService.NOTIFICATION_ID);
//        Assert.assertNotNull("Expected notification object", notification);
//
//        ShadowNotification shadowNotification = Shadows.shadowOf(notification);
//        Assert.assertNotNull("Expected shadow notification object", shadowNotification);
//        Assert.assertNotNull("Expected to have event information", shadowNotification.getContentInfo());

//        Assert.assertEquals("You are going to eat an apple", shadowNotification.getContentText());
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
