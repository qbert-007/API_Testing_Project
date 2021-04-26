package release;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateNewRelease
{
	
	public  Map<String,Object> param;
	public Map<String,Object> CreateParameterMap(String projectKey,String milestone)
	{
		Map<String,Object> p=new HashMap<String,Object>();
		p.put("projectKey",projectKey);
		p.put("milestone", milestone);
		return p;
	}
	@Parameters({"Existing_projectKey","milestone"})
	@Test//(dataProvider="projectKeyProvider",dataProviderClass=DataProviderUtility.class)
	public void TC_050_CreateRelease_ExistingProjectKey(String Existing_projectKey,String milestone)
	{
		param=CreateParameterMap(Existing_projectKey,milestone);
		given().headers(SetUp.headers).queryParams(param).log().method().log().uri()
        .when()
        .post("/milestone?projectKey="+Existing_projectKey+"&milestone="+milestone)
        .then().spec(SetUp.responseSpecPass).log().status();
	}
	
	@Parameters({"NonExisting_projectKey","milestone"})
	@Test
	public  void TC_051_CreateRelease_NonExistingProjectKey(String NonExisting_projectKey,String milestone) 
	{
		
		param=CreateParameterMap(NonExisting_projectKey,milestone);
		Response re=given()
				.headers(SetUp.headers).queryParams(param).log().method().log().uri()
				.when()
				.post("/milestone?projectKey="+NonExisting_projectKey+"&milestone="+milestone)
				.then() 
				.spec(SetUp.responseSpecFail).log().status().log().body(true)
				.extract().response();
		
		Assert.assertEquals(re.jsonPath().getBoolean("success"),false);
		Assert.assertEquals(re.jsonPath().get("error_msg"),"Project Key not Found!");
						
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateRelease*-------------------------------------");
	}

}
