package com.msos;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView roomsListView;
    
    private Room activeRoom;
    
    
    
    @FXML
    private void initialize()
    {
        activeRoom = new Room(12, 6);
        activeRoom.fill();
        SeatsView seatsView = new SeatsView(activeRoom);
        rootBorderPane.setCenter(seatsView);
        
    }
    
}
