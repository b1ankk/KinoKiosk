package com.msos;

import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MySubStage extends Stage
{
    public MySubStage(Stage ownerStage)
    {
        initOwner(ownerStage);
        initModality(Modality.WINDOW_MODAL);
    
        getIcons().addAll(ownerStage.getIcons());
    
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    
        widthProperty().addListener(
            (observableValue, wOld, wNew) ->
            {
                if ((wNew.doubleValue() > screen.getWidth() ||
                    getHeight() > screen.getHeight()) &&
                    !isMaximized())
                {
                    setMaximized(true);
                }
            }
        );
        
        heightProperty().addListener(
            (observableValue, hOld, hNew) ->
            {
                if ((hNew.doubleValue() > screen.getHeight() ||
                    getWidth() > screen.getWidth()) &&
                    !isMaximized())
                {
                    setMaximized(true);
                }
            }
        );
    }
}
