import java.util.HashMap;
import java.util.Map;

public class RequestMaps {


    public Map<String,Object> headersMap(){
        String token = "BQAeg2WR62kqeQphboI95ELZLZRAIJiwFrPcKJx9flCpPyw684XS3ut1qw951vs9qign1qCp_ABldkBOrMP9COYIw58J6d7VP2QlocCns2tgevL5fUlqOKl2bqUjeiIbF3En23WGv3brZN8sLP1IRc91Qh1uu8U2FZJmS6NFBaGtqmMpZeBjdlSSg6L9ruIMzHur8RytSZkc7Ydzun0zDOCdjSa8QB7MdhMaWhNCWoVqfQx35m-GA9flK_9yB764hVN3qJt3UXwMVflu6m5Yxo_fGToC03pIxY6DmhPu";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type" , "application/json; charset=UTF-8");
        headers.put("Authorization", "Bearer " + token);
        return headers;
    }

    public Map<String,Object> getPlaylistByIdParamsMap(String playlistId){
        Map<String, Object> params = new HashMap<>();
        params.put("playlist_id", playlistId);
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


    public Map<String,Object> addItemsToPlaylistParamsMap(String playlistId, String trackUri){
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


}
