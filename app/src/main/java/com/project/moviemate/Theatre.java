package com.project.moviemate;

public class Theatre {
    private String theatreName;
    private String theatreLocation;
    private String theatreImageUrl; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String theatreID;

    public Theatre() {

    }

    public Theatre(String theatreName, String theatreLocation, String theatreImageUrl, String theatreID) {
        this.theatreName = theatreName;
        this.theatreLocation = theatreLocation;
        this.theatreImageUrl = theatreImageUrl;
        this.theatreID = theatreID;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreLocation() {
        return theatreLocation;
    }

    public void setTheatreLocation(String theatreLocation) {
        this.theatreLocation = theatreLocation;
    }

    public String getTheatreImageUrl() {
        return theatreImageUrl;
    }

    public void setTheatreImageUrl(String theatreImageUrl) {
        this.theatreImageUrl = theatreImageUrl;
    }

    public String getTheatreID() {
        return theatreID;
    }

    public void setTheatreID(String theatreID) {
        this.theatreID = theatreID;
    }
}
