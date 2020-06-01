package com.msos;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView roomsView;
    
    @FXML
    private void initialize()
    {
        SeatsView seatsView = new SeatsView(20, 8);
        rootBorderPane.setCenter(seatsView);
        
    }
    
    public double getRootWidth()
    {
        return rootBorderPane.getWidth();
    }
    
}
