package helper;
import org.testng.annotations.DataProvider;

public class DataProviderHelper {
    @DataProvider(name = "trackName")
    public Object[][] createTrackName() {

        return new Object[][]{
                {"Beat It"},
                {"Happy"},
                {"Get Lucky"}
        };
    }
}
