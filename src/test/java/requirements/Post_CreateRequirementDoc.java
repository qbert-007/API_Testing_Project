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

public class Post_CreateRequirementDoc {
	Map<String,String> parametersMap=new HashMap<>();
	
	@Parameters({"projectKey","releaseKey"})
	@Test(priority = 20)
	public void TC_022_CreateReqDoc(String projectKey,String releaseKey) {
		parametersMap.put("projectKey", projectKey);
		parametersMap.put("releaseKey", releaseKey);
		String title="Dummy_Req_Doc_Title"+Math.round(Math.random()*100);
		parametersMap.put("title", title);

		Response response = given().headers(SetUp.headers)
				.queryParams(parametersMap).log().method().log().uri()
				.when().post("/requirement/document")
				.then()
				.body("success",equalTo(true))
				.body("data[0]", hasKey("document_id"))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

		
		Assert.assertEquals(response.jsonPath().getString("data[0].project_id"),projectKey);
		Assert.assertEquals(response.jsonPath().getString("data[0].title"),title);
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateRequirementDocument*-------------------------------------");
	}

}
