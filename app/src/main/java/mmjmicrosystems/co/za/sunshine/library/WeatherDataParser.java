package mmjmicrosystems.co.za.sunshine.library;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import mmjmicrosystems.co.za.sunshine.app.ForecastFragment;
import mmjmicrosystems.co.za.sunshine.app.R;

/**
 * Created by CodeTribe1 on 2015-02-27.
 */
public class WeatherDataParser {

    private static final String LOG_TAG = "WeatherDataParser";

    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException {

        JSONObject weatherData = new JSONObject(weatherJsonStr);
        JSONArray daysList = weatherData.getJSONArray("list");
        JSONObject dayInfo = daysList.getJSONObject(dayIndex);
        JSONObject temperatureInfo = dayInfo.getJSONObject("temp");

        return  temperatureInfo.getDouble("max");
    }
}
