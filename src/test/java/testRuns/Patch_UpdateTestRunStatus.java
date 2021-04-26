package testRuns;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Patch_UpdateTestRunStatus {

	static String availableStatus[] = { "passed", "untested", "blocked", "retest", "failed", "not-applicable",
			"in-progress" };

	@Parameters({"projectKey","testRunKey","runResultKey"})
	@Test(priority = 1)
	public void TC_007_UpdateRunStatus(String projectKey,String testRunKey,String runResultKey) {
		Map<String, String> parametersMap = new HashMap<>();
		parametersMap.put("status", availableStatus[1]);
		parametersMap.put("project", projectKey);
		parametersMap.put("test_run", testRunKey);
		parametersMap.put("run_result", runResultKey);

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when()
				.patch("/testRunResults/status")
				.then()
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();

		
        JsonPath jPath = response.jsonPath();
		
		Assert.assertEquals(jPath.getBoolean("success"), true);
		
		Assert.assertEquals(jPath.getString("msg"),
				"Updated Test Run's status to " + availableStatus[1].toUpperCase());


	}
	
	@Parameters("projectKey")
	@Test(priority = 2)
	public void TC_008_UpdateInvalidRunStatus(String projectKey) {
		Map<String, String> parametersMap = new HashMap<>();
		parametersMap.put("status", availableStatus[1]+"invalid");
		parametersMap.put("project", projectKey);
		parametersMap.put("test_run", "nqZ6");
		parametersMap.put("run_result", "NWR4z");

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when()
				.patch("/testRunResults/status")
				.then()
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();

		JsonPath jPath = response.jsonPath();
		
		Assert.assertEquals(jPath.getBoolean("success"), false);
		Assert.assertEquals(jPath.getString("error_msg"),"Provided Status is not Valid");

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*UpdateTestRunStatus*-------------------------------------");
	}

}
