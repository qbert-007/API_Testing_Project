package defects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateDefect {
	
	static String[] priorityArr= {"Low","Medium","High"};
	static Map<String,String> parametersMap=new HashMap<>();
	
	@Parameters("projectKey")
	@Test(priority = 16)
	public void TC_017_CreateDefect(String projectKey) {
		
		parametersMap.put("projectKey", projectKey);
		parametersMap.put("priority", priorityArr[0]);
		String summary="Dummy_Issue_Summary"+Math.round(Math.random()*100);
		parametersMap.put("issueSummary", summary);

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when().post("/defects")
				.then()
				.body("data[0].project_id", equalTo(projectKey))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		Assert.assertEquals(response.jsonPath().getString("data[0].summary"), summary);

	}
	
	@Parameters("projectKey")
	@Test(priority = 16)
	public void REQ_17(String projectKey) {
		
		parametersMap.put("projectKey", projectKey);
		parametersMap.put("priority", priorityArr[0]+"invalidPriority");
		String summary="Dummy_Issue_Summary"+Math.round(Math.random()*100);
		parametersMap.put("issueSummary", summary);

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when().post("/defects")
				.then()
				.body("success", equalTo(false))
				.statusCode(404).log().status()
				.extract().response();

		Assert.assertEquals(response.jsonPath().getString("error_msg"),"Priority not Found!");

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateDefect*-------------------------------------");
	}

}
