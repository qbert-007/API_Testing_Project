package testRuns;

import static io.restassured.RestAssured.given;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;

public class Get_ListTestRunsResult {
	
	@Test(dataProvider = "projectKeyProvider",dataProviderClass = DataProviderUtility.class)
	public void TC_005_TestRunResult(String projectKey, ITestContext context) {

		  given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/testRunResults/"+projectKey+"/nqZ6")
				.then()
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();

	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListTestRunsResult*-------------------------------------");
	}

}
