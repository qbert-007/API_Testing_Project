package config;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.ResponseSpecification;

public class SetUp {
	public static Headers headers;
	public static ResponseSpecification responseSpecPass, responseSpecFail;

	@Parameters({ "domain", "api-token" })
	@BeforeSuite
	public void setUpBeforeSuite(String domain, String token) {

		RestAssured.baseURI = "https://api.qatouch.com";
		RestAssured.basePath = "/api/v1";

		Header dom = new Header("domain", domain);
		Header key = new Header("api-token", token);
		headers = new Headers(dom, key);

		setResponseSpec();
	}
	

	private static void setResponseSpec() {

		ResponseSpecBuilder builderPass= new ResponseSpecBuilder();
		builderPass.expectStatusCode(200);
		builderPass.expectStatusLine("HTTP/1.1 200 OK");
		responseSpecPass = builderPass.build();

		ResponseSpecBuilder builderFail= new ResponseSpecBuilder();
		builderFail.expectStatusCode(404);
		builderFail.expectStatusLine("HTTP/1.1 404 Not Found");
		responseSpecFail = builderFail.build();

	}

}
