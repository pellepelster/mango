package packagename.projectname;

import static com.jayway.restassured.RestAssured.given;

import java.util.UUID;

import packagename.projectname.client.TodoVO;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.jayway.restassured.response.ValidatableResponse;

public class ProjectNameServiceRestRemoteTest {

	@Test
	@Ignore
	public void testAddTodo() {

		String todoTitle = UUID.randomUUID().toString();

		String baseUrl = "http://localhost:8888/remote/";
		given().get(baseUrl + "/todos/deleteAll").then();

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(0));

		TodoVO todo = new TodoVO();
		todo.setTitle(todoTitle);
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(todo).post(baseUrl + "/todos").then().assertThat().body("title", Matchers.equalTo(todoTitle));

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(1));
	}

	@Test
	@Ignore
	public void testUpdateTodo() {

		String todoTitle = UUID.randomUUID().toString();

		String baseUrl = "http://localhost:8888/remote/";
		given().get(baseUrl + "/todos/deleteAll").then();

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(0));

		TodoVO newTodo = new TodoVO();
		newTodo.setTitle(todoTitle);

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(newTodo).post(baseUrl + "/todos").then();
		response.assertThat().body("title", Matchers.equalTo(todoTitle));
		int todoId = response.extract().path("id");

		TodoVO updateTodo = new TodoVO();
		updateTodo.setId(todoId);
		updateTodo.setTitle(todoTitle);

		ValidatableResponse updateResponse = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(updateTodo).post(baseUrl + "/todos").then();
		updateResponse.assertThat().body("title", Matchers.equalTo(todoTitle));

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(1));
	}

	@Test
	@Ignore
	public void testDeleteTodo() {

		String todoTitle = UUID.randomUUID().toString();

		String baseUrl = "http://localhost:8888/remote/";
		given().get(baseUrl + "/todos/deleteAll").then();

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(0));

		TodoVO todo = new TodoVO();
		todo.setTitle(todoTitle);

		ValidatableResponse response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(todo).post(baseUrl + "/todos").then();
		response.body("title", Matchers.equalTo(todoTitle));
		int todoId = response.extract().path("id");

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(1));
		given().pathParam("todoId", todoId).delete(baseUrl + "/todos/{todoId}").then();

		given().get(baseUrl + "/todos").then().assertThat().body("", Matchers.hasSize(0));
	}
}