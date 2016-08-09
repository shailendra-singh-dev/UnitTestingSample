package com.itexico.unittestingsample.mocktest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class PowerMockitoIntegrationExample {
	private Service service;
	private SomeSystem system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		// Mock
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = Mockito.spy(new SomeSystem());
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@Test
	public void startSystem() {
		// Stub using Mockito and PowerMockito
		p("Stub using PowerMockito. service.start() should return 1 as we want start of the service to be successful");
		PowerMockito.when(service.start()).thenReturn(1);

		// Run
		p("Start the system, should start the services in turn");
		system.start();		

		// Verify using Mockito	
		p("Verify using Mockito that service started successfuly");
		Mockito.verify(serviceListener).onSuccess(service);
		
		p("Verifed. Service started successfully");
	}

	private void p(String s) {
		System.out.println(s);
	}
}