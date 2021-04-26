package projects;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateProject 
{
	
	public static Object success,msg;
	
	@Parameters({"Existing_projectName"})
	@Test(priority=0)
	public  void TC_028_CreateProjectWithExistingName(String Existing_projectName) 
	{
		Response allProjects=given()
				.headers(SetUp.headers)
				.queryParam("name",Existing_projectName).log().method().log().uri()
				 .when().
				 post("project?name="+Existing_projectName).
				 then().
				 spec(SetUp.responseSpecPass).log().status()
 				 .extract().response();
		
		 success=allProjects.jsonPath().getString("success");
		
		Assert.assertEquals("false",success.toString());
		
         msg=allProjects.jsonPath().getString("error_msg");
         Assert.assertEquals(msg.toString(), "Project with name "+Existing_projectName+" is already existing!");
        
		
	}
	
	
	@Parameters({"NonExisting_projectName"})
	@Test(priority = 1)
	public  void TC_027_CreateProjectWithNonExistingName(String NonExisting_projectName) 
	{
		given().headers(SetUp.headers).queryParam("name",NonExisting_projectName).
        when().
        post("project?name="+NonExisting_projectName).
        then(). 
        spec(SetUp.responseSpecPass);
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateProject*-------------------------------------");
	}

}
