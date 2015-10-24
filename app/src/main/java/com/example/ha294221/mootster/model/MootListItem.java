package com.example.ha294221.mootster.model;

/**
 * Created by HA294221 on 10/14/2015.
 */
public class MootListItem {
    private String mootTitle;
    private String mootDate;
    private String venue;
    private boolean expanded;

    public String getMootTitle() {
        return mootTitle;
    }

    public void setMootTitle(String mootTitle) {
        this.mootTitle = mootTitle;
    }

    public String getMootDate() {
        return mootDate;
    }

    public void setMootDate(String mootDate) {
        this.mootDate = mootDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
