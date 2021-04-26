package testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Post_CreateModule
{
	public     Map<String,Object> param;
	public  Object success,msg;
	
	
	public  Map<String,Object> CreateParameterMap(String key,String name)
	{
		Map<String,Object> p=new HashMap<String,Object>();
		p.put("projectKey", key);
		p.put("moduleName",name);
		return p;
	}
	//Non existing moduleName and existing projectKey
	@Parameters({"Existing_projectKey","NonExisting_ModuleName"})
	@Test(priority=1)
	public  void TC_037_NonExistingModuleNameExistingKey(String Existing_projectKey,String NonExisting_ModuleName)
	{
		param=CreateParameterMap(Existing_projectKey,NonExisting_ModuleName);
		Response response=given()
		.headers(SetUp.headers)
		.queryParams(param).log().method().log().uri()
		.post("/module?projectKey="+Existing_projectKey+"&moduleName="+NonExisting_ModuleName)
		.then().log().status()
		.extract().response();
		
	   success=response.jsonPath().get("success");
	   
	   Assert.assertEquals(success.toString(),"false");
	   		
	}
	
	//Existing ModuleName and existing projectKey
	@Parameters({"Existing_projectKey","Existing_ModuleName"})
	@Test(priority=0)
	public void TC_038_ExistingModuleNameExistingKey(String Existing_projectKey,String Existing_ModuleName)
	{
		
		param=CreateParameterMap(Existing_projectKey,Existing_ModuleName);
		Response response=		given()
								.headers(SetUp.headers)
								.queryParams(param).log().method().log().uri()
								.post("module?projectKey="+Existing_projectKey+"&moduleName="+Existing_ModuleName)
								.then()
								.spec(SetUp.responseSpecPass).log().status()
								.extract().response();
		 success=response.jsonPath().get("success");
		 msg=response.jsonPath().get("error_msg");
		
		Assert.assertEquals(success.toString(),"false");
		Assert.assertEquals(msg.toString(), "Module with name "+Existing_ModuleName+" already exist!");
		
	}
	
	
	
	//non-existing moduleName and non existing project key
	@Parameters({"NonExisting_projectKey","NonExisting_ModuleName"})
	@Test(priority=4)
	public  void TC_039_NonExistingModuleNameNonExistingKey(String NonExisting_projectKey,String NonExisting_ModuleName)
	{
		
		param=CreateParameterMap(NonExisting_projectKey,NonExisting_ModuleName);
		Response response=given().
							headers(SetUp.headers).queryParams(param).log().method().log().uri()
							.post("/module?projectKey="+NonExisting_projectKey+"&moduleName="+NonExisting_ModuleName)
							.then()
							.spec(SetUp.responseSpecPass).log().status()
							.extract().response();
		
		Assert.assertEquals(response.jsonPath().getBoolean("success"),false);
		Assert.assertEquals(response.jsonPath().get("error_msg"),"Project Key not Found!");
		
		
	}
	//existing module name and non existing project key
	@Parameters({"Existing_ModuleName","NonExisting_projectKey"})
		@Test(priority=3)
		public  void TC_040_ExistingModuleNameNonExistingKey(String Existing_ModuleName,String NonExisting_projectKey ) 
		{	
		      param=CreateParameterMap(NonExisting_projectKey,Existing_ModuleName);
					Response response=given()
					.headers(SetUp.headers).queryParams(param)
					.post("/module?projectKey="+NonExisting_projectKey+"&moduleName="+Existing_ModuleName)
					.then()
					.spec(SetUp.responseSpecPass)
					.extract().response();

				Assert.assertEquals(response.jsonPath().getBoolean("success"),false);
				Assert.assertEquals(response.jsonPath().get("error_msg"),"Project Key not Found!");
			
		}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CreateModule*-------------------------------------");
	}
		
}
