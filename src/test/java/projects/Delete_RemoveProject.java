package projects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;

public class Delete_RemoveProject {
	
	@Parameters("projectKey")
	@Test
	public void TC_029_DeleteProject(String projectKey, ITestContext context) {

			given().headers(SetUp.headers).log().method().log().uri()
				.when().delete("/project/" + projectKey)
				.then()
				.assertThat().statusCode(200)
				.statusLine(containsString("OK")).log().status();
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*DeleteProject*-------------------------------------");
	}

}
