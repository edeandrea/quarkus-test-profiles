package com.example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
public class FruitResource {
	@ConfigProperty(name = "someprop")
	String someProp;

	@GET
	public List<Fruit> getAllFruits() {
		return Fruit.listAll();
	}

	@GET
	@Path("/{id}")
	public Response getFruitById(@PathParam("id") Long id) {
		return Fruit.findByIdOptional(id)
			.map(fruit -> Response.ok(fruit).build())
			.orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSomePropVal() {
		return this.someProp;
	}
}
