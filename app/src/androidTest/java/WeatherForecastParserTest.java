import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.android.sunshine.app.WeatherForecastParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by neema on 2016-06-05.
 */

@RunWith(AndroidJUnit4.class)
public class WeatherForecastParserTest {

    private WeatherForecastParser parser = null;

    private static String sampleJSONString = "{\"city\":{\"id\":6090786,\"name\":\"North Vancouver\",\"coord\":{\"lon\":-123.069344,\"lat\":49.36636},\"country\":\"CA\",\"population\":0}" +
            ",\"cod\":\"200\",\"message\":0.0133,\"cnt\":7,\"list\":[" +
            "{\"dt\":1465156800,\"temp\":{\"day\":27.59,\"min\":19.55,\"max\":27.59,\"night\":19.55,\"eve\":25.78,\"morn\":27.59},\"pressure\":1006.14,\"humidity\":49,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"02n\"}],\"speed\":1.11,\"deg\":271,\"clouds\":8}," +
            "{\"dt\":1465243200,\"temp\":{\"day\":24.1,\"min\":16.5,\"max\":24.26,\"night\":16.5,\"eve\":22.49,\"morn\":20.71},\"pressure\":1006.8,\"humidity\":59,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"speed\":1.32,\"deg\":255,\"clouds\":20}," +
            "{\"dt\":1465329600,\"temp\":{\"day\":21.38,\"min\":14.14,\"max\":21.48,\"night\":14.14,\"eve\":19.71,\"morn\":18.21},\"pressure\":1003.7,\"humidity\":62,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":1.06,\"deg\":235,\"clouds\":0}," +
            "{\"dt\":1465416000,\"temp\":{\"day\":17.3,\"min\":11.66,\"max\":17.3,\"night\":11.66,\"eve\":15.96,\"morn\":14.84},\"pressure\":1006.16,\"humidity\":72,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":2.48,\"deg\":159,\"clouds\":44,\"rain\":0.21}," +
            "{\"dt\":1465502400,\"temp\":{\"day\":15.72,\"min\":11.26,\"max\":17.85,\"night\":14.09,\"eve\":17.85,\"morn\":11.26},\"pressure\":990.67,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.34,\"deg\":235,\"clouds\":0,\"rain\":0.38}," +
            "{\"dt\":1465588800,\"temp\":{\"day\":14.15,\"min\":11.62,\"max\":16.44,\"night\":12.65,\"eve\":16.44,\"morn\":11.62},\"pressure\":994.8,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.74,\"deg\":192,\"clouds\":26,\"rain\":0.42}," +
            "{\"dt\":1465675200,\"temp\":{\"day\":12.12,\"min\":9.57,\"max\":12.79,\"night\":10.93,\"eve\":12.79,\"morn\":9.57},\"pressure\":998.93,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":3.12,\"deg\":196,\"clouds\":25,\"rain\":4.23}]}";

    @Before
    public void setup(){

        parser = new WeatherForecastParser(sampleJSONString);
    }

    @Test
    public void getTempMaxTest1(){

        String output = parser.getTemp(WeatherForecastParser.MAX_TEMP, 1);

        Assert.assertEquals(output,"24.26");

    }

    @Test
    public void getTempMaxTest2(){

        String output = parser.getTemp(WeatherForecastParser.MAX_TEMP, 6);

        Assert.assertEquals(output, "12.79");
    }

    @Test
    public void getTempMinTest1(){

        String output = parser.getTemp(WeatherForecastParser.MIN_TEMP, 2);

        Assert.assertEquals(output, "14.14");

    }

    @Test
    public void getTempMinTest2(){

        String output = parser.getTemp(WeatherForecastParser.MIN_TEMP, 0);

        Assert.assertEquals(output, "19.55");
    }

    @Test
    public void getMainTest1(){

        String output = parser.getMain(2);

        Assert.assertEquals(output, "Clear");
    }

    @Test
    public void getMainTest2(){

        String output = parser.getMain(5);

        Assert.assertEquals(output, "Rain");
    }


}
