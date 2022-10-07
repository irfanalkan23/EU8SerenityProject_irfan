package eu8.spartan.editor;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.Ensure;
import utilities.SpartanNewBase;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @DisplayName("Editor should be able to POST")
    @Test
    public void postSpartanAsEditor(){
        //when you need deserialize or serialize, you don't need to add seperate dependency, it comes with Serenity.
        //create one spartan using util
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();
        System.out.println("bodyMap = " + bodyMap);

        // send a post request as editor
        given()
                .auth().basic("editor","editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)      //serialization happens behind. no need to Jackson or Gson dependency. Serenity has it.
                .log().body()
        .when()
                .post("/spartans")      //because we have basePath = "/api";
                .then().log().all();

        /*
                status code is 201
                content type is Json
                success message is A Spartan is Born!
                id is not null
                name is correct
                gender is correct
                phone is correct
                check location header ends with newly generated id
         */

        Ensure.that("Status code is 201",x -> x.statusCode(201));
        Ensure.that("Content type is JSON", vR -> vR.contentType(ContentType.JSON));
        Ensure.that("success message is correct",
                thenPart -> thenPart.body("success",is("A Spartan is Born!")));
        Ensure.that("id is not null",
                thenPart -> thenPart.body("data.id",notNullValue()));
        Ensure.that("name is correct",
                thenPart -> thenPart.body("data.name",is(bodyMap.get("name"))));
        Ensure.that("gender is correct",
                thenPart -> thenPart.body("data.gender",is(bodyMap.get("gender"))));
        Ensure.that("phone is correct",
                thenPart -> thenPart.body("data.phone",is(bodyMap.get("phone"))));

        //get id and save
        String id = lastResponse().jsonPath().getString("data.id");
        //check location header ends with newly generated id
        Ensure.that("check location header ends with newly generated id",
                vR -> vR.header("Location",endsWith(id)));      //endsWith() : hamcrest matcher
    }

}
