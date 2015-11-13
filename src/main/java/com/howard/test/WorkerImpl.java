package com.howard.test;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class WorkerImpl implements Worker {

	@Override
	public void doSomeWork() {
		System.out.println("Hello World!");		
	}

}
