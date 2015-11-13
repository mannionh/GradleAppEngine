package com.howard.test;

import javax.servlet.ServletContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import com.google.inject.Injector;

public class JerseyConfiguration extends ResourceConfig{

	@Inject
	public JerseyConfiguration (ServiceLocator serviceLocator, ServletContext servletContext) {
		
		packages("com.howard.test");
		
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector((Injector) servletContext.getAttribute(Injector.class.getName()));
        
	}
}
