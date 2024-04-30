package com.project.moviemate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder>{
    private final List<Ticket> Tickets;

    public TicketAdapter(List<Ticket> tickets) {
        Tickets = tickets;
    }

    @NonNull
    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new TicketAdapter.TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        Ticket ticket = Tickets.get(position);
        holder.ticketID.setText("Ticket ID: "+ ticket.getTicketID());
        holder.theatreName.setText(ticket.getTheatreName());
        holder.movieName.setText(ticket.getMovieName());
        holder.NoOfTickets.setText(ticket.getNoOfTickets());
        holder.totalPayment.setText(ticket.getTotalPayment()+" Rs");
        holder.movieShowDate.setText(ticket.getMovieShowDate());
        holder.timestampDetails.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return Tickets.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder{
        TextView ticketID, theatreName, movieName, NoOfTickets, totalPayment, movieShowDate, timestampDetails;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketID = itemView.findViewById(R.id.ticketID);
            theatreName = itemView.findViewById(R.id.theatreName);
            movieName = itemView.findViewById(R.id.movieName);
            NoOfTickets = itemView.findViewById(R.id.NoOfTickets);
            totalPayment = itemView.findViewById(R.id.totalPayment);
            movieShowDate = itemView.findViewById(R.id.movieShowDate);
            timestampDetails = itemView.findViewById(R.id.timestampDetails);

        }
    }
}
