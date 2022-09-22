package com.example;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

import io.restassured.http.ContentType;

@QuarkusTest
@TestProfile(OtherTestProfile.class)
public class FruitOtherProfileResourceTests {
	@ConfigProperty(name = "someprop")
	String someProp;

	@Test
	public void somePropOk() {
		assertThat(this.someProp)
			.isNotNull()
			.isEqualTo("anotherval");
	}

	@Test
	public void getAllFruits() {
		get("/fruits").then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.body("$.size()", is(0));
	}

	@Test
	public void getFruitNotFound() {
		get("/fruits/{id}", 1).then()
			.statusCode(Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void getSomePropValHttp() {
		get("/fruits/hello").then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.TEXT)
			.body(is("anotherval"));
	}
}
