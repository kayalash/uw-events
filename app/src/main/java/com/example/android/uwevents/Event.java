package com.example.android.uwevents;

/**
 * Created by Kayalash on 2017-09-20.
 */

public class Event {

    private String eSite;

    private String eTitle;

    private String eStart;

    private String eEnd;

    private String eURL;

    public Event (String site, String title, String start, String end, String url) {
        eSite = site;
        eTitle = title;
        eStart = start;
        eEnd = end;
        eURL = url;
    }

    public String geteSite () { return eSite; }

    public String geteTitle () { return eTitle; }

    public String geteStart () { return eStart; }

    public String geteEnd () { return eEnd; }

    public String geteURL () { return eURL; }
}
