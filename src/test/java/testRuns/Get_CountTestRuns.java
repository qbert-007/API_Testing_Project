package testRuns;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;

public class Get_CountTestRuns {

	@Parameters("projectKey")
	@Test(priority = 1,groups = "setterGroup")
	public void TC_001_CountTestRun(String projectKey, ITestContext context) {

		Response response = given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/count/allTestRuns/" + projectKey)
				.then()
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();

		int count = response.jsonPath().get("count");
		context.setAttribute("testRunCount", count);

	}

	@Parameters("projectKey")
	@Test(priority = 2)
	public void TC_002CountTestRun(String projectKey) {
		
		Response response = given().log().method().log().uri()
				.when().get("/count/allTestRuns/" + projectKey)
				.then().statusCode(400).log().status()
				.extract().response();
		
		String err = response.body().asString();
		Assert.assertEquals(err, "API Key and Domain are not Provided");

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountTestRuns*-------------------------------------");
	}

}
