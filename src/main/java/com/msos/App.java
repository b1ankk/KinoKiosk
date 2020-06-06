package com.msos;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.image4j.codec.ico.ICODecoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends Application
{
    private final String UI_PATH = "/fxmls/kiosk.fxml";
    private final String ICO_PATH = "/icons/ticket_gimp.ico";
    
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UI_PATH));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/ticket_menu.fxml"));
        Parent root = loader.load();
        KioskController controller = loader.getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Kiosk");
        stage.getIcons().addAll(loadIcons());
        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();
    }
    
    private List<Image> loadIcons()
    {
        List<BufferedImage> bufferedImages = null;
    
        try
        {
            bufferedImages = ICODecoder.read(getClass().getResourceAsStream(ICO_PATH));
        }
        catch (IOException e)
        {
            System.err.println("Error reading icon file: " + ICO_PATH);
        }
    
        List<Image> images = new ArrayList<>();
        
        if (bufferedImages != null)
            for (BufferedImage bfImage : bufferedImages)
                images.add(SwingFXUtils.toFXImage(bfImage, null));
        
        return images;
    }
}
