import com.google.common.io.Resources;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import spec.ResponseSpec;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SpotifyApiTest extends BaseServiceTest {

    public String getUserId() {
        Map<String, Object> headers = requestMaps.headersMap();
        Response response =  userService.getUserProfile(headers, ResponseSpec.checkStatusCodeOk());
        return response.getBody().jsonPath().getString("id");
    }

    public String getPlaylistId() throws IOException {

       JSONObject playListBody = new JSONObject();
       playListBody.put("name", "New Playlist");
       playListBody.put("description", "New playlist description");
       playListBody.put("public", true);

       //String body = playListBody.toString();

       String body = helper.getPlayListBody("playlist" , "yeni" , true);

        Map<String, Object> headers = requestMaps.headersMap();
        Response playlistResponse = playlistService.createPlayList(headers,body, getUserId(), ResponseSpec.checkStatusCreated());
        return playlistResponse.getBody().jsonPath().getString("id");
    }

    public List<Object> getPlaylistItems() throws IOException {
        Map<String, Object> headers = requestMaps.headersMap();
        Map<String, Object> params = requestMaps.getPlaylistByIdParamsMap(getPlaylistId());
        Response bodyResponse = playlistService.getPlayListById(headers,params,getPlaylistId(),ResponseSpec.checkStatusCodeOk());
        return bodyResponse.getBody().jsonPath().getList("items");
    }

    public String getTrackUri(String trackName){
        Map<String, Object> headers = requestMaps.headersMap();
        Map<String, Object> params = requestMaps.searchParamsMap(trackName , "track" , "TR","1");
        Response searchResponse = searchService.search(headers,params,ResponseSpec.checkStatusCodeOk());

        ArrayList arrayList = searchResponse.path("tracks.items.uri");
        return arrayList.get(0).toString();
    }

    public void addItemsToPlaylist(String trackUri) throws IOException {
        Map<String, Object> headers = requestMaps.headersMap();
        Map<String, Object> params = requestMaps.addItemsToPlaylistParamsMap(getPlaylistId(),trackUri);
        playlistService.addItemsToPlayList(headers,params,getPlaylistId(),ResponseSpec.checkStatusCreated());
    }

    public String isItemAdded() throws IOException {
        Map<String, Object> headers = requestMaps.headersMap();
        Map<String, Object> params = requestMaps.getAPlaylistItemsParamsMap(getPlaylistId());
        Response itemsResponse = playlistService.getAPlaylistItems(headers,params,getPlaylistId(),ResponseSpec.checkStatusCodeOk());

        itemsResponse.getBody().jsonPath().prettyPeek();
        List<Object> trackList =  itemsResponse.jsonPath().getList("items");
        ArrayList arraylist =  itemsResponse.path("items.track.uri");
        return arraylist.get(0).toString();

    }

    public String getPlaylistName() throws IOException {
        Map<String, Object> headers = requestMaps.headersMap();
        Map<String, Object> params = requestMaps.getPlaylistByIdParamsMap(getPlaylistId());

        Response bodyResponse = playlistService.getPlayListById(headers,params,getPlaylistId(),ResponseSpec.checkStatusCodeOk());
        return bodyResponse.getBody().jsonPath().getString("name");
    }



    public void deleteTrack(String trackName) throws IOException {
        URL file = Resources.getResource("deletetrackBody.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        String body = json.toString();

        //json.getJSONObject("tracks").put("uri",getTrackUri(trackName));


        Map<String, Object> headers = requestMaps.headersMap();

        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", getPlaylistId());

        playlistService.deleteItems(headers,params,getPlaylistId(),body,ResponseSpec.checkStatusCodeOk());
    }

    @Test
    public void spotifyTest() throws IOException {
        getUserId();
        String trackName = "Beat it";
        String trackName1 = "Billie Jean";
        String trackName2 = "Bad";



        String trackUri = getTrackUri(trackName); //track search et ve URI al
        addItemsToPlaylist(trackUri); //playliste ekle

        String trackUri1 = getTrackUri(trackName1); //track search et ve URI al
        addItemsToPlaylist(trackUri1); //playliste ekle

        String trackUri2 = getTrackUri(trackName2); //track search et ve URI al
        addItemsToPlaylist(trackUri2); //playliste ekle


    }



}
/*TODO
    1- Get User ID+
    2- Create New Playlist+
    3- Search for Track *2
    4- Add Items to Playlist
    5- Get a Playlist + Check Track is Add
    6- Update Playlist
    7- Delete a track
    */