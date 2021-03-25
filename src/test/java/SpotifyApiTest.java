import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SpotifyApiTest extends Service{


    @BeforeMethod
    public void beforeTest() throws IOException {
        RestAssured.baseURI = "https://api.spotify.com/v1";
    }

    @Test
    public void spotifyTest() throws IOException {
        getUserId();
        String trackName = "Beat it";
        String trackName1 = "Billie Jean";
        String trackName2 = "Bad";

        createNewPlaylist();

        String trackUri = getTrackUri(trackName); //track search et ve URI al
        addItemsToPlaylist(trackUri); //playliste ekle

        String trackUri1 = getTrackUri(trackName1); //track search et ve URI al
        addItemsToPlaylist(trackUri1); //playliste ekle

        String trackUri2 = getTrackUri(trackName2); //track search et ve URI al
        addItemsToPlaylist(trackUri2); //playliste ekle


        assertEquals(trackUri,isItemAdded());

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