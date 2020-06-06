package com.msos.ticket_menu;

import com.msos.Seat;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TicketMenuController
{
    @FXML
    private ListView<TicketEntry> ticketListView;
    
    
    private ObservableList<Seat> selectedSeats;
    
    
    @FXML
    private void initialize()
    {
    
    }
    
    
    public ObservableList<Seat> getSelectedSeats()
    {
        return selectedSeats;
    }
    
    public void setSelectedSeats(ObservableList<Seat> selectedSeats)
    {
        this.selectedSeats = selectedSeats;
    
        ObservableList<TicketType> ticketOptions = FXCollections.observableArrayList();
        ticketOptions.addAll(
            TicketType.values()
        );
        
        for (Seat seat : selectedSeats)
        {
            ticketListView.getItems().add(
                new TicketEntry(
                    seat, ticketOptions
                )
            );
        }
    }
}
