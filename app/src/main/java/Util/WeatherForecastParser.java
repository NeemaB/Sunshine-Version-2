package Util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by neema on 2016-06-05.
 *
 * Class that implements a json parser for json strings returned from
 * the openweathermap.org API
 */
public class WeatherForecastParser  {

    private static String LOG_TAG = "WeatherForecastParser";
    private JSONObject JSONForecast = null;

    //static constants used to determine whether getTemp()
    //should return a daily max temp or min temp

    public static final int MAX_TEMP = 0;
    public static final int MIN_TEMP = 1;

    /***********************************************************************************************
     * public constructor for WeatherForecastParser
     *
     * @param JSONstring
     *                  A JSON formatted String used to create the initial JSONObject
     *
     **********************************************************************************************/

    public WeatherForecastParser(String JSONstring){
        try {

            JSONForecast = new JSONObject(JSONstring);


        }catch(JSONException e){
            Log.v(LOG_TAG, "JSON Exception From Constructor");
        }
    }

    /***********************************************************************************************
     *
     * @param Select
     *              int value used to determine whether to return maximum temperature
     *              or minimum temperature
     *
     * @param dayIndex
     *              int value used to determine which day of the week to retrieve
     *              information for
     *
     * @return      returns a temperature value as a string
     **********************************************************************************************/

    public int getTemp(int Select, int dayIndex){

        JSONArray tempJSONArray = null;
        JSONObject tempJSONObject;
        int maxOutput;
        int minOutput;

        try {

            tempJSONArray = (JSONArray) JSONForecast.get("list");

            tempJSONObject = (JSONObject) tempJSONArray.get(dayIndex);
            tempJSONObject = (JSONObject) tempJSONObject.get("temp");

            if (tempJSONObject.get("max") instanceof Double){

                maxOutput = (int) ((double) tempJSONObject.get("max"));

            }else{

                maxOutput = (int) tempJSONObject.get("max");
            }

            if(tempJSONObject.get("min") instanceof Double){

                minOutput = (int) ((double) tempJSONObject.get("min"));

            }else{

                minOutput = (int) tempJSONObject.get("min");
            }

            switch(Select){

                case MAX_TEMP : return  maxOutput;

                case MIN_TEMP : return  minOutput;
            }



        }catch(JSONException e){
            Log.v(LOG_TAG, "JSON Exception From getMaxTemp()");
        }

        return 0;


    }

    /***********************************************************************************************
     *
     * @param dayIndex
     *                int value used to determine which day of the week to retrieve
     *                information for
     * @return
     *          returns the "main" JSON field as a string
     **********************************************************************************************/

    public String getMain(int dayIndex){

        JSONArray tempJSONArray = null;
        JSONObject tempJSONObject = null;


        try {

            tempJSONArray = (JSONArray) JSONForecast.get("list");

            tempJSONObject = (JSONObject) tempJSONArray.get(dayIndex);
            tempJSONArray = (JSONArray) tempJSONObject.get("weather");

            tempJSONObject = (JSONObject) tempJSONArray.get(0);

            return (String) tempJSONObject.get("main");


        }catch(JSONException e){
            Log.v(LOG_TAG, "JSON Exception From getMain()");
        }

        return null;
    }



}
