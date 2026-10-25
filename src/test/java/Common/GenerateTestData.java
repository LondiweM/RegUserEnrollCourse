package Common;

import com.github.javafaker.Faker;

public class GenerateTestData {

    static Faker faker = new Faker();

    public static String firstName = faker.name().firstName();
    public static String lastName = faker.name().lastName();
    public static int randomNumber = faker.number().numberBetween(1000, 9999);
    public static String email = firstName + "." + lastName+randomNumber + "@gmail.com";
    public static String password = faker.internet().password(8, 10, true, true, true)+"#";
    public static String confirmPassword = password;



}
