package payloadBuilder;

import com.google.gson.JsonObject;

public class PayloadBuilder {



    public static JsonObject createUserPayload(String firstName, String lastName, String email, String password, String confirmPassword,String groupId) {
        JsonObject userObject = new JsonObject();
        userObject.addProperty("firstName", firstName);
        userObject.addProperty("lastName", lastName);
        userObject.addProperty("groupId", groupId);
        userObject.addProperty("email", email);
        userObject.addProperty("password", password);
        userObject.addProperty("confirmPassword", confirmPassword);

        return userObject;
    }

    public static JsonObject loginPayload(String userName, String password) {
        JsonObject loginObject = new JsonObject();
        loginObject.addProperty("email", userName);
        loginObject.addProperty("password", password);
        return loginObject;
    }



}
