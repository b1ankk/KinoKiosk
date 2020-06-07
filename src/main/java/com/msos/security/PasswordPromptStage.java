package com.msos.security;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PasswordPromptStage extends Stage
{
    public PasswordPromptStage(Window window)
    {
        super();
 
        PasswordPrompt passwordPrompt = new PasswordPrompt();
        passwordPrompt.setWindowToClose(window);
        initOwner(window);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        
        if (window instanceof Stage)
            getIcons().addAll(((Stage)window).getIcons());
        
        Scene scene = new Scene(passwordPrompt);
        setScene(scene);
    }
}
