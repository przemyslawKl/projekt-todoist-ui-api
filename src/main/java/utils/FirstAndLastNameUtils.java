package utils;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FirstAndLastNameUtils {

    public static String getFirstName() {
        return new Faker().name().firstName();
    }
    public static String getLastName() {
        return new Faker().name().lastName();
    }
}
