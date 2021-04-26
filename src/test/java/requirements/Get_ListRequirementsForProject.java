package requirements;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;

public class Get_ListRequirementsForProject {
	
	@Parameters("projectKey")
	@Test(priority = 1)
	public void TC_018_ListRequirements_WithValidKey(String projectKey) {

			given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/getAllRequirements/" + projectKey)
				.then()
				.body("success",equalTo(true))
				.body("data[0]", hasKey("requirement_key"))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
	}

	
	@Parameters("projectKey")
	@Test(priority = 2)
	public void TC_019_ListRequirements_WithInvalidKey(String projectKey) {

			given().headers(SetUp.headers).log().method().log().uri()
				.when().get("/getAllRequirements/" + projectKey+"invalidAddition")
				.then()
				.body("msg", equalTo("Project Key not found")).log().status();
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListRequirements*-------------------------------------");
	}
}
