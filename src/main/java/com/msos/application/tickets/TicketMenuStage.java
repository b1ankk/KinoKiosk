package com.msos.application.tickets;

import com.msos.application.rooms.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicketMenuStage extends Stage
{
    
    public TicketMenuStage(Room room) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/ticket_menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
    
        setMaxWidth(((VBox) root).getMaxWidth());
    
        TicketMenuController controller = loader.getController();
        controller.setSelectedSeats(room.getSelectedSeats(), room.getNumber());
        
        this.setScene(scene);
    }
}
