package eu8.spartan.editor;

import io.restassured.http.ContentType;
import utilities.SpartanNewBase;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utilities.SpartanUtil;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
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
                .body(bodyMap)      //serialization happens behind. no need to Jackson or Gson dependency. Serenity has it.
                .log().body()
        .when()
                .post("/spartans")      //because we have basePath = "/api";
                .prettyPrint();

    }

}
