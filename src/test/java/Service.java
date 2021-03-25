import com.google.common.io.Resources;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Service {
    private String userId;
    private String playlistId;
    private String token = "BQA_iG0kTaoPrvJ8Gr0ZDROinH_1dSl1nRtObyNs4CBgzkJKKjv4YyeDsE2P-bt1BPUa30goGtMiETJgELdal8HhSj9XhWpnu75YXiiG32uzBMA6iJ0q8krH27WqzSIv4vLyPDQDapHLVytghQJNjhqiaIbU8pEg0OtOMricjGKH2e99j07nTQBXZgFzuTQV8FkPRW9CfEr_EzAjMvIsFb0x9NOuXprLjU3KinDmZ2PVdP5fWPM5bLBjBt1i6nuKTmqhvy7Thbu08241nlDLyS6FgQsmui_086JIJJi_";

    public void getUserId() {
        Response response =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/me")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        userId =  response.getBody().jsonPath().getString("id");

    }

    public void createNewPlaylist() throws IOException {
        URL file = Resources.getResource("playListBody.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);

        Response playlistResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .body(json.toString())
                        .when()
                        .post("/users/{userId}/playlists",userId)
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        playlistId = playlistResponse.getBody().jsonPath().getString("id");

    }

    public List<Object> getPlaylistItems(){
        Response bodyResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("playlist_id", playlistId)
                        .when()
                        .get("playlists/{playlist_id}",playlistId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return bodyResponse.getBody().jsonPath().getList("items");

    }


    public String getTrackUri(String trackName){
        Response trackUriResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("q",trackName )
                        .queryParam("type", "track")
                        .queryParam("market", "US")
                        .queryParam("limit","1")
                        .when()
                        .get("search")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        ArrayList arrayList = trackUriResponse.path("tracks.items.uri");
        return arrayList.get(0).toString();
    }

    public void addItemsToPlaylist(String trackUri){
        given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .queryParam("playlist_id",playlistId)
                .queryParam("uris",trackUri)
                .when()
                .post("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .statusCode(201);
    }

    public String isItemAdded(){
        Response itemResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("playlist_id", playlistId)
                        .queryParam("market", "TR")
                        .queryParam("limit", "1")
                        .when()
                        .get("playlists/{playlist_id}/tracks",playlistId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        ArrayList arraylist =  itemResponse.path("items.track.uri");
        return arraylist.get(0).toString();
    }

    public String getPlaylistName(){
        Response nameResponse =
                given()
                        .contentType("application/json; charset=UTF-8")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("playlist_id", playlistId)
                        .when()
                        .get("playlists/{playlist_id}",playlistId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return nameResponse.getBody().jsonPath().getString("name");
    }



    public void deleteTrack(String trackName) throws IOException {
        URL file = Resources.getResource("deletetrackBody.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject().put("tracks",trackName);

        json.getJSONObject("tracks").put("uri",getTrackUri(trackName));

        given()
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .queryParam("playlist_id", playlistId)
                .body(json.toString())
                .when()
                .delete("playlists/{playlist_id}/tracks",playlistId)
                .then()
                .statusCode(200);
    }
}
