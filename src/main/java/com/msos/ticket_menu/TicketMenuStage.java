package com.msos.ticket_menu;

import com.msos.Seat;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicketMenuStage extends Stage
{
    
    public TicketMenuStage(ObservableList<Seat> selectedSeats) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/ticket_menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        TicketMenuController controller = loader.getController();
        controller.setSelectedSeats(selectedSeats);
        
        this.setScene(scene);
    }
}