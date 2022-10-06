package utilities;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Faker faker = new Faker();

    public static Map<String,Object> getRandomSpartanMap(){

        Map<String,Object> bodyMap = new LinkedHashMap<>();

        bodyMap.put("name", faker.name().firstName());

//        if (faker.random().nextBoolean().equals(true)){
//            bodyMap.put("gender", "Male");
//        } else {
//            bodyMap.put("gender", "Female");
//        }

        bodyMap.put("gender", faker.demographic().sex());

        bodyMap.put("phone", faker.number().numberBetween(5_000_000_000L, 10_000_000_000L  ));

        return bodyMap;
    }
}
