package com.msos.security;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PasswordPrompt extends VBox
{
    public interface OnVerifiedEvent
    {
        void onVerified();
    }
    
    @FXML
    private TextField userField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Label incorrectInputLabel;
    
    
    private OnVerifiedEvent onVerifiedEvent;
    
    public PasswordPrompt()
    {
        super();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/password_prompt.fxml"));
        loader.setRoot(this);
        loader.setController(this);
    
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void initialize()
    {
        confirmButton.setOnAction(
            e ->
            {
                String user = userField.getText();
                String password = passwordField.getText();
    
                try
                {
                    if (PasswordManager.verify(user, password))
                    {
                        ((Node) e.getSource()).getScene().getWindow().hide();
                        onVerifiedEvent.onVerified();
                    }
                    else
                    {
                        incorrectInputLabel.setVisible(true);
                    }
                }
                catch (PasswordVerificationException ex)
                {
                    ex.printStackTrace();
                }
            }
        );
        
        cancelButton.setOnAction(
            e -> ((Node) e.getSource()).getScene().getWindow().hide()
        );
    
        Platform.runLater(userField::requestFocus);
    }
    
    public void setOnVerifiedEvent(OnVerifiedEvent event)
    {
        this.onVerifiedEvent = event;
    }
}
