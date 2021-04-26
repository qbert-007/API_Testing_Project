package projects;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Get_ListAllProjects

{

  	@Test
	public  void TC_024_ListProjects()
	{
		
		Response response=given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/getAllProjects")
				.then().spec(SetUp.responseSpecPass).log().status()
				.extract().response();
		
		Object success=response.jsonPath().get("success").toString();
		Assert.assertEquals(success.toString(), "true");
	}
	
  	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListProjects*-------------------------------------");
	}
}
