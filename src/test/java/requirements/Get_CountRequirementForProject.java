package requirements;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.DataProviderUtility;
import config.SetUp;

public class Get_CountRequirementForProject {
	
	@Test(dataProvider = "projectKeyProvider",dataProviderClass = DataProviderUtility.class)
	public void TC_020_CountReq(String projectKey) {

			given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/count/allRequirements/" + projectKey)
				.then()
				.body("$", hasKey("count"))
				.body("count", greaterThanOrEqualTo(0))
				.spec(SetUp.responseSpecPass).log().status().log().body(true)
				.extract().response();
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*CountRequirements*-------------------------------------");
	}

}
