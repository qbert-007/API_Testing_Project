package release;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;
import io.restassured.response.Response;

public class Get_CountReleases 
{
	
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public  void TC_047_CountRealeasesOfExistingProjectKey(String Existing_projectKey) 
	{
		given()
		.headers(SetUp.headers).log().method().log().uri()
		.when()
		.get("/count/allMilestones/"+Existing_projectKey)
		.then()
		.assertThat().spec(SetUp.responseSpecPass).log().status().log().body(true);
		
	}
	
	
	@Parameters({"NonExisting_projectKey"})
	@Test
	public  void TC_048_CountRealeasesOfNonExistingProjectKey(String NonExisting_projectKey)
	{
		Response re=given().
		headers(SetUp.headers).log().method().log().uri()
		.when()
		.get("/count/allMilestones/"+NonExisting_projectKey)
		.then()
		.assertThat().spec(SetUp.responseSpecPass).log().status().log().body(true)
		.extract().response();
		
		
		String msg=re.jsonPath().get("msg").toString();
		
		Assert.assertEquals(msg,"Project Key not found");
	}
	
	@Parameters({"Existing_projectKey"})
	@Test(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public  void TC_049_ValidateCount(String Existing_projectKey)
	{
		Response res1=given()
				.headers(SetUp.headers)
				.when()
				.get("/count/allMilestones/"+Existing_projectKey)
				.then()
				.extract().response();
		int actualCount=res1.jsonPath().getInt("count");
		
		int expectedCount=0;
		
		int pages=(actualCount/50);
		if(pages>0) {
			for(int i=1;i<=pages;i++) {
				
				Response response = given().headers(SetUp.headers)
				.when()
				.get("/getAllMilestones/"+Existing_projectKey+"?page="+i)
				.then()
				.extract().response();
				
				String s=response.jsonPath().get("data.size()").toString();
				expectedCount+=Integer.parseInt(s);			
			}
			expectedCount+=50;
		}else {
			expectedCount=given().headers(SetUp.headers)
					.when()
					.get("/getAllMilestones/"+Existing_projectKey)
					.then()
					.extract().response().jsonPath().getList("data").size();
		}
						
		Assert.assertEquals(actualCount,expectedCount);
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountReleases*-------------------------------------");
	}

}
