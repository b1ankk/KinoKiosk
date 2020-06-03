package com.msos;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView roomsListView;
    
    @FXML
    private ListView<SelectedSeatEntry> selectedSeatsListView;
    
    
    private Room activeRoom;
    
    
    
    @FXML
    private void initialize()
    {
        activeRoom = new Room(6, 12);
        activeRoom.fill();
        SeatsView seatsView = new SeatsView(activeRoom);
        
        activeRoom.getSelectedSeats().addListener(
            (ListChangeListener<? super Seat>) change ->
            {
                List<SelectedSeatEntry> items = selectedSeatsListView.getItems();
                while (change.next())
                {
                    for (Seat seat : change.getAddedSubList())
                    {
                        SelectedSeatEntry entry = seat.getSelectedEntry();
                        if (items.isEmpty())
                            entry.setTicketNumber(1);
                        else
                        {
                            entry.setTicketNumber(
                                items.get(items.size() - 1)
                                     .getTicketNumber() + 1
                            );
                        }
                        items.add(entry);
                    }
                    for (Seat seat : change.getRemoved())
                        items.remove(seat.getSelectedEntry());
                }
                
                // validate ticket numbers
                for (int i = 0; i < items.size(); ++i)
                    items.get(i).setTicketNumber(i + 1);
            }
        );
        
        rootBorderPane.setCenter(seatsView);
        
        roomsListView.getItems().add(new SelectedSeatEntry());
    }
    
}
