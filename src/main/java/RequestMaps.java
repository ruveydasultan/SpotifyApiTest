import java.util.HashMap;
import java.util.Map;

public class RequestMaps {


    public Map<String,Object> headersMap(){
        String token = "BQC1Ycjkshvsw7YMFfqLaSs5UDyD1JF5eNBiYUfsJ1ob2bkP2z46eBO2-Qx2fOlJ718NRF9aW-WYuZKBFXc2RqEMAIRa81m13mQgQDYrlqTjiQ4hb2iP__tQcpFGnfZJkZqiAT6g1Et4Q_fQS7ObkVdfT496d7KK2QtZyLKnDO_ntyCq_M88dK42g4xHNoyOvYQliIp943qPXAEg_GY38KYyYqiRM0aDe7Ur2650oB4bIXJn_aIHM-jOHaxAYrGUU2HzvudbOKCBO8qogcPKisg8Ft6-8sbUzbsuFyqF";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type" , "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + token);
        return headers;
    }

    public Map<String,Object> getPlaylistByIdParamsMap(String playlistId, String market){
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
        params.put("market", market);
        return params;
    }
    public Map<String,Object> searchParamsMap(String trackName, String type, String market, String limit){
        Map<String, Object> params = new HashMap<>();
        params.put("q",trackName);
        params.put("type", type);
        params.put("market", market);
        params.put("limit",limit);
        return params;
    }


    public Map<String,Object> addItemsToPlaylistParamsMap(String playlistId, Object trackUri){
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
        params.put("uris",trackUri);
        return params;
    }
    public Map<String,Object> getAPlaylistItemsParamsMap(String playlistId){
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
        params.put("market", "TR");
        params.put("limit", "1");
        return params;
    }
    public Map<String,Object> getTopTracksParamsMap(String artistId){
        Map<String, Object> params = new HashMap<>();
        params.put("id", artistId);
        params.put("market", "TR");
        return params;
    }

    public Map<String,Object> deleteTrackParams(String playlistId){
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
        return params;
    }


}
