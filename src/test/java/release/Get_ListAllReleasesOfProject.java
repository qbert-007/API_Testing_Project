package release;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Get_ListAllReleasesOfProject
{
	
	
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void TC_045_withExistingProjectKey(String Existing_projectKey)
	{
		Response response=given()
		.headers(SetUp.headers).log().method().log().uri()
		.when()
		.get("/getAllMilestones/"+Existing_projectKey)
		.then()
		.spec(SetUp.responseSpecPass).log().status()
		.extract().response();
		Object success=response.jsonPath().get("success");
		Assert.assertEquals(success.toString(),"true");
	}
	
	@Parameters({"NonExisting_projectKey"})
	@Test
	public void TC_046_withNonExistingProjectKey(String NonExisting_projectKey)
	{
		
		
		Response re=given().
				headers(SetUp.headers).log().method().log().uri()
				.when()
				.get("/getAllMilestones/"+NonExisting_projectKey)
				.then()
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
		
		Object msg=re.jsonPath().get("msg").toString();
		
		Assert.assertEquals(msg.toString(),"Project Key not found" );
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListReleases*-------------------------------------");
	}
}
