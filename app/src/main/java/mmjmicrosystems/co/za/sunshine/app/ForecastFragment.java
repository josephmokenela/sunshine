package mmjmicrosystems.co.za.sunshine.app;

/**
 * Created by CodeTribe1 on 2015-02-25.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private static final String TAG = "ForecastFragment";

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This enables the fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.forecastfragment, menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.action_refresh) {
            FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
            fetchWeatherTask.execute("94043");
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);

       // TextView postalCodeTextView = (TextView) rootView.findViewById(R.id.postal_code);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "The view position clicked " + position);
            }
        });

        // Create the array of strings to put some dummy data
        String[] forecastArray = new String[]{  "Today - Sunny 50/60",
                                                "Tomorrow - Cloudy 20/30",
                                                "Thursday - Rainy 40/60",
                                                "Friday - Sunny 34/65",
                                                "Saturday - Foggy 30/50",
                                                "Sunday - Sunny 20/60",
                                                "Monday - Cloudy 70/90"};

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);

        listView.setAdapter(listAdapter);

        return rootView;
    }

    public  class FetchWeatherTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        protected Void doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numberOfDays = 7;

            try {

                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";

                Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numberOfDays))
                        .build();


                // Construct the url for the openWeather Map query
                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                // This is the url that is being build from the input
                URL url = new URL(buildUri.toString());

                //Create the request to openWeather and create the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Do nothing in this case, the string was empty
                    return null;
                }

                forecastJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast JSON String " + forecastJsonStr);

            } catch (IOException ex) {
                Log.e("PlaceholderFragment", "Error", ex);

                // No need to pass the string since we got an error
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error in closing a stream");
                    }
                }
            }

            return null;
        }
    }
}
