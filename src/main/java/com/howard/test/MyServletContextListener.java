package com.howard.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new MyServletModule(), new OtherMods());
	}
	
	
	public static class OtherMods extends AbstractModule{

		@Override
		protected void configure() {
			bind(Worker.class).to(WorkerImpl.class);
		}
		
	}

}
