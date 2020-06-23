package com.msos.application.seats;

import com.msos.util.MySubStage;
import com.msos.application.rooms.Room;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SeatsEditStage extends MySubStage
{
    public SeatsEditStage(Stage ownerStage, Room activeRoom)
    {
        super(ownerStage);
        setTitle("Edit Seats' States");
        
        SeatsView seatsView = new SeatsEditView(activeRoom);
        Scene scene = new Scene(seatsView);
        setScene(scene);
    }
}
