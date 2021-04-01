import java.util.HashMap;
import java.util.Map;

public class RequestMaps {


    public Map<String,Object> headersMap(){
        String token = "BQA6YgoixXIx5hcIUlVQH2oCgvaJ-7Vo4MY51g7Z_epPtJD2yG5pLRE-fMI-Uan3ntWilTYg1j-M-COxDtUHKUhoDonN3BRqHB55fNRbXnjv1QRR92KEGgDqTB_A1XDEYPUenMrFhHOIsO1XhBzzkZLB2MdVUcm2-WUUBtuuR2Me0KXLNXHOsFS2SaFThRmnu0yj-1d4aIak5S7wppevCNN-40bwv2T57dlkjC6w3FBmdhoedIs4FyoGM4ba4X1HpuyzP7oF-tRmgQpAPaUNBNVC3n5otYLf6YnEBh5H";
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
