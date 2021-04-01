import helper.BodyHelper;
import service.*;

import java.util.Map;

public class BaseServiceTest {
    UserService userService = new UserService();
    PlaylistService playlistService = new PlaylistService();
    SearchService searchService = new SearchService();

    RequestMaps requestMaps = new RequestMaps();
    Map<String, Object> headers = requestMaps.headersMap();
    BodyHelper helper = new BodyHelper();
}
