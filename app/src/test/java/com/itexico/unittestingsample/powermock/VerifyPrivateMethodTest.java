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
public class VerifyPrivateMethodTest {
	private Service service;
	private SystemUnderTest system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		// Mock
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = Mockito.spy(new SystemUnderTest());
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@Test
	public void verifyPrivateMethods() throws Exception {
		p("Stub using PowerMockito. service.startService() should return 1 as we want startService of the service to be successful");
		PowerMockito.when(service.startService()).thenReturn(1);
		
		p("Stub service name to return serviceA");
		Mockito.when(service.getServiceName()).thenReturn("serviceA");

		p("Start the system, should startService the services in turn");
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