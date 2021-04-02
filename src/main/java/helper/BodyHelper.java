package helper;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BodyHelper {
    public String getPlayListBody(String name, String description, boolean publicOk ){

        JSONObject playListBody = new JSONObject();
        playListBody.put("name", name);
        playListBody.put("description", description);
        playListBody.put("public", publicOk);

        return playListBody.toString();
    }

    public String getDeleteTracksBody(String uri, int position){
        JSONObject deleteTracksBody = new JSONObject();
        List positions = new ArrayList();
        positions.add(position);
        List tracks = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        map.put("uri", uri );
        map.put("positions" ,positions);
        tracks.add(map);
        deleteTracksBody.put("tracks" ,tracks);

        return deleteTracksBody.toString();
    }
}

