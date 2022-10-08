package eu8.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@Disabled   //I don't want to run this when running BookitEnvTest with Maven "verity"
@SerenityTest
public class SpartanAdminGetTest {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI="http://44.195.19.167:7000";
    }

    @Test
    public void getAllSpartan(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
        .when()
                .get("api/spartans")
        .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void getOneSpartan(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .pathParam("id",15)
        .when()
                .get("/api/spartans/{id}");

        //if you send a request using SerenityRest, the response object can be obtained
        // from the method called lastResponse() without being saved seperately
        // same with Response response object.
        System.out.println("lastResponse().statusCode() = " + lastResponse().statusCode());

        //print id
        //instead of response.path(), we use lastResponse.path()
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //use jsonpath with lastResponse and get the name
        String name = lastResponse().jsonPath().getString("name");
        System.out.println("name = " + name);

        //you can run here in regular way if you don't want to generate the Serenity report!

    }

    //now we will do assertion in the Serenity way; lambda assertion
    //not JUnit, not Hamcrest,..
    @DisplayName("GET request with Serenity Assertion way")
    @Test
    public void getOneSpartanAssertion(){
        given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin","admin")
                .pathParam("id",15)
                .when()
                .get("/api/spartans/{id}");

        //Serenity way of assertion
        Ensure.that("Status code is 200",validatableResponse -> validatableResponse.statusCode(201));

        Ensure.that("Content-type is JSON",vRes -> vRes.contentType(ContentType.JSON));

        Ensure.that("Id is 15",vRes -> vRes.body("id",is(15)));

        // --> we can chain, but we want to do separate assertions because we want to see report separately
    }

}
