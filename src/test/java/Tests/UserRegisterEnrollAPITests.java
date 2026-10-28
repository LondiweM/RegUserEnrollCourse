package Tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static Common.CommonTestData.*;
import static Common.GenerateTestData.*;
import static requestBuilder.RequestBuilder.*;
import static requestBuilder.RequestBuilder.courseId;

public class UserRegisterEnrollAPITests {

    @Test
    @Description("As an API user I want to create the new user")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId).
                then().
                assertThat().
                statusCode(Creation_Success);
    }

    @Test(dependsOnMethods = "registerUserTests")
    @Description("As an API user I want to login as Admin user")
    @Severity(SeverityLevel.BLOCKER)
    public void loginAsAdminUserTests() {
        loginResponse(adminUsername, adminPassword).
                then().
                assertThat().
                statusCode(Success_Status);
    }

    @Test(dependsOnMethods = "loginAsAdminUserTests")
    @Description("As an admin I want to approve the new user")
    @Severity(SeverityLevel.BLOCKER)
    public void approveUserTests() {
        approveUser().
                then().
                assertThat().
                statusCode(Success_Status);
    }
    @Test(dependsOnMethods = "approveUserTests")
    @Description("As a API user I want to Login as newly approved user")
    @Severity(SeverityLevel.BLOCKER)
    public void loginAsCreatedUserTests() {
        loginResponse(email, password)
                .then()
                .assertThat()
                .statusCode(Success_Status);
    }
    @Test(dependsOnMethods = "loginAsCreatedUserTests")
    @Description("As an API user I want to get the course list")
    @Severity(SeverityLevel.NORMAL)
    public void getCourseListTests() {
        getCourses().
                then().
                assertThat().
                statusCode(Success_Status);
    }

    @Test(dependsOnMethods = "getCourseListTests")
    //@Test
    @Description("As an API user i want to enroll the user to a course")
    @Severity(SeverityLevel.BLOCKER)
    public void enrollUserToCourseTests() {
        enrollCourse(courseId).
                then().
                assertThat().
                statusCode(Creation_Success);
    }

    @Test(dependsOnMethods = "enrollUserToCourseTests")
    @Description("As an API user I want to verify that user is enrolled in the course")
    @Severity(SeverityLevel.BLOCKER)
    public void verifyUserEnrollmentTests() {

        getMyEnrollments()
                .then()
                .assertThat()
                .statusCode(Success_Status);
    }

    @Description("As an API user I want to register a user with different password")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithDifferentPasswordTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword+1, groupId).
                then().
                assertThat().
                statusCode(badRequest);
    }

    @Description("As an API user I want to register a user with existing email address")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithExistingEmailAddressTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId).
                then().
                assertThat().
                statusCode(badRequest);
    }

    @Description("As an API user I want to register a user with incorrect group id")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserWithIncorrectGroupIdTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId+000).
                then().
                assertThat().
                statusCode(badRequest);
    }







}