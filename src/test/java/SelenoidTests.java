import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SelenoidTests {

    @Test
    void checkTotal() {
        get("http://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkTotalAndStatus() {
        get("http://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithGiven() {
       given()
               .when()
               .get("http://selenoid.autotests.cloud/status")
               .then()
               .statusCode(200)
               .body("total", is(20));
    }

    @Test
    void checkTotalWithLogs() {
        given()
                .log().all()
                .when()
                .get("http://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .when()
                .get("http://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    void checkChromeVersion() {
        given()
                .log().uri()
                .when()
                .get("http://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    void checkTotalElse() {
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("http://selenoid.autotests.cloud/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void checkWdHubStatus401() {
        given()
                .log().uri()
                .when()
                .get("http://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(401);
    }

    @Test
    void checkWdHubStatusWithAuth() {
        given()
                .log().uri()
                .auth().basic("user1", "1234")
                .when()
                .get("http://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
