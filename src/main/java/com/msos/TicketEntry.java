package com.msos;

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

public class TicketEntry extends GridPane implements Initializable
{
    @FXML
    private Label priceValueLabel;
    
    @FXML
    private Label rowNumberLabel;
    
    @FXML
    private Label seatNumberLabel;
    
    @FXML
    private ComboBox<TicketType> ticketOptions;
    
    public TicketEntry(ObservableList<TicketType> ticketOptionsList)
    {
        super();
    
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
        
        ticketOptions.setItems(ticketOptionsList);
        ticketOptions.getSelectionModel().selectFirst();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    
    }
}
