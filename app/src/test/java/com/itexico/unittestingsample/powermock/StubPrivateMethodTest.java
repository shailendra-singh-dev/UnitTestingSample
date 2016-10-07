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
import org.junit.Assert;


@PrepareForTest({ SystemUnderTest.class })
@RunWith(PowerMockRunner.class)
public class StubPrivateMethodTest {
	private Service service;
	private SystemUnderTest system;
	private ServiceListener serviceListener;

	@Before
	public void setupMock() {
		// Mock
		service = Mockito.mock(Service.class);
		serviceListener = Mockito.mock(ServiceListener.class);

		system = PowerMockito.spy(new SystemUnderTest());
		system.add(service);
		system.setServiceListener(serviceListener);
	}

	@Test
	public void stubPrivateMethodAddEvent() throws Exception {
		p("Stub using PowerMockito. service.startService() should return 1 as we want startService of the service to be successful");
		PowerMockito.when(service.startService()).thenReturn(1);
		
		p("Stub service name to return serviceA");
		Mockito.when(service.getServiceName()).thenReturn("serviceA");
		
		p("Stub private addEvent to do nothing");
		PowerMockito.doNothing().when(system, "addEvent", service, true);

		p("Start the system, should startService the services in turn");
		system.start();

		p("Since we have stubbed addEvent, assert that system.getEvents() is empty");
		Assert.assertTrue(system.getEvents().isEmpty());
	}
	
	@Test
	public void stubPrivateMethodGetEventString() throws Exception {
		final String serviceA = "serviceA";
		final String serviceA_is_successful = serviceA + " is successful";
		p("Stub using PowerMockito. service.startService() should return 1 as we want startService of the service to be successful");
		PowerMockito.when(service.startService()).thenReturn(1);
		
		p("Stub service name to return serviceA");
		Mockito.when(service.getServiceName()).thenReturn(serviceA);
		
		p("Stub private addEvent to do nothing");
		PowerMockito.when(system, "getEvent", serviceA, true).thenReturn(serviceA_is_successful);

		p("Start the system, should startService the services in turn");
		system.start();

		p("Since we have stubbed getEvent, assert that system.getEvents() contains the event string");
		Assert.assertTrue(!system.getEvents().isEmpty());
		Assert.assertEquals(serviceA_is_successful, system.getEvents().get(0));
		System.out.println(system.getEvents());
	}

	private void p(String s) {
		System.out.println(s);
	}
}