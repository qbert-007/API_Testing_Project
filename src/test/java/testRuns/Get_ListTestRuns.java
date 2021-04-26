package testRuns;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Get_ListTestRuns {

	@Parameters("projectKey")
	@Test(priority = 1, dependsOnGroups = "setterGroup")
	public void TC_003_TestRuns(String projectKey, ITestContext context) {

		Response response = given()
				.headers(SetUp.headers).log().method().log().uri()
				.when()
				.get("/getAllTestRuns/" + projectKey)
				.then().spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		
		List<Object> list = response.jsonPath().getList("data");
		int count=list.size();
		
		if (count >= 50) {
			int pages = ((int) context.getAttribute("testRunCount")) / 50;

			for (int i = 1; i <= pages; i++) {

				Response res = given().headers(SetUp.headers).log().uri()
						.when().get("/getAllTestRuns/"+ projectKey+"?page="+i)
						.then().extract().response();
							
				String s=res.jsonPath().get("data.size()").toString();
				count+=Integer.parseInt(s);	
				 
			}

		}
		Assert.assertEquals(count, (int) context.getAttribute("testRunCount"));

	}

	@Test(priority = 2)
	public void TC_004_TestRuns(ITestContext context) {

		Response response = given().headers(SetUp.headers).log().method().log().uri().when()
				.get("/getAllTestRuns/" + "").then().statusCode(404).log().status().extract().response();

		Assert.assertTrue(response.statusLine().contains("Not Found"));

	}

	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListTestRuns*-------------------------------------");
	}

}
