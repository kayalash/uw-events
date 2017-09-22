package com.example.android.uwevents;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kayalash on 2017-09-20.
 */

public class QueryUtils {
    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the UW Events dataset and return a list of Event objects.
     */
    public static List<Event> fetchEventData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of Events
        List<Event> events = extractFeatureFromJson(jsonResponse);

        // Return the list of Events
        return events;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the event JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of Event objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Event> extractFeatureFromJson(String eventJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(eventJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding events to
        List<Event> events = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(eventJSON);

            // Extract the JSONArray associated with the key called "data",
            // which represents a list of features (or events).
            JSONArray eventArray = baseJsonResponse.getJSONArray("data");

            // For each event in the eventArray, create an Event object
            for (int i = 0; i < eventArray.length(); i++) {

                // Get a single event at position i within the list of events
                JSONObject currentEvent = eventArray.getJSONObject(i);

                JSONArray timeArray = currentEvent.getJSONArray("times");

                JSONObject times = timeArray.getJSONObject(0);

                String site = currentEvent.getString("site_name");

                String title = currentEvent.getString("title");

                String start = times.getString("start");

                String end = times.getString("end");

                String url = currentEvent.getString("link");

                Event event = new Event(site, title, start, end, url);

                // Add the new Event to the list of events.
                events.add(event);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the event JSON results", e);
        }

        // Return the list of events
        return events;
    }
}
