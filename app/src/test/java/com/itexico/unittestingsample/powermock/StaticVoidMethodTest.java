package com.itexico.unittestingsample.powermock;

import com.itexico.unittestingsample.mocktest.Service;
import com.itexico.unittestingsample.mocktest.ServiceListener;
import com.itexico.unittestingsample.mocktest.SystemUnderTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class StaticVoidMethodTest {
	private Service service;
	private SystemUnderTest system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = new SystemUnderTest();
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@PrepareForTest({ SystemUnderTest.class })
	@Test
	public void stubStaticVoidMethod() {		
		p("Call mockStatic SystemUnderTest.class to enable static mocking");
		PowerMockito.mockStatic(SystemUnderTest.class);
		
		p("Stub static void method SystemUnderTest.notifyServiceListener to do nothing");
		PowerMockito.doNothing().when(SystemUnderTest.class);
		SystemUnderTest.notifyServiceListener(serviceListener, service, true);
		
		p("Stub using PowerMockito. service.startService() should return 1 as we want startService of the service to be successful");
		PowerMockito.when(service.startService()).thenReturn(1);

		p("Start the system");
		system.start();

		p("Verify static method startServiceStaticWay(service) is called");
		PowerMockito.verifyStatic();
		SystemUnderTest.startServiceStaticWay(service);

		p("Verify serviceListener.onSuccess(service) is not called as notifyServiceListener is stubbed to do nothing");
		Mockito.verify(serviceListener, Mockito.never()).onSuccess(service);
	}

	private void p(String s) {
		System.out.println(s);
	}
}