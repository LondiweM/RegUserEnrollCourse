package requestBuilder;

import io.restassured.response.Response;

import static Common.BasePaths.ndosi_BaseURL;
import static Common.ContentType.contentType;
import static io.restassured.RestAssured.given;
import static payloadBuilder.PayloadBuilder.*;

public class RequestBuilder {

    public static String token;
    public static String userId;
    public static String courseId;

    public static Response createUserResponse(String firstName, String lastName, String email, String password, String confirmPassword, String groupId) {
        Response response = given().
                when().
                body(createUserPayload(firstName, lastName, email, password, confirmPassword, groupId)).
                contentType(contentType).
                log().all().
                post(ndosi_BaseURL + "/register").
                then().
                log().all().
                extract().response();

        userId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response loginResponse(String userName, String password) {
        Response response = given().
                when().
                body(loginPayload(userName, password)).
                contentType(contentType).
                log().all().
                post(ndosi_BaseURL + "/login").
                then().
                log().all().
                extract().response();
        token = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response approveUser() {
        Response approve = given().
                when().
                contentType(contentType).
                header("Authorization", "Bearer " + token).
                log().all().
                put(ndosi_BaseURL + "/admin/users/" + userId + "/approve").
                then().
                log().all().
                extract().response();
        return approve;
    }

    public static Response enrollCourse(String courseId) {

        Response enrollresponse = given()
                .contentType(contentType)
                .body(enrollCoursePayload(courseId))
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .post(ndosi_BaseURL + "/courses/" + courseId + "/enroll")
                .then()
                .log().all()
                .extract().response();

        return enrollresponse;
    }
    public static Response getCourses() {

        Response courseresponse = given()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get(ndosi_BaseURL + "/courses")
                .then()
                .log().all()
                .extract().response();
       // response.prettyPrint();
        courseId = courseresponse.jsonPath().getString("data.courses[2].Id");
        System.out.println("Course ID = " + courseId);
        return courseresponse;
    }
    public static Response getMyEnrollments() {

        Response Enrollmentresponse = given()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get(ndosi_BaseURL + "/my-enrollments")
                .then()
                .log().all()
                .extract()
                .response();

        return Enrollmentresponse;
    }
}