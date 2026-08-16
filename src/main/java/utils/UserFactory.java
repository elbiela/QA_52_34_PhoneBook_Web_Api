package utils;

import dto.User;
import dto.UserLombok;
import net.datafaker.Faker;

public class UserFactory {
    static Faker faker = new Faker();


    public static UserLombok positiveRegistrationUser() {
        UserLombok user = UserLombok.builder()
                .username(faker.internet().emailAddress())
                .password(PropertiesReader.getProperty("base.properties", "password"))
                .build();
        return user;
    }


}
