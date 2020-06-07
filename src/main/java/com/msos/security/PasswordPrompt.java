package com.msos.security;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;

public class PasswordPrompt extends VBox
{
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
    
    private Window windowToClose;
    
    
    public PasswordPrompt()
    {
        super();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/password-prompt.fxml"));
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
                        windowToClose.hide();
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
    }
    
    
    public Window getWindowToClose()
    {
        return windowToClose;
    }
    
    public void setWindowToClose(Window windowToClose)
    {
        this.windowToClose = windowToClose;
    }
}
