package autorefresh.jobportal;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class NaukriAutoUpdate {
	
	String userName = "srikantsahoo1995@gmail.com";
	String password = "8338886625";
	String profileID = "5c89cb746dad01d57d4bbfc541bdc7fda0a3f19e780c1603cdf3e35370f49177";
	static String reqbody = "{\"username\":\"srikantsahoo1995@gmail.com\",\"password\":\"8338886625\"}";
	
	static String resumeHeadline = "{\r\n"
			+ "    \"profile\": {\r\n"
			+ "        \"resumeHeadline\": \"Experienced SDET with nearly 6 years of expertise in designing, developing, and implementing automated test frameworks and strategies. Proficient in a range of automation tools and technologies, with a strong passion for innovation and continuous improvement. Skilled in diverse domains including AppSec, SaaS, and enterprise applications. Adept in various testing methodologies, continuous integration, and Agile practices. Seeking challenging opportunities to enhance quality assurance in cutting-edge software projects.\"\r\n"
			+ "    },\r\n"
			+ "    \"profileId\": \"5c89cb746dad01d57d4bbfc541bdc7fda0a3f19e780c1603cdf3e35370f49177\"\r\n"
			+ "}";
	
	static String updatedresumeHeadline = "{\r\n"
			+ "    \"profile\": {\r\n"
			+ "        \"resumeHeadline\": \"Experienced SDET with nearly 6 years of expertise in designing, developing, and implementing automated test frameworks and strategies. Proficient in a range of automation tools and technologies, with a strong passion for innovation and continuous improvement. Skilled in diverse domains including AppSec, SaaS, and enterprise applications. Adept in various testing methodologies, continuous integration, and Agile practices. Seeking challenging opportunities to enhance quality assurance in cutting-edge software projects..\"\r\n"
			+ "    },\r\n"
			+ "    \"profileId\": \"5c89cb746dad01d57d4bbfc541bdc7fda0a3f19e780c1603cdf3e35370f49177\"\r\n"
			+ "}";

	@Test
	public void testbody() {
		
		RestAssured.baseURI = "https://www.naukri.com";
		
		String loginResponse = given().log().all()
		.header("accept", "application/json")
		.header("appid", "105")
		.header("content-type", "application/json")
		.header("systemid", "jobseeker")
		.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
		.body(reqbody)
		.when()
		.post("central-login-services/v1/login")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath json = new JsonPath(loginResponse);
		String token = json.getString("cookies[0].value");
		System.out.println("The token is :"+token );
		
		
		
		given().log().all()
		.header("accept", "application/json")
		.header("appid", "105")
		.header("clientid", "d3skt0p")
		.header("content-type", "application/json")
		.header("systemid", "Naukri")
		.header("x-http-method-override", "PUT")
		.header("authorization", "Bearer "+token)
		.body(updatedresumeHeadline)
		.when()
		.post("/cloudgateway-mynaukri/resman-aggregator-services/v1/users/self/fullprofiles")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		given().log().all()
		.header("accept", "application/json")
		.header("appid", "105")
		.header("clientid", "d3skt0p")
		.header("content-type", "application/json")
		.header("systemid", "Naukri")
		.header("x-http-method-override", "PUT")
		.header("authorization", "Bearer "+token)
		.body(resumeHeadline)
		.when()
		.post("/cloudgateway-mynaukri/resman-aggregator-services/v1/users/self/fullprofiles")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		
		
		
		
		
	}

}
