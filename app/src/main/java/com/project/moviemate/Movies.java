package com.project.moviemate;

public class Movies {
    private String movieName;
    private String movieDescription;
    private String movieImageUrl; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String movieActors; // URL of profile picture in Firebase Storage or download URL if stored locally
    private String movieID;
    private String ticketPrice;
    private String theatreName;
    public Movies() {

    }
    public Movies(String movieID, String movieName, String movieDescription, String movieImageUrl, String movieActors, String ticketPrice, String theatreName) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.movieImageUrl = movieImageUrl;
        this.movieActors = movieActors;
        this.ticketPrice = ticketPrice;
        this.theatreName = theatreName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public void setMovieImageUrl(String movieImageUrl) {
        this.movieImageUrl = movieImageUrl;
    }

    public String getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(String movieActors) {
        this.movieActors = movieActors;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }
}
