package com.example;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(WiremockServerResource.class)
class FruitResourceTests {
	@InjectWireMock
	WireMockServer wireMockServer;

	@ConfigProperty(name = "someprop")
	String someProp;

	@BeforeEach
  public void beforeEach() {
    this.wireMockServer.resetAll();
  }

	@Test
	public void somePropOk() {
		assertThat(this.someProp)
			.isNotNull()
			.isEqualTo("someval");
	}

	@Test
	public void getAllFruits() {
		get("/fruits").then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.body("$.size()", is(2))
			.body("[0].name", is("Apple"))
			.body("[0].description", is("Crisp fruit"))
			.body("[1].name", is("Pear"))
			.body("[1].description", is("Juicy fruit"));
	}

	@Test
	public void getFruit() {
		var apple = get("/fruits").then()
			.extract()
			.body()
			.jsonPath().getList(".", Fruit.class)
			.stream()
			.filter(fruit -> "Apple".equals(fruit.name))
			.findFirst();

		assertThat(apple)
			.isNotNull()
			.get()
			.extracting("name", "description")
			.containsExactly("Apple", "Crisp fruit");

		get("/fruits/{id}", apple.get().id).then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.JSON)
			.body("name", is("Apple"))
			.body("description", is("Crisp fruit"));
	}

	@Test
	public void getFruitNotFound() {
		get("/fruits/{id}", -1).then()
			.statusCode(Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void getSomePropValHttp() {
		get("/fruits/hello").then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.TEXT)
			.body(is("someval"));
	}
}