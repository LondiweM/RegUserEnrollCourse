package Tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static requestBuilder.RequestBuilder.*;

public class NdosiAutomationAPITests {

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
        loginrResponse(adminUsername, adminPassword).
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




}
