package com.example.android.uwevents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kayalash on 2017-09-20.
 */

public class EventAdapter  extends ArrayAdapter<Event> {
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
        siteView.setText(currentEvent.geteSite());

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentEvent.geteTitle());

        TextView startView = (TextView) listItemView.findViewById(R.id.start);
        startView.setText(currentEvent.geteStart());

        TextView endView = (TextView) listItemView.findViewById(R.id.end);
        endView.setText(currentEvent.geteEnd());

        TextView urlView = (TextView) listItemView.findViewById(R.id.url);
        urlView.setText(currentEvent.geteSite());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
