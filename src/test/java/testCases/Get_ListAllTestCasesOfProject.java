package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Get_ListAllTestCasesOfProject 
{
	Response response;
	Object success,msg;
	@Parameters({"Existing_projectKey"})
	@Test(priority=1,dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void TC_030_TestCaseWithExistingProjectKey(String Existing_projectKey)
	{
		response=given().log().method().log().uri()
				 .headers(SetUp.headers)
				 .when()
				 .get("/getAllTestCases/"+Existing_projectKey)
				 .then().spec(SetUp.responseSpecPass).log().status()
				 .extract().response();
		
		success=response.jsonPath().get("success");
		Assert.assertEquals(success.toString(), "true");
		
				 
	}
	
	@Parameters({"NonExisting_projectKey"})
	@Test(priority = 2)
	public void TC_031_TestCaseWithNonExistingProjectKey(String NonExisting_projectKey)
	{
		response=given()
				 .headers(SetUp.headers)
				 .when()
				 .get("/getAllTestCases/"+NonExisting_projectKey)
				 .then().spec(SetUp.responseSpecPass).extract().response();
		
		msg=response.jsonPath().get("msg");
		Assert.assertEquals(msg.toString(), "Project Key not found");
				 
	}
	
	@Parameters("Existing_projectKey_HavingNoTestCases")
	@Test
	public void TC_032_ProjectHavingNoTestCases(String Existing_projectKey_HavingNoTestCases)
	{
		response=given()
				 .headers(SetUp.headers)
				 .when()
				 .get("/getAllTestCases/"+Existing_projectKey_HavingNoTestCases)
				 .then().spec(SetUp.responseSpecPass).extract().response();
		
		msg=response.jsonPath().get("msg");
		Assert.assertEquals(msg.toString(), "Test case(s) not found for the Project");
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListTestCases*-------------------------------------");
	}

}
