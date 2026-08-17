package utils;

import dto.User;
import net.datafaker.Faker;

public class UserFactory {
    static Faker faker = new Faker();


    public static User positiveRegistrationUser() {
        User user = User.builder()
                .username(faker.internet().emailAddress())
                .password(PropertiesReader.getProperty("base.properties", "password"))
                .build();
        return user;
    }

    public static User positiveLoginUser() {
        User user = User.builder()
                .username(PropertiesReader.getProperty("base.properties", "email"))
                .password(PropertiesReader.getProperty("base.properties", "password"))
                .build();
        return user;
    }


}
