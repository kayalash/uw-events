package com.example.android.uwevents;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kayalash on 2017-09-20.
 */

public class EventAdapter  extends ArrayAdapter<Event> {

    public static final String LOG_TAG = EventAdapter.class.getSimpleName();

    public EventAdapter (Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.event_activity, parent, false);
        }

        // Find the event at the given position in the list of events
        Event currentEvent = getItem(position);

        TextView siteView = (TextView) listItemView.findViewById(R.id.site);
        siteView.setText(currentEvent.geteSite().toUpperCase());

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentEvent.geteTitle());

        Date start, end;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        try {
            start = simpleDateFormat.parse(currentEvent.geteStart());
        } catch (ParseException e) {
            Log.e (LOG_TAG, "Problem converting ISO date to simple date");
            start = new Date ();
        }

        try {
            end = simpleDateFormat.parse(currentEvent.geteEnd());
        } catch (ParseException e) {
            Log.e (LOG_TAG, "Problem converting ISO date to simple date");
            end = new Date ();
        }

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(dateFormat.format(start));

        TextView startView = (TextView) listItemView.findViewById(R.id.start);
        startView.setText(timeFormat.format(start));

        TextView endView = (TextView) listItemView.findViewById(R.id.end);
        endView.setText(timeFormat.format(end));

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
