package defects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import config.SetUp;
import io.restassured.response.Response;

public class Get_ListDefectPriority {
	
	@Test(priority = 12)
	public void TC_015_DefectPriority() {

		Response response = given()
				.headers(SetUp.headers).log().method().log().uri()
				.when().get("/defects/priority")
				.then().body("success", equalTo(true))
				.spec(SetUp.responseSpecPass).log().status()
				.extract().response();
		
		new SoftAssert().assertEquals(response.header("Connection"), "dead");
		//new SoftAssert().assertEquals(response.header("Connection"), "keep-alive");
		
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListDefectPriority*-------------------------------------");
	}

}
