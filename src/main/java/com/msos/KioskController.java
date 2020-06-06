package com.msos;

import com.msos.ticket_menu.TicketMenuStage;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView roomsListView;
    
    @FXML
    private ListView<SelectedSeatEntry> selectedSeatsListView;
    
    @FXML
    private Button buyTicketsButton;
    
    
    private Stage stage;
    
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
        
    }
    
    
    @FXML
    private void openTicketsMenu() throws IOException
    {
        TicketMenuStage ticketMenu = new TicketMenuStage(activeRoom.getSelectedSeats());
        ticketMenu.initModality(Modality.WINDOW_MODAL);
        ticketMenu.initOwner(stage);
        ticketMenu.setTitle("BUY TICKETS");
        ticketMenu.getIcons().addAll(stage.getIcons());
        ticketMenu.show();
    }
    
    
    public Stage getStage()
    {
        return stage;
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}
