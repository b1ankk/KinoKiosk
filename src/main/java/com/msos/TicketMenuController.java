package com.msos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TicketMenuController
{
    @FXML
    private ListView<TicketEntry> ticketListView;
    
    @FXML
    private void initialize()
    {
        ObservableList<TicketType> ticketOptions = FXCollections.observableArrayList();
        ticketOptions.addAll(
            TicketType.values()
        );
        
        
        // TEST TICKETS
        TicketEntry ticketEntry = new TicketEntry(ticketOptions);
        
        ticketListView.getItems().add(
            ticketEntry
        );
        
        ticketListView.getItems().add(
            new TicketEntry(ticketOptions)
        );
        
    }
}
