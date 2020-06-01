package com.msos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/kiosk.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/ticket_menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle("Kiosk");
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
    }
}
