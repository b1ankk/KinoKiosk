package com.msos.ticket_menu;

import com.msos.Seat;
import javafx.application.Platform;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class TicketMenuController
{
    @FXML
    private ListView<TicketEntry> ticketListView;
    
    @FXML
    private Label toPayValue;
    
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
            TicketEntry entry = new TicketEntry(seat, ticketOptions);
            entry.ticketPriceProperty().addListener(
                (observableValue, oldPrice, newPrice) ->
                    calculateFullPrice()
            );
            
            ticketListView.getItems().add(
                entry
            );
        }
        
        calculateFullPrice();
    }
    
    @FXML
    private void finalizeTransaction(ActionEvent e)
    {
        while (!selectedSeats.isEmpty())
            selectedSeats.get(0).setState(Seat.State.OCCUPIED);
    
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cancel(ActionEvent e)
    {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
    
    private void calculateFullPrice()
    {
        double price = 0;
        for (TicketEntry entry : ticketListView.getItems())
        {
            price += entry.getTicketPrice();
        }
        
        toPayValue.textProperty().setValue(
            String.format("%.2f", price)
        );
    }
}
