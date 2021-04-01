import helper.DataProviderHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import spec.ResponseSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListTest extends BaseServiceTest {

    String myPlayListId;

    @Test
    public void istCreateAndIsEmptyTest() throws IOException {

        Response userResponse =  userService.getUserProfile(headers, ResponseSpec.checkStatusCodeOk());
        String userId = userService.getUserId(userResponse);// USER ID AL

        String body = helper.getPlayListBody("playlist" , "yeni" , true);
        Response playlistResponse = playlistService.createPlayList(headers,body, userId, ResponseSpec.checkStatusCreated());// USER ID KULLANARAK PLAYLIST OLUŞTUR.

        myPlayListId = playlistService.getCreatedPlayListId(playlistResponse);//Oluşturduğun playlistin Response modelinden playlist id'i al

       Map<String, Object> playlistByIdParams = requestMaps.getPlaylistByIdParamsMap(myPlayListId);
       Response itemsPlayListResponse = playlistService.getAPlaylistItems(headers,playlistByIdParams,myPlayListId,ResponseSpec.checkStatusCodeOk());
       boolean isEmpty = playlistService.getItems(itemsPlayListResponse).isEmpty();
       assertTrue(isEmpty);//ALDIĞIN ID İLE YENİ PLAYLISTIN İÇİ BOŞ MU KONTROL ET.

    }

    @Test (dataProvider = "trackName",dataProviderClass = DataProviderHelper.class)
    public void searchItems(String trackName){
        Map<String, Object> searchParams = requestMaps.searchParamsMap(trackName , "track" , "TR","1");//Item ekleyebilmek için eklemek istediğin şeyi search et
        Response searchResponse = searchService.search(headers,searchParams,ResponseSpec.checkStatusCodeOk());

        boolean isQuery = searchService.getTrackItems(searchResponse).contains(trackName);
        assertTrue(isQuery);//search sonucu gelen sonuçlarda aradığımız Track adı var mı kontrolü.

        String trackUri = searchService.getTrackUri(searchResponse);// search edilen trackin uri'ını al
        Map<String, Object> addItemsToPlayListParams = requestMaps.addItemsToPlaylistParamsMap(myPlayListId,trackUri);// aldığın uri'ı parametrelere ver
        playlistService.addItemsToPlayList(headers, addItemsToPlayListParams,myPlayListId,ResponseSpec.checkStatusCreated());
    }


}
