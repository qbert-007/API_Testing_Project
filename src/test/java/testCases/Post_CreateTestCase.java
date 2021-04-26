package testCases;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateTestCase
{

	public   Map<String,Object> param;
	public  Object success,msg;
    Response response;
	
	public Map<String,Object> CreateHashMap(String pkey,String skey,String title)
	{
		 Map<String,Object> p=new HashMap<String,Object>();
		 p.put("projectKey", pkey);
		 p.put("sectionKey", skey);
		 p.put("caseTitle", title);
		 return p;
	}
	
	@Parameters({"Existing_projectKey","Existing_sectionKey","title"})
	@Test
	public void TC_041_Existing_projectKey_Existing_sectionKey(String Existing_projectKey,String Existing_sectionKey,String title)
	{
		param=CreateHashMap(Existing_projectKey,Existing_sectionKey,title);
		response=given()
		.headers(SetUp.headers)
		.queryParams(param).log().method().log().uri()
		.post("testCase?projectKey="+Existing_projectKey+"&sectionKey="+Existing_sectionKey+"&caseTitle="+title)
		.then()
	    .spec(SetUp.responseSpecPass).log().status()
	    .extract().response();
		
		success=response.jsonPath().get("success");
		Assert.assertEquals(success.toString(),"true");
	}
	
	@Parameters({"NonExisting_projectKey","Existing_sectionKey","title"})
	@Test
	public void TC_042_NonExisting_projectKey_Existing_sectionKey(String NonExisting_projectKey,String Existing_sectionKey,String title)
	{
		param=CreateHashMap(NonExisting_projectKey,Existing_sectionKey,title);
		response=given()
		.headers(SetUp.headers)
		.queryParams(param).log().method().log().uri()
		.post("testCase?projectKey="+NonExisting_projectKey+"&sectionKey="+Existing_sectionKey+"&caseTitle="+title)
		.then()
		.spec(SetUp.responseSpecFail).log().status()
		.extract().response();
		
		Assert.assertEquals(response.jsonPath().getBoolean("success"),false);
		Assert.assertEquals(response.jsonPath().get("error_msg"),"Project Key not Found!");
		
		
		
	}
	
	@Parameters({"Existing_projectKey","NonExisting_sectionKey","title"})
	@Test
	public void TC_043_Existing_projectKey_NonExisting_sectionKey(String Existing_projectKey,String NonExisting_sectionKey,String title) throws URISyntaxException
	{
		param=CreateHashMap(Existing_projectKey,NonExisting_sectionKey,title);
		response=given().
		headers(SetUp.headers).
		queryParams(param).
		post(new URI("testCase?projectKey="+Existing_projectKey+"&sectionKey="+NonExisting_sectionKey+"&caseTitle="+title)).
		then().
		spec(SetUp.responseSpecFail).extract().response();
		
		Assert.assertEquals(response.jsonPath().getBoolean("success"),false);
		Assert.assertEquals(response.jsonPath().get("error_msg"),"Module with Key "+NonExisting_sectionKey+" does not exist!");
	
		
	}
	@Parameters({"NonExisting_projectKey","NonExisting_sectionKey","title"})
	@Test
	public void TC_044_NonExisting_projectKey_NonExisting_sectionKey(String NonExisting_projectKey,String NonExisting_sectionKey,String title) throws URISyntaxException
	{
		param=CreateHashMap(NonExisting_projectKey,NonExisting_sectionKey,title);
		response=given().
		headers(SetUp.headers).
		queryParams(param).
		post(new URI("testCase?projectKey="+NonExisting_projectKey+"&sectionKey="+NonExisting_sectionKey+"&caseTitle="+title)).
		then().
		spec(SetUp.responseSpecFail).extract().response();

		Assert.assertEquals(response.jsonPath().getBoolean("success"),false);
		Assert.assertEquals(response.jsonPath().get("error_msg"),"Project Key not Found!");
			
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountTestCase*-------------------------------------");
	}
	
}
