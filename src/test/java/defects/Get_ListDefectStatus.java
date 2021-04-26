package defects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import config.SetUp;

public class Get_ListDefectStatus {
	
	@Test
	public void TC_012_DefectStatus() {

		    given()
				.headers(SetUp.headers).log().method().log().uri()
				.when().get("/defects/status")
				.then().body("success", equalTo(true))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListDefectStatus*-------------------------------------");
	}

}
