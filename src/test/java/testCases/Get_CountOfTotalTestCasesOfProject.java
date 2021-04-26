package testCases;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Get_CountOfTotalTestCasesOfProject
{
	
	Response response;
	Object msg;
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void  TC_033_WithExistingProjectKey(String Existing_projectKey)
	{
		
	given().headers(SetUp.headers).log().method().log().uri().log().body(true)
	.when().get("/count/allTestCases/"+Existing_projectKey)
	.then().spec(SetUp.responseSpecPass).log().status();
		
	}
	
	@Parameters({"NonExisting_projectKey"})
	@Test
	public void TC_034_WithNonExisting_projectKey(String NonExisting_projectKey)
	{
		response=	given().headers(SetUp.headers).log().method().log().uri()
				.when()
				.get("/count/allTestCases/"+NonExisting_projectKey)
					.then().spec(SetUp.responseSpecPass).log().status().log().body(true)
					.extract().response();
		
		msg=response.jsonPath().get("msg");
		Assert.assertEquals(msg.toString(), "Project Key not found");
		
	}
	
	@Parameters({"Existing_projectKey_HavingNoTestCases"})
	@Test
	public void TC_035_CountProjectHavingNoTestCases(String Existing_projectKey_HavingNoTestCases)
	{
		response=	given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/count/allTestCases/"+Existing_projectKey_HavingNoTestCases)
				.then()
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();
	
	msg=response.jsonPath().get("count");
	Assert.assertEquals(msg.toString(), "0");
		
	}
	
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void  TC_033_ValidateCount(String Existing_projectKey)
	{
		
	response=given().headers(SetUp.headers)
			.when().get("/getAllTestCases/"+Existing_projectKey)
			.then().spec(SetUp.responseSpecPass)
			.extract().response();
	
	String expectedCount=response.jsonPath().get("data.caseTitle.size()").toString();
	
	Response res=given().headers(SetUp.headers)
			.when().get("/count/allTestCases/"+Existing_projectKey)
			.then().spec(SetUp.responseSpecPass)
			.extract().response();
	
	String actualCount=res.jsonPath().get("count").toString();
	
	Assert.assertEquals(expectedCount, actualCount);
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountTestCases*-------------------------------------");
	}

}
