package service;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import spec.RequestSpec;
import spec.ResponseSpec;

import java.util.Map;

public class UserService extends RequestSpec {
    public UserService() {
        super("https://api.spotify.com/v1");
    }

     public Response getUserProfile(Map<String,Object> headers, ResponseSpecification responseSpecification){
         return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .when()
                .get("/me")
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
     }
     public String  getUserId(Response response){
         return response.getBody().jsonPath().getString("id");
     }
}
