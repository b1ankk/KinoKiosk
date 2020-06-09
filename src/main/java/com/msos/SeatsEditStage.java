package com.msos;

import com.msos.seat_menu.SeatsEditView;
import com.msos.seat_menu.SeatsView;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SeatsEditStage extends Stage
{
    public SeatsEditStage(Window ownerWindow, Room activeRoom)
    {
        SeatsView seatsView = new SeatsEditView(activeRoom);
        Pane pane = new Pane();
        pane.getChildren().add(seatsView);
    
        Scene scene = new Scene(pane);
    
        initOwner(ownerWindow);
        initModality(Modality.WINDOW_MODAL);
        setResizable(false);
        setTitle("Edit Seats' States");
        
        if (ownerWindow instanceof Stage)
            getIcons().addAll(((Stage)ownerWindow).getIcons());
        
        setScene(scene);
    }
}
