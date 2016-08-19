package com.itexico.unittestingsample.mocktest;

import java.util.ArrayList;
import java.util.List;

public class SystemUnderTest {
    private static final String TAG = SystemUnderTest.class.getSimpleName();
    private List<Service> services = new ArrayList<Service>();
	private ServiceListener serviceListener;
	private List<String> events = new ArrayList<String>();

	public void start() {
        System.out.println("startService() called");
        for (Service service : services) {
			boolean success = startServiceStaticWay(service) > 0;
			notifyServiceListener(serviceListener, service, success);
			addEvent(service, success);
		}
	}
	
	private void addEvent(Service service, boolean success) {
        System.out.println("addEvent() called");
		events.add(getEvent(service.getServiceName(), success));
	}

	private String getEvent(String serviceName, boolean success) {
        System.out.println("getEvent() called");
		return serviceName + (success ? "started" : "failed");
	}

	public static void notifyServiceListener(ServiceListener serviceListener,
			Service service, boolean success) {
        System.out.println("notifyServiceListener() called");
		if (serviceListener != null) {
			if (success) {
				serviceListener.onSuccess(service);
			} else {
				serviceListener.onFailure(service);
			}
		}
	}

	public void add(Service someService) {
        System.out.println("add() called");
        services.add(someService);
	}

	public static int startServiceStaticWay(Service service) {
        System.out.println("startServiceStaticWay() called");
		int returnCode = service.startService();
		return returnCode;
	}

	public void setServiceListener(ServiceListener serviceListener) {
        System.out.println("setServiceListener() called");
		this.serviceListener = serviceListener;
	}	
	
	public List<String> getEvents() {
        System.out.println("getEvents() called");
		return events;
	}
}
