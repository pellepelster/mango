package io.pelle.mango.demo.server;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.pelle.mango.test.server.RestTestRestControllerMethodWithBooleanParameterRequestVO;

import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.response.Response;

public class DemoRestServiceTest extends BaseRestTest {

	@Test
	public void testMethodWithBooleanParameterPathVariables() {

		Response response = given().pathParameter("onOff", true).get(getUrl("resttest/methodwithbooleanparameter/{onOff}"));
		response.then().body(equalTo(Boolean.FALSE.toString()));

		response = given().pathParameter("onOff", false).get(getUrl("resttest/methodwithbooleanparameter/{onOff}"));
		response.then().body(equalTo(Boolean.TRUE.toString()));

	}

	@Test
	@Ignore
	public void testMethodWithBooleanParameterRequestParameters() {

		Response response = given().param("onOff", true).post(getUrl("resttest/methodwithbooleanparameter/form"));
		response.then().body(equalTo(Boolean.FALSE.toString()));

		response = given().param("onOff", false).post(getUrl("resttest/methodwithbooleanparameter"));
		response.then().body(equalTo(Boolean.TRUE.toString()));

	}

	@Test
	public void testMethodWithBooleanParameterRequestVO() {

		RestTestRestControllerMethodWithBooleanParameterRequestVO vo = new RestTestRestControllerMethodWithBooleanParameterRequestVO();
		vo.setOnOff(true);

		Response response = givenJson().body(vo).post(getUrl("resttest/methodwithbooleanparameter"));
		response.then().body(equalTo(Boolean.FALSE.toString()));

		vo.setOnOff(false);
		response = givenJson().body(vo).post(getUrl("resttest/methodwithbooleanparameter"));
		response.then().body(equalTo(Boolean.TRUE.toString()));

	}
}