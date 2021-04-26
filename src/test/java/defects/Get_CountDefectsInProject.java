package defects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.SetUp;

public class Get_CountDefectsInProject {
	
	public class ListAvailableDefectPriority {
		
		@Parameters("projectKey")
		@Test
		public void TC_016_CountDefects(String projectKey) {

			       given()
					.headers(SetUp.headers).log().method().log().uri()
					.when().get("/count/allDefects/"+projectKey)
					.then().body("$", hasKey("count"))
					.body("count", greaterThanOrEqualTo(0))
					.spec(SetUp.responseSpecPass).log().status().log().body(true)
					.extract().response();

		}
	}
	
	@BeforeClass
	public void beforeClassMethod() {
		System.out.println();
		System.out.println("------------------------------*ListDefectPriority*-------------------------------------");
	}
}
