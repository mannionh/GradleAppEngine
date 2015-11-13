package com.howard.test;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule {

	
	@Override
	protected void configureServlets() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("javax.ws.rs.Application", "com.howard.test.JerseyConfiguration");
		
		//Servlets/Filters must be guice singletons
		bind(org.glassfish.jersey.servlet.ServletContainer.class).in(Singleton.class);
		bind(com.googlecode.objectify.ObjectifyFilter.class).in(Singleton.class);
	    
		filter("/*").through(org.glassfish.jersey.servlet.ServletContainer.class, params);
		filter("/*").through(com.googlecode.objectify.ObjectifyFilter.class);
		
	}

	
}
