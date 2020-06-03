package com.msos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectedSeatEntry extends VBox implements Initializable
{
    @FXML
    private Label ticketNumberValueLabel;
    
    @FXML
    private Label rowNumberValueLabel;
    
    @FXML
    private Label seatNumberValueLabel;
    
    
    private final IntegerProperty ticketNumber;
    private final ReadOnlyIntegerProperty rowNumber;
    private final ReadOnlyIntegerProperty seatNumber;
    
    
    public SelectedSeatEntry()
    {
        this(0, 0);
    }
    
    public SelectedSeatEntry(int rowNumber, int seatNumber)
    {
        this(rowNumber, seatNumber, 0);
    }
    
    public SelectedSeatEntry(int rowNumber, int seatNumber, int ticketNumber)
    {
        super();
    
        this.ticketNumber = new SimpleIntegerProperty(ticketNumber);
        this.rowNumber = new ReadOnlyIntegerWrapper(rowNumber);
        this.seatNumber = new ReadOnlyIntegerWrapper(seatNumber);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/selected-seat-entry.fxml"));
        loader.setRoot(this);
        loader.setController(this);
    
        try
        {
            loader.load();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ticketNumberValueLabel.textProperty().bind(ticketNumber.asString());
        rowNumberValueLabel.textProperty().bind(rowNumber.asString());
        seatNumberValueLabel.textProperty().bind(seatNumber.asString());
    }
    
    
    public int getTicketNumber()
    {
        return ticketNumber.get();
    }
    
    public IntegerProperty ticketNumberProperty()
    {
        return ticketNumber;
    }
    
    public int getRowNumber()
    {
        return rowNumber.get();
    }
    
    public ReadOnlyIntegerProperty rowNumberProperty()
    {
        return rowNumber;
    }
    
    public int getSeatNumber()
    {
        return seatNumber.get();
    }
    
    public ReadOnlyIntegerProperty seatNumberProperty()
    {
        return seatNumber;
    }
    
    public void setTicketNumber(int ticketNumber)
    {
        this.ticketNumber.set(ticketNumber);
    }
}
