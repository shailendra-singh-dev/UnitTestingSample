package com.itexico.unittestingsample.mocktest;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class PowerMockitoVerifyPrivateMethodExample {
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
	public void verifyPrivateMethods() throws Exception {
		p("Stub using PowerMockito. service.start() should return 1 as we want start of the service to be successful");
		PowerMockito.when(service.start()).thenReturn(1);
		
		p("Stub service name to return serviceA");
		Mockito.when(service.getName()).thenReturn("serviceA");

		p("Start the system, should start the services in turn");
		system.start();

		p("Verify private method addEvent(service, true) is called");
		PowerMockito.verifyPrivate(system).invoke("addEvent",
				new Object[] { service, true });
		p("Verified private method is called");
	}
	
	private void p(String s) {
		System.out.println(s);
	}
}