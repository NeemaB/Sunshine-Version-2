package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
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

import com.example.android.sunshine.app.DetailActivity;
import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.SettingsActivity;

import Util.WeatherForecastParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by neema on 2016-06-01.
 *
 * Fragment class that displays the main page UI
 */
public class ForecastFragment extends Fragment {


    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> forecastItems = new ArrayList<String>();

    public ForecastFragment() {
    }

    /***********************************************************************************************
     * Called when the fragment instance is created, setHasOptionsMenu is set to true so that the
     * fragment can provide a menu item to the main activity's menu
     *
     *
     **********************************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    /*********************************************************************************************
     * Attaches the refresh menu item to the menu bar
     *
     **********************************************************************************************/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.forecastfragment, menu);


    }

    /**********************************************************************************************

     If the refresh button is selected, a new fetch weather task is created and run.

     **********************************************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new fetchWeatherTask().execute("V7N1R2");
        }else if (id == R.id.action_settings){

            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /*********************************************************************************************
     * Updates the UI to display the layout in layout/fragment_main.xml
     *
     * Creates the array adapter that will populate the list view
     **********************************************************************************************/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                forecastItems);

        ListView list = (ListView) rootView.findViewById(R.id.listview_forecast);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /***************************************************************************************
             Create a new Toast that displays the contents of the view inside of the list view
             **************************************************************************************/
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                TextView textView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
                CharSequence text = textView.getText();
//
//                Context context = getActivity().getApplicationContext();
//                int duration = Toast.LENGTH_LONG;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();

                Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, (String) text );
                startActivity(intent);

            }
        });

        list.setAdapter(arrayAdapter);


        return rootView;
    }

    /**********************************************************************************************
     *
     * Helper Class that represents a task to query the openweathermap API and return a 7 day
     * weather forecast encoded in json format.
     *
     * Outputs an array of strings that will populate the text in each row of the ListView
     *
     *
     **********************************************************************************************/
    private class fetchWeatherTask extends AsyncTask<String, Void, String[]> {

        private final static String LOG_TAG = "fetchWeatherTask";
        final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

        private String units = "metric";
        private String mode = "json";
        private int days = 7;
        private String key = "978df8c5e60e8c8666206cd8f11ee8a2";

        private String getReadableDateString(long time){
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        protected String[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                final String QUERY_PARAM = "q";
                final String MODE_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String COUNT_PARAM = "cnt";
                final String APPID_PARAM = "APPID";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(MODE_PARAM, mode)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(COUNT_PARAM, Integer.toString(days))
                        .appendQueryParameter(APPID_PARAM, key).build();

                Log.v(LOG_TAG, builtUri.toString());

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("ForecastFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ForecastFragment", "Error closing stream", e);
                    }
                }
            }
            //parse the json data
            WeatherForecastParser parser = new WeatherForecastParser(forecastJsonStr);
            int maxTemp;
            int minTemp;
            String main;
            String entry;
            String day;
            String[] output = new String[days];

            //create an output string for each day
            for (int i = 0; i <= days - 1; i++) {

                Time dayTime = new Time();
                dayTime.setToNow();

                int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

                dayTime = new Time();
                long dateTime;

                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                //daily maximum temperature
                maxTemp = parser.getTemp(WeatherForecastParser.MAX_TEMP, i);
                //daily minimum temperature
                minTemp = parser.getTemp(WeatherForecastParser.MIN_TEMP, i);
                //general description of the weather
                main = parser.getMain(i);

                entry = day + " - " + main + " - " + (maxTemp) + "/" + (minTemp);

                output[i] = new String(entry);

            }

            return output;

        }

        protected void onPostExecute(String[] array) {

            if (array.length == days) {

                forecastItems.clear();

                for (int i = 0; i <= days - 1; i++) {

                    forecastItems.add(array[i]);

                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}