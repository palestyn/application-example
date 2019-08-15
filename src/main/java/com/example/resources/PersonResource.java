package com.example.resources;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.example.domain.Person;
import com.example.service.PersonService;

@Path("person")
public class PersonResource {

	@Inject PersonService personService;
	
	@Path("create")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Person addPerson(@Context ServletContext context, @QueryParam("fullName") String fullName) {
		return personService.create(fullName);
	}
	
	@Inject
	@ConfigProperty(name = "some.config.key")
	String configValue;
	
	@Path("config")
	@GET
	public String configKey() {
		return "This is from microprofile config: "+configValue;
	}
}
