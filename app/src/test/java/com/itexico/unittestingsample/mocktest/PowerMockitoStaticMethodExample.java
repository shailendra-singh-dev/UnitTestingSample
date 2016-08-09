package com.itexico.unittestingsample.mocktest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class PowerMockitoStaticMethodExample {
	private Service service;
	private SomeSystem system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		// Mock
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = new SomeSystem();
		//system = Mockito.spy(new SomeSystem());
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@Test
	public void stubStaticNonVoidMethod() {
		// Stub static method startServiceStatic to start successfully
		p("Call mockStatic SomeSystem.class to enable static mocking");
		PowerMockito.mockStatic(SomeSystem.class);
		
		p("Stub static method startServiceStaticWay to return 1");
		PowerMockito.when(SomeSystem.startServiceStaticWay(service))
				.thenReturn(1);

		// Run
		p("Start the system, should start the services in turn");
		system.start();

		// Verify success
		p("Verify using Mockito that service started successfuly");
		Mockito.verify(serviceListener).onSuccess(service);

		// Stub static method startServiceStatic to fail
		p("Stub static method startServiceStaticWay to return 0");
		PowerMockito.when(SomeSystem.startServiceStaticWay(service))
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