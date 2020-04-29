package nagel.nasa.neo;

import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
        NeoFeed feed = service.getAsteroids(
                "2020-04-28",
                "2020-04-29").execute().body();

        //then
        assertNotNull(feed);


        HashMap<String, List<NeoFeed.NearEarthObject>> nearEarthObjects = feed.nearEarthObjects;
        assertFalse(nearEarthObjects.isEmpty());

        NeoFeed.NearEarthObject nearEarthObject = nearEarthObjects.get("2020-04-28").get(0);
        assertNotNull(nearEarthObject.id);
        assertNotNull(nearEarthObject.name);
        assertNotNull(nearEarthObject.nasaJpUrl);
        assertNotNull(nearEarthObject.hazardous);
    }
}