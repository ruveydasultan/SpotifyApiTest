package helper;

import org.json.JSONObject;

public class BodyHelper {
    public String getPlayListBody(String name, String description, boolean publicOk ){

        JSONObject playListBody = new JSONObject();
        playListBody.put("name", name);
        playListBody.put("description", description);
        playListBody.put("public", publicOk);

        return playListBody.toString();
    }
}
