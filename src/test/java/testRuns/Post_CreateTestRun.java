package testRuns;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Post_CreateTestRun {
	
	@Parameters({"projectKey","userKey","mileKey"})
	@Test(priority = 9,dependsOnGroups = "setterGroup")
	public void TC_010_CreateTestRun(String projectKey,String userKey,String mileStoneKey,ITestContext context) {
		Map<String,String> parametersMap=new HashMap<>();
		parametersMap.put("projectKey", projectKey);
		parametersMap.put("assignTo", userKey);
		parametersMap.put("milestoneKey", mileStoneKey);
		String title="Dummy_TestRun_Title"+Math.round(Math.random()*100);
		parametersMap.put("testRun", title);

		Response response = given()
				.headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when().post("/testRun")
				.then()
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();
		int c=(int) context.getAttribute("testRunCount");
		context.setAttribute("testRunCount", c+1);
		
		JsonPath jpath = response.jsonPath();
		Assert.assertEquals(jpath.getBoolean("success"),true);
		Assert.assertEquals(jpath.getString("data[0].project_id"), projectKey);
		Assert.assertEquals(jpath.getString("data[0].name"), title);
	}


	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateTestRuns*-------------------------------------");
	}
}
