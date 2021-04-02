import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import spec.ResponseSpec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Test
public class TopTracksTest extends BaseServiceTest{
    /*TODO
    Items içinde uri varlığı kontrol etme.
     */

    String myPlayListId;
    List<String> tracksList;
    Map<String, Object> playlistByIdParams = requestMaps.getPlaylistByIdParamsMap(myPlayListId,"tr");

    @Test
    public void istCreateAndIsEmptyTest() throws IOException {

        Response userResponse =  userService.getUserProfile(headers, ResponseSpec.checkStatusCodeOk());
        String userId = userService.getUserId(userResponse);// USER ID AL

        String body = helper.getPlayListBody("playlist" , "yeni" , true);
        Response playlistResponse = playlistService.createPlayList(headers,body, userId, ResponseSpec.checkStatusCreated());// USER ID KULLANARAK PLAYLIST OLUŞTUR.

        myPlayListId = playlistService.getCreatedPlayListId(playlistResponse);//Oluşturduğun playlistin Response modelinden playlist id'i al

        //Map<String, Object> playlistByIdParams = requestMaps.getPlaylistByIdParamsMap(myPlayListId,"tr");
        Response itemsPlayListResponse = playlistService.getAPlaylistItems(headers,playlistByIdParams,myPlayListId,ResponseSpec.checkStatusCodeOk());
        boolean isEmpty = playlistService.getItems(itemsPlayListResponse).isEmpty();
        assertTrue(isEmpty);//ALDIĞIN ID İLE YENİ PLAYLISTIN İÇİ BOŞ MU KONTROL ET.

    }

    @Test(dependsOnMethods={"istCreateAndIsEmptyTest"})
    public void topTrackTest(){

        //artist araması yap
        String artistName = "Pharrell Williams";
        Map<String, Object> searchParams = requestMaps.searchParamsMap(artistName , "artist" , "US","1");//Item ekleyebilmek için eklemek istediğin şeyi search et
        Response searchResponse = searchService.search(headers,searchParams, ResponseSpec.checkStatusCodeOk());
        boolean isQuery = searchService.getArtistsItems(searchResponse).contains(artistName);
        assertTrue(isQuery);//arama sonucunda aradığın şey var mı kontrol et.

        JsonPath responseJson = searchResponse.getBody().jsonPath();
        List<String> list = responseJson.getList("artists.items.id");
        String artistId = list.get(0);

        Map<String,Object> topTracksParams = requestMaps.getTopTracksParamsMap(artistId);
        Response topTracksResponse = artistsService.getAnArtistsTopTracks(headers,topTracksParams, artistId, ResponseSpec.checkStatusCodeOk());
        tracksList = artistsService.getTopTracks(topTracksResponse);

        assertEquals(artistId,artistsService.getTopTracksArtistName(topTracksResponse));//gelen sonuçlardaki id ile verilen id aynı mı kontrolü

    }

    @Test(dependsOnMethods={"topTrackTest"})
    public void addTracksToPlayLists(){

//        for(String track : tracksList) {
//            Map<String, Object> addItemsToPlayListParams = requestMaps.addItemsToPlaylistParamsMap(myPlayListId,track);// aldığın uri'ı parametrelere ver
//            playlistService.addItemsToPlayList(headers, addItemsToPlayListParams,myPlayListId,ResponseSpec.checkStatusCreated());//track ekle
//        }
        for(int i = 0; i < 3; i++) {
           Map<String, Object> addItemsToPlayListParams = requestMaps.addItemsToPlaylistParamsMap(myPlayListId,tracksList.get(i));// aldığın uri'ı parametrelere ver
           playlistService.addItemsToPlayList(headers, addItemsToPlayListParams,myPlayListId,ResponseSpec.checkStatusCreated());//track ekle

           Response itemsPlayListResponse = playlistService.getAPlaylistItems(headers,playlistByIdParams,myPlayListId,ResponseSpec.checkStatusCodeOk());
           boolean uri = playlistService.getItems(itemsPlayListResponse).get(i).get("track").toString().contains(tracksList.get(i));
           assertTrue(uri);//playlistte eklenen track var mı kontrolü
         }


    }

    @Test(dependsOnMethods={"addTracksToPlayLists"})
    public void deleteTrackfromPlaylist(){
        Response itemsPlayListResponse = playlistService.getAPlaylistItems(headers,playlistByIdParams,myPlayListId,ResponseSpec.checkStatusCodeOk());
        int firstSize = playlistService.getItems(itemsPlayListResponse).size();
        Map<String, Object> deleteParams = requestMaps.deleteTrackParams(myPlayListId);
        String deleteBody = helper.getDeleteTracksBody(tracksList.get(2) , 2 );
        playlistService.deleteItems(headers,deleteParams,myPlayListId, deleteBody,ResponseSpec.checkStatusCodeOk() );//son ekleneni silmek için
        Response itemsPlayListResponse2 = playlistService.getAPlaylistItems(headers,playlistByIdParams,myPlayListId,ResponseSpec.checkStatusCodeOk());
        int lastSize = playlistService.getItems(itemsPlayListResponse2).size();
        assertTrue(lastSize<firstSize);

    }






}
