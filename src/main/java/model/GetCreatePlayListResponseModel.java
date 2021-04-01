package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCreatePlayListResponseModel {
    @JsonProperty("tracks")
    private Map tracks;
    private List items;
    private String id;

    public String getId() {
        return id;
    }

    public List getItems() {
        return items;
    }



    public Map getTracks() {
        return tracks;
    }


}
