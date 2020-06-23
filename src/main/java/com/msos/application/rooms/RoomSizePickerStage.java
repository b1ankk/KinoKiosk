package com.msos.application.rooms;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoomSizePickerStage extends Stage
{
    public RoomSizePickerStage(Stage ownerStage)
    {
        initOwner(ownerStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Pick room size");
        getIcons().addAll(ownerStage.getIcons());
        setResizable(false);
    }
}
