package testRuns;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Get_ListTestRunsStatus {
	
	@Test
	public void TC_006_TestRuns_Status() {

		Response response = given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/testRuns/getAvailableStatuses")
				.then()
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		Assert.assertEquals(response.jsonPath().getBoolean("success"), true);

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListTestRunsStatus*-------------------------------------");
	}
	
}
