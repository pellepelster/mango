package io.pelle.mango.demo.server.tmp;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.pelle.mango.demo.client.test.ValueObject2;
import io.pelle.mango.demo.server.test.RestTestRestControllerMethodWithBooleanParameterRequest;

import org.junit.Ignore;
import org.junit.Test;

import com.jayway.restassured.response.Response;

public class DemoRemoteRestServiceTest extends BaseRemoteRestTest {

	@Test
	@Ignore
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

		RestTestRestControllerMethodWithBooleanParameterRequest vo = new RestTestRestControllerMethodWithBooleanParameterRequest();
		vo.setOnOff(true);

		Response response = givenJson().body(vo).post(getUrl("resttest/methodwithbooleanparameter"));
		response.then().body(equalTo(Boolean.FALSE.toString()));

		vo.setOnOff(false);
		response = givenJson().body(vo).post(getUrl("resttest/methodwithbooleanparameter"));
		response.then().body(equalTo(Boolean.TRUE.toString()));
	}

	@Test
	public void testMethodWithValueObjectParameter() {

		ValueObject2 vo = new ValueObject2();
		vo.setString2("ggg");

		Response response = givenJson().body(vo).post(getUrl("resttest/methodwithvalueobjectparameter"));
		response.then().body("string2", equalTo("ggg"));
	}
}