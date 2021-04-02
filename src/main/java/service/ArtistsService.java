package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import spec.RequestSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ArtistsService extends RequestSpec {

    public ArtistsService() {
        super("https://api.spotify.com/v1");
    }

    public Response getAnArtistsTopTracks(Map<String,Object> headers, Map<String,Object> params, String artistId,ResponseSpecification responseSpecification){
        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .when()
                .get("/artists/{id}/top-tracks" , artistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public List getTopTracks(Response topTracksResponse){
        List<Map> list = topTracksResponse.getBody().jsonPath().getList("tracks");
        List<String> newlist = new ArrayList();

        for(int i = 0; i < 4; i++) {
            newlist.add(list.get(i).get("uri").toString());
        }
        return newlist;
    }
    public String getTopTracksArtistName(Response topTracksResponse){
        List<Map> list = topTracksResponse.getBody().jsonPath().getList("tracks");
        List<Map> artistsList = (List<Map>) list.get(0).get("artists");
        String artistId = (String) artistsList.get(0).get("id");
        return artistId;
    }


}
