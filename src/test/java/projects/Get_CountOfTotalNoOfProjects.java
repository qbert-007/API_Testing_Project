package projects;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.SetUp;
import io.restassured.response.Response;

public class Get_CountOfTotalNoOfProjects {

	@Test
	public static void TC_025_CountProject() {
		given().headers(SetUp.headers).log().method().log().uri()
		.when()
		.get("/count/allProjects")
		.then().spec(SetUp.responseSpecPass).log().status().log().body(true);
	}

	@Test
	public static void TC_026_ValidateCount() {
		
		Response response1 = given().headers(SetUp.headers)
				.when().get("/count/allProjects")
				.then()
				.extract().response();
		
		int actualCount = response1.jsonPath().getInt("count");
		
		int expectedCount=0;
		
		int pages=(actualCount/50);
		if(pages>0){	
			for(int i=1;i<=pages;i++) {
				
				Response response = given().headers(SetUp.headers)
				.when()
				.get("/getAllProjects?page="+i)
				.then()
				.extract().response();
				
				String s=response.jsonPath().get("data.size()").toString();
				expectedCount+=Integer.parseInt(s);			
			}
			expectedCount+=50;    //1st response size
		}else {
			expectedCount=given().headers(SetUp.headers)
					.when()
					.get("/getAllProjects")
					.then()
					.extract().response().jsonPath().getList("data").size();
		}
		
		Assert.assertEquals(expectedCount, actualCount);

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountProjects*-------------------------------------");
	}
}
