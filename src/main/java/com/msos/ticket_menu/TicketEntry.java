package com.msos.ticket_menu;

import com.msos.seat_menu.Seat;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicketEntry extends GridPane
{
    @FXML
    private Label roomNumberLabel;
    
    @FXML
    private Label priceValueLabel;
    
    @FXML
    private Label rowNumberLabel;
    
    @FXML
    private Label seatNumberLabel;
    
    @FXML
    private ComboBox<TicketType> ticketOptions;
    
    private DoubleProperty ticketPrice;
    
    private Seat seat;
    private ObservableList<TicketType> ticketOptionsList;
    
    public TicketEntry(Seat seat, ObservableList<TicketType> ticketOptionsList)
    {
        this(seat, ticketOptionsList, 1);
    }
    
    public TicketEntry(Seat seat, ObservableList<TicketType> ticketOptionsList, int roomNumber)
    {
        super();
        this.seat = seat;
        this.ticketOptionsList = ticketOptionsList;
    
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxmls/ticket_entry.fxml")
        );
        
        loader.setRoot(this);
        loader.setController(this);
    
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    
        roomNumberLabel.setText(String.format("Room %d.", roomNumber));
    }
    
    @FXML
    private void initialize()
    {
        ticketOptions.setItems(ticketOptionsList);
        ticketOptions.getSelectionModel().selectFirst();

        ticketPrice = new SimpleDoubleProperty(ticketOptions.getValue().getPrice());
        ticketOptions.valueProperty().addListener(
            (observableValue, oldType, newType) ->
                ticketPrice.set(newType.getPrice())
        );

        priceValueLabel.textProperty().bind(
            ticketPrice.asString()
        );
        
        rowNumberLabel.textProperty().bind(seat.rowNumberProperty().asString());
        seatNumberLabel.textProperty().bind(seat.seatNumberProperty().asString());
    }
    
    public double getTicketPrice()
    {
        return ticketPrice.get();
    }
    
    public DoubleProperty ticketPriceProperty()
    {
        return ticketPrice;
    }
}
