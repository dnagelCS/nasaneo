package nagel.nasa.neo;

import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class NeoServiceTest {

    @Test
    public void getAsteroids() throws IOException {
        //given
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nasa.gov")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        NeoService service = retrofit.create(NeoService.class);

        //when
        Response<NeoFeed> response = service.getAsteroids(
                "2020-04-28",
                "2020-04-29").execute();

        //then
        assertTrue(response.toString(), response.isSuccessful());
        NeoFeed feed = response.body();
        assertNotNull(feed);


        HashMap<String, List<NeoFeed.NearEarthObject>> nearEarthObjects = feed.nearEarthObjects;
        assertFalse(nearEarthObjects.isEmpty());

        List<NeoFeed.NearEarthObject> list = nearEarthObjects.get("2020-04-28");
        NeoFeed.NearEarthObject nearEarthObject = list.get(0);
        assertNotNull(nearEarthObject.id);
        assertNotNull(nearEarthObject.name);
        assertNotNull(nearEarthObject.nasaJpUrl);
        assertNotNull(nearEarthObject.hazardous);
    }
}