package com.msos.security;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PasswordPromptStage extends Stage
{
    public PasswordPromptStage(Window parentWindow, PasswordPrompt.OnVerifiedEvent event)
    {
        super();
 
        PasswordPrompt passwordPrompt = new PasswordPrompt();
        passwordPrompt.setOnVerifiedEvent(
            event
        );
        initOwner(parentWindow);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        setTitle("Authentication required");
        
        if (parentWindow instanceof Stage)
            getIcons().addAll(((Stage)parentWindow).getIcons());
        
        Scene scene = new Scene(passwordPrompt);
        setScene(scene);
    }
}
