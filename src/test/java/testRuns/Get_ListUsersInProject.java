package testRuns;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;

public class Get_ListUsersInProject {
	
	@Test(dataProvider = "projectKeyProvider",dataProviderClass = DataProviderUtility.class)
	public void TC_009_UserList(String projectKey, ITestContext context) {

		Response response = given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/testRun/availableUsers/"+projectKey)
				.then()
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		Assert.assertEquals(response.jsonPath().getBoolean("success"),true);
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListUsers*-------------------------------------");
	}

}
