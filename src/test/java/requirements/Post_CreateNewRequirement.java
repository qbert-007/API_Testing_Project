package requirements;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateNewRequirement {
	Map<String,String> parametersMap=new HashMap<>();
	long r=Math.round(Math.random()*100);
	
	@Parameters({"projectKey","docKey"})
	@Test
	public void TC_023_CreateRequirement(String projectKey,String docKey) {
		parametersMap.put("projectKey", projectKey);
		parametersMap.put("documentKey", docKey);
		String title="Dummy_Req_Title"+r;
		parametersMap.put("title", title);
		String desc="Dummy_Req_Desc"+r;
		parametersMap.put("desc", desc);

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when().post("/requirement")
				.then()
				.body("success",equalTo(true))
				.body("data[0]", hasKey("requirement_id"))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		
		Assert.assertEquals(response.jsonPath().getString("data[0].project_id"),projectKey);
		Assert.assertEquals(response.jsonPath().getString("data[0].title"),title);
		Assert.assertEquals(response.jsonPath().getString("data[0].description"),desc);
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateRequirement*-------------------------------------");
	}

}
