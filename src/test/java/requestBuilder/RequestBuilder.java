package requestBuilder;

import io.restassured.response.Response;

import static Common.BasePaths.ndosi_BaseURL;
import static Common.ContentType.contentType;
import static io.restassured.RestAssured.given;
import static payloadBuilder.PayloadBuilder.*;
import org.testng.Assert;

public class RequestBuilder {

    public static String token;
    public static String userId;
    public static String courseId;
    public static String enrolledCourseId;

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

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertNotNull(userId, "User ID was not returned");
        Assert.assertFalse(userId.isEmpty(), "User ID was empty");
        System.out.println("Created User ID: " + userId);

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
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        return response;
    }

    public static Response approveUser() {
        Response response = given().
                when().
                contentType(contentType).
                header("Authorization", "Bearer " + token).
                log().all().
                put(ndosi_BaseURL + "/admin/users/" + userId + "/approve").
                then().
                log().all().
                extract().response();
        Assert.assertEquals(response.getStatusCode(), 200,
                "User approval was unsuccessful");
        return response;
    }

    public static Response enrollCourse(String courseId) {

        Response response = given()
                .contentType(contentType)
                .body(enrollCoursePayload(courseId))
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .post(ndosi_BaseURL + "/courses/" + courseId + "/enroll")
                .then()
                .log().all()
                .extract().response();

    //Assert.assertEquals(response.getStatusCode(), 200, "Course enrollment was unsuccessful");
        return response;
    }
    public static Response getCourses() {

        Response response = given()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get(ndosi_BaseURL + "/courses")
                .then()
                .log().all()
                .extract().response();
        // response.prettyPrint();
        courseId = response.jsonPath().getString("data.courses[2].Id");
        System.out.println("Course ID = " + courseId);
        Assert.assertNotNull(response, "Course response should not be null");
        return response;
    }
    public static Response getMyEnrollments() {

        Response response = given()
                .contentType(contentType)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get(ndosi_BaseURL + "/my-enrollments")
                .then()
                .log().all()
                .extract()
                .response();

         enrolledCourseId =
                response.jsonPath()
                        .getString("data.enrollments[0].Id");

        System.out.println("Expected Course ID: " + courseId);
        System.out.println("Actual Course ID: " + enrolledCourseId);

        Assert.assertEquals(enrolledCourseId, courseId, "User was not enrolled in the expected course");

        return response;
    }
}