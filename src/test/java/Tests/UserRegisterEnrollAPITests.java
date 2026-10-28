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
    @Description("As an api user i want to create the new user")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId).
                then().
                assertThat().
                statusCode(Creation_Success);
    }

    @Test(dependsOnMethods = "registerUserTests")
    @Description("As an api user i want to login as admin user")
    @Severity(SeverityLevel.BLOCKER)
    public void loginAsAdminUserTests() {
        loginResponse(adminUsername, adminPassword).
                then().
                assertThat().
                statusCode(Success_Status);
    }

    @Test(dependsOnMethods = "loginAsAdminUserTests")
    @Description("As an api user i want to approve the user")
    @Severity(SeverityLevel.BLOCKER)
    public void approveUserTests() {
        approveUser().
                then().
                assertThat().
                statusCode(Success_Status);
    }




    @Test()
    @Description("As an api user i want to register a user with different password")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithDifferentPasswordTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword+1, groupId).
                then().
                assertThat().
                statusCode(badRequest);
    }
    @Test(dependsOnMethods = "registerUserTests")
    @Description("As an api user i want to register a user with existing email address")
    @Severity(SeverityLevel.CRITICAL)
    public void registerUserWithExistingEmailAddressTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId).
                then().
                assertThat().
                statusCode(badRequest);
    }

    @Test
    @Description("As an api user i want to register a user with incorrect group id")
    @Severity(SeverityLevel.NORMAL)
    public void registerUserWithIncorrectGroupIdTests() {
        createUserResponse(firstName, lastName, email, password, confirmPassword, groupId+000).
                then().
                assertThat().
                statusCode(badRequest);
    }
    @Test(dependsOnMethods = "loginAsCreatedUserTests")
    @Description("As an api user i want to get course list as a user")
    @Severity(SeverityLevel.NORMAL)
    public void getCourseListTests() {
        getCourses().
                then().
                assertThat().
                statusCode(Success_Status);
    }

    @Test(dependsOnMethods = "approveUserTests")
    @Description("Login as newly approved user")
    @Severity(SeverityLevel.BLOCKER)
    public void loginAsCreatedUserTests() {

        loginResponse(email, password)
                .then()
                .assertThat()
                .statusCode(Success_Status);
    }
    @Test(dependsOnMethods = "getCourseListTests")
    //@Test
    @Description("As an api user i want to enroll the user to a course")
    @Severity(SeverityLevel.BLOCKER)
    public void enrollUserToCourseTests() {
        enrollCourse(courseId).
                then().
                assertThat().
                statusCode(Creation_Success);
    }
    @Test(dependsOnMethods = "enrollUserToCourseTests")
    @Description("Verify that user is enrolled in the course")
    @Severity(SeverityLevel.BLOCKER)
    public void verifyUserEnrollmentTests() {

        getMyEnrollments()
                .then()
                .assertThat()
                .statusCode(Success_Status);
    }


}