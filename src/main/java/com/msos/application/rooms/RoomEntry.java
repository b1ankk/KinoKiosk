package com.msos.application.rooms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RoomEntry extends VBox
{
    private final int number;
    
    @FXML
    private Label numberLabel;
    
    @FXML
    private Label freeSeatsLabel;
    
    
    public RoomEntry(int number)
    {
        this.number = number;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/room_entry.fxml"));
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
        numberLabel.setText(number + ".");
    }
    
    
    
    public int getNumber()
    {
        return number;
    }
}
