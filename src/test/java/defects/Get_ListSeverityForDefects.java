package defects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.SetUp;

public class Get_ListSeverityForDefects {
	
	@Test(priority = 12)
	public void TC_013_DefectSeverity() {

		      given()
				.headers(SetUp.headers).log().method().log().uri()
				.when().get("/defects/severity")
				.then().body("success", equalTo(true))
				.body("data.exception", equalTo(null))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListSeverity*-------------------------------------");
	}

}
