package service;

import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.GetCreatePlayListResponseModel;
import org.json.JSONObject;
import spec.RequestSpec;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class PlaylistService extends RequestSpec {
    public PlaylistService() {
        super("https://api.spotify.com/v1");
    }

    public Response createPlayList(Map<String,Object> headers, String body,String userId, ResponseSpecification responseSpecification) throws IOException {

        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .body(body)
                .when()
                .post("/users/{userId}/playlists",userId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();

    }
    public String getCreatedPlayListId(Response playlistResponse){
        GetCreatePlayListResponseModel as = playlistResponse.as(GetCreatePlayListResponseModel.class);
        return as.getId();
    }


    public Response getAPlaylistItems(Map<String,Object> headers, Map<String,Object> params, String playlistId, ResponseSpecification responseSpecification){
        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .when()
                .get("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }
    public List getItems(Response itemsResponse){
        return itemsResponse.jsonPath().getList("items");
    }

    public Response getPlayListById(Map<String,Object> headers,Map<String,Object> params,String playlistId, ResponseSpecification responseSpecification){

        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .when()
                .get("playlists/{playlist_id}",playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }
    public Response addItemsToPlayList(Map<String,Object> headers, Map<String,Object> params,String playlistId, ResponseSpecification responseSpecification){
        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .when()
                .post("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }

    public Response deleteItems(Map<String,Object> headers, Map<String,Object> params, String playlistId,String body, ResponseSpecification responseSpecification){
        return RestAssured.given()
                .spec(super.getRequestSpecification())
                .headers(headers)
                .queryParams(params)
                .body(body)
                .when()
                .delete("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .spec(responseSpecification)
                .extract()
                .response();
    }



}
