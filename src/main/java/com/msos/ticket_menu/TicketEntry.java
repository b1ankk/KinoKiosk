package com.msos.ticket_menu;

import com.msos.Seat;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketEntry extends GridPane
{
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