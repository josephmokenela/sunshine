package mmjmicrosystems.co.za.sunshine.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class MainActivity extends ActionBarActivity {

    public static final String TAG = "Sunshine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG, "The view position clicked " + position);
                }
            });

            // Create the array of strings to put some dummy data
            String[] forecastArray = new String[]{"Today - Sunny 50/60",
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
    }
}
