package com.itexico.unittestingsample.powermock;

import com.itexico.unittestingsample.mocktest.Service;
import com.itexico.unittestingsample.mocktest.ServiceListener;
import com.itexico.unittestingsample.mocktest.SystemUnderTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class PowerMockitoStaticMethodExample {
	private Service service;
	private SystemUnderTest system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		// Mock
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = new SystemUnderTest();
		//system = Mockito.spy(new SystemUnderTest());
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@Test
	public void stubStaticNonVoidMethod() {
		// Stub static method startServiceStatic to startService successfully
		p("Call mockStatic SystemUnderTest.class to enable static mocking");
		PowerMockito.mockStatic(SystemUnderTest.class);
		
		p("Stub static method startServiceStaticWay to return 1");
		PowerMockito.when(SystemUnderTest.startServiceStaticWay(service))
				.thenReturn(1);

		// Run
		p("Start the system, should startService the services in turn");
		system.start();

		// Verify success
		p("Verify using Mockito that service started successfuly");
		Mockito.verify(serviceListener).onSuccess(service);

		// Stub static method startServiceStatic to fail
		p("Stub static method startServiceStaticWay to return 0");
		PowerMockito.when(SystemUnderTest.startServiceStaticWay(service))
				.thenReturn(0);

		// Run
		p("Start the system again");
		system.start();

		// Verify failure
		p("Verify using Mockito that service has failed");
		Mockito.verify(serviceListener).onFailure(service);
	}

	private void p(String s) {
		System.out.println(s);
	}
}