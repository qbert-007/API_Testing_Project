package testCases;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;

public class Get_ListAllModulesOfProject
{
	public  Object success,msg;
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void TC_036_WithExistingProjectKey(String Existing_projectKey)
	{
		
		Response response=given().
				headers(SetUp.headers).log().method().log().uri()
				.when()
				.get("/getAllModules/"+Existing_projectKey)
				.then()
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
		
		success=response.jsonPath().get("success");
		
		
	}
	
	@Test(dependsOnMethods="TC_036_WithExistingProjectKey")
	public  void TC_036_ListModules()
	{
		Assert.assertEquals(success.toString(),"true");
	}
	
	@Parameters({"NonExisting_projectKey"})
	@Test
	public  void TC_036_WithNonExisting_projectKey(String NonExisting_projectKey)
	{
		Response response=given().
				headers(SetUp.headers).
				when().
				get("/getAllModules/"+NonExisting_projectKey).
				then().
				spec(SetUp.responseSpecPass).extract().response();
		
		
		msg=response.jsonPath().get("msg");
		Assert.assertEquals(msg.toString(), "Project Key not found");
		
	}
	
	
	
	@Parameters({"Existing_projectKey_HavingNoModules"})
	@Test
	public void TC_036_WithExisting_projectKey_HavingNoModules(String Existing_projectKey_HavingNoModules) throws URISyntaxException
	{
		Response response=given().
				          headers(SetUp.headers).
				          when().
				          get(new URI("/getAllModules/"+ Existing_projectKey_HavingNoModules)).
				          then().
				          spec(SetUp.responseSpecPass).extract().response();
		
		msg=response.jsonPath().get("msg");
		Assert.assertEquals(msg.toString(),"Module(s) not found for the Project");
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListAllModules*-------------------------------------");
	}

}
