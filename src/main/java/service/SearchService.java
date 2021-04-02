package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import spec.RequestSpec;

import java.util.ArrayList;
import java.util.Map;

public class SearchService extends RequestSpec {
    public SearchService() {
        super("https://api.spotify.com/v1");
    }

    public Response search(Map<String,Object> headers, Map<String,Object> params, ResponseSpecification responseSpecification){
        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .when()
                .get("search")
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public String getTrackUri(Response searchResponse){
        ArrayList arrayList = searchResponse.path("tracks.items.uri");
        return arrayList.get(0).toString();
    }
    public String getTrackItems(Response searchResponse){
        ArrayList arrayList = searchResponse.path("tracks.items");
        return arrayList.get(0).toString();
    }
    public String getArtistsItems(Response searchResponse){
        ArrayList arrayList = searchResponse.path("artists.items");
        return arrayList.get(0).toString();
    }
}
