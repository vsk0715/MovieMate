package com.project.moviemate;

public class Ticket {
    String ticketID, theatreName, movieName, NoOfTickets, totalPayment, movieShowDate, timestampDetails;

    public Ticket(String ticketID, String theatreName, String movieName, String noOfTickets, String totalPayment, String movieShowDate) {
        this.ticketID = ticketID;
        this.theatreName = theatreName;
        this.movieName = movieName;
        NoOfTickets = noOfTickets;
        this.totalPayment = totalPayment;
        this.movieShowDate = movieShowDate;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getNoOfTickets() {
        return NoOfTickets;
    }

    public void setNoOfTickets(String noOfTickets) {
        NoOfTickets = noOfTickets;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getMovieShowDate() {
        return movieShowDate;
    }

    public void setMovieShowDate(String movieShowDate) {
        this.movieShowDate = movieShowDate;
    }

}
