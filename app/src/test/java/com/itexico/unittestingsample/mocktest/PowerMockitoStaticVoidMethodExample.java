package com.itexico.unittestingsample.mocktest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class PowerMockitoStaticVoidMethodExample {
	private Service service;
	private SomeSystem system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = new SomeSystem();
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@PrepareForTest({ SomeSystem.class })
	@Test
	public void stubStaticVoidMethod() {		
		p("Call mockStatic SomeSystem.class to enable static mocking");
		PowerMockito.mockStatic(SomeSystem.class);
		
		p("Stub static void method SomeSystem.notifyServiceListener to do nothing");
		PowerMockito.doNothing().when(SomeSystem.class);
		SomeSystem.notifyServiceListener(serviceListener, service, true);
		
		p("Stub using PowerMockito. service.start() should return 1 as we want start of the service to be successful");
		PowerMockito.when(service.start()).thenReturn(1);		

		p("Start the system");
		system.start();

		p("Verify static method startServiceStaticWay(service) is called");
		PowerMockito.verifyStatic();
		SomeSystem.startServiceStaticWay(service);

		p("Verify serviceListener.onSuccess(service) is not called as notifyServiceListener is stubbed to do nothing");
		Mockito.verify(serviceListener, Mockito.never()).onSuccess(service);
	}

	private void p(String s) {
		System.out.println(s);
	}
}