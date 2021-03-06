package com.example.application;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.ws.rs.core.Application;

import org.palestyn.events.ApplicationStarted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.resources.PersonResource;

// @ApplicationPath() mapping will be ignored
// use .setContextPath("/example") in Launcher
public class ExampleApplication extends Application {
	
	Logger logger = LoggerFactory.getLogger(ExampleApplication.class);
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(PersonResource.class);
		return set;
	}
	
	public void onApplicationStarted(@Observes ApplicationStarted event) {
		logger.info("Application started..");
	}
}
