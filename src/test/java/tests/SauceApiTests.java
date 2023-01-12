package tests;

import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.serializer.SerializeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.User;

public class SauceApiTests {

	@Test
	public void getandVerifyValidSingleUserTest() {
		RestAssured.given().baseUri("https://reqres.in/").basePath("api/users/{id}").pathParam("id", 2).when().get()
				.then().assertThat().statusCode(200);
	}

	@Test
	public void getandVerifyInValidSingleUserTest() {
		RestAssured.given().baseUri("https://reqres.in/").basePath("api/users/{id}").pathParam("id", 25).when().get()
				.then().assertThat().statusCode(404);
	}

	@Test
	public void getAndVerifyValidMultipleUsersTest() {
		RestAssured.given().baseUri("https://reqres.in/api/users?page=2").when().get().then().assertThat()
				.statusCode(200);
	}

	@Test
	public void postValidUserTest() throws SerializeException {
		Faker faker = new Faker();

		User user = new User(faker.name().firstName(), faker.job().position());
		JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;
		String jsonUser = jsonSerializer.serialize(user);

		Response response = RestAssured.given().baseUri("https://reqres.in/api/users").body(jsonUser)
				.when().post();				

		Assert.assertNotNull(response.body().jsonPath().get("id"));

	}
}
