package tests;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetStoreTest {

    public RequestSpecification makeGetRequest() {
        String baseUrl = System.getProperty("baseUrl");
        if (baseUrl == null) {
            baseUrl = System.getenv("BASE_URL");
        }
        return RestAssured.given()
                .baseUri(baseUrl)
                .config(RestAssured.config()
                        .logConfig(LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()))
                .when();
    }

    @Test(groups = "smoke")
    public void getStoreInventory() {
        Response response = makeGetRequest().get("/store/inventory");

        Assert.assertEquals(response.statusCode(), 200, "Status is" + response.statusCode());
    }

    @Test(groups = "sanity")
    public void logoutUser() {
        Response response = makeGetRequest()
                .get("/user/logout");

        Assert.assertEquals(response.statusCode(), 200, "Status is" + response.statusCode());
    }

    @Test()
    public void failedTest() {
        Response response = makeGetRequest()
                .get("/nourl");

        Assert.assertEquals(response.statusCode(), 200, "Status is" + response.statusCode());
    }
}
