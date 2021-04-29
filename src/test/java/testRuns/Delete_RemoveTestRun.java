package testRuns;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;

public class Delete_RemoveTestRun {
	
	
	@Parameters("projectKey")
	@Test//(enabled=false)
	public void TC_011_DeleteTestRun(String projectKey, ITestContext context) {

			given().headers(SetUp.headers).log().method().log().uri()
				.when().delete("/testRuns/" + projectKey)
				.then()
				.assertThat().statusCode(200)
				.statusLine(containsString("OK")).log().status();
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*DeleteTestRuns*-------------------------------------");
	}

}
