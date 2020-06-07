package com.msos;

import com.msos.security.Password;
import com.msos.security.PasswordManager;
import com.msos.serialization.Cluster;
import com.msos.serialization.DefaultSerializer;
import com.msos.ticket_menu.TicketMenuStage;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView roomsListView;
    
    @FXML
    private ListView<SelectedSeatEntry> selectedSeatsListView;
    
    @FXML
    private Button buyTicketsButton;
    
    
    private Stage stage;
    
    private List<Room> rooms = new ArrayList<>();
    
    private Room activeRoom;
    
    
    private File lastSaveDestination;
    
    @FXML
    private void initialize()
    {
        if (activeRoom == null)
        {
            activeRoom = new Room(6, 12);
            rooms.add(activeRoom);
        }
        
        SeatsView seatsView = new SeatsView(activeRoom);
        
        activeRoom.getSelectedSeats().forEach(
            seat -> selectedSeatsListView.getItems().add(seat.getSelectedEntry())
        );
        
        activeRoom.getSelectedSeats().addListener(
            (ListChangeListener<? super Seat>) change ->
            {
                List<SelectedSeatEntry> items = selectedSeatsListView.getItems();
                while (change.next())
                {
                    for (Seat seat : change.getAddedSubList())
                    {
                        SelectedSeatEntry entry = seat.getSelectedEntry();
                        if (items.isEmpty())
                            entry.setTicketNumber(1);
                        else
                        {
                            entry.setTicketNumber(
                                items.get(items.size() - 1)
                                     .getTicketNumber() + 1
                            );
                        }
                        items.add(entry);
                    }
                    for (Seat seat : change.getRemoved())
                        items.remove(seat.getSelectedEntry());
                }
                
                // validate ticket numbers
                for (int i = 0; i < items.size(); ++i)
                    items.get(i).setTicketNumber(i + 1);
            }
        );
        
        rootBorderPane.setCenter(seatsView);
        
        buyTicketsButton.disableProperty().bind(
            activeRoom.emptyProperty()
        );
    }
    
    
    @FXML
    private void openTicketsMenu() throws IOException
    {
        TicketMenuStage ticketMenu = new TicketMenuStage(activeRoom.getSelectedSeats());
        ticketMenu.initModality(Modality.WINDOW_MODAL);
        ticketMenu.initOwner(stage);
        ticketMenu.setTitle("BUY TICKETS");
        ticketMenu.getIcons().addAll(stage.getIcons());
        ticketMenu.show();
    }
    
    @FXML
    private void open()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
            String.format("CinemaKiosk file(*%s)", App.SAVE_FILE_EXTENSION),
            "*" + App.SAVE_FILE_EXTENSION
        ));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null)
        {
            loadFromFile(file);
        }
    }
    
    private void loadFromFile(File file)
    {
        try
        {
            Cluster cluster = DefaultSerializer.readFromJson(file.toPath());
            
            PasswordManager.removeAllPasswords();
            PasswordManager.retrievePasswords(cluster);
            
            rooms.clear();
            cluster.getRooms().forEach(
                room -> rooms.add(
                    Room.fromPojo(room)
                )
            );
            
            // TODO temporary assignment -> delete later
            activeRoom = rooms.get(0);
            initialize();
            System.out.println(activeRoom.getSelectedSeats());
            
            lastSaveDestination = file;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showErrorAlert(e, "Error reading file");
        }
    }
    
    @FXML
    private void save()
    {
        if (lastSaveDestination != null)
            saveToFile(lastSaveDestination);
        else
            saveAs();
    }
    
    @FXML
    private void saveAs()
    {
        final String INITIAL_FILE_NAME = "cinema_settings" + App.SAVE_FILE_EXTENSION;
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName(INITIAL_FILE_NAME);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
            String.format("CinemaKiosk file(*%s)", App.SAVE_FILE_EXTENSION),
            "*" + App.SAVE_FILE_EXTENSION
        ));
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null)
        {
            saveToFile(file);
        }
    }
    
    private void saveToFile(File file)
    {
        Cluster cluster = new Cluster();
        cluster.setPasswords(PasswordManager.getUnmodifiableUserPasswords());
        rooms.forEach(
            room -> cluster.getRooms().add(
                Room.toPojo(room)
            )
        );
        
        try
        {
            DefaultSerializer.writeAsJson(cluster, file.toPath());
            lastSaveDestination = file;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showErrorAlert(e, "Error saving file");
        }
    }
    
    private void showErrorAlert(Exception e, String title)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(e.getMessage());
        alert.setContentText(e.toString());
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().addAll(stage.getIcons());
    
        alert.show();
    }
    
    public Stage getStage()
    {
        return stage;
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
}
