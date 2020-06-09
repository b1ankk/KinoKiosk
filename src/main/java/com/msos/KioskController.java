package com.msos;

import com.msos.seat_menu.*;
import com.msos.security.PasswordManager;
import com.msos.security.PasswordPromptStage;
import com.msos.serialization.Cluster;
import com.msos.serialization.DefaultSerializer;
import com.msos.ticket_menu.TicketMenuStage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView<RoomEntry> roomsListView;
    
    @FXML
    private ListView<SelectedSeatEntry> selectedSeatsListView;
    
    @FXML
    private Button buyTicketsButton;
    
    
    private Stage stage;
    
    private ObservableList<Room> rooms = FXCollections.observableArrayList();
    
    private SeatsPickView seatsView;
    
    private Room activeRoom;
    
    private File lastSaveDestination;
    
    @FXML
    private void initialize()
    {
        if (activeRoom == null)
        {
            activeRoom = new Room(6, 12);
            activeRoom.fill();
            rooms.add(activeRoom);
        }
        
        initSeatsView();
        resetBuyButton();
        initRoomsList();
        
    }
    
    private void resetBuyButton()
    {
        buyTicketsButton.disableProperty().unbind();
        buyTicketsButton.disableProperty().bind(
            activeRoom.emptyProperty()
        );
    }
    
    private void initRoomsList()
    {
        roomsListView.getItems().clear();
        
        roomsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomsListView.getSelectionModel().clearSelection();
        
        rooms.forEach(
            room -> roomsListView.getItems().add(
                room.getEntry()
            )
        );
        
        rooms.addListener(
            (ListChangeListener<? super Room>) change ->
            {
                while (change.next())
                {
                    change.getAddedSubList().forEach(
                        room -> roomsListView.getItems().add(
                            room.getEntry()
                        )
                    );
                    change.getRemoved().forEach(
                        room -> roomsListView.getItems().remove(
                            room.getEntry()
                        )
                    );
                }
            }
        );
    }
    
    private void initSeatsView()
    {
        seatsView = new SeatsPickView(activeRoom);
        selectedSeatsListView.getItems().clear();
        
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
                        
                        entry.setTicketNumber(items.size() + 1);
                        
                        items.add(entry);
                    }
                    for (Seat seat : change.getRemoved())
                        items.remove(seat.getSelectedEntry());
                }
            
                validateTicketNumbers();
            }
        );
        
        validateTicketNumbers();
    
        rootBorderPane.setCenter(seatsView);
    }
    
    private void validateTicketNumbers()
    {
        List<SelectedSeatEntry> items = selectedSeatsListView.getItems();
        for (int i = 0; i < items.size(); ++i)
            items.get(i).setTicketNumber(i + 1);
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
            
            // TODO: temporary assignment -> delete later
            activeRoom = rooms.get(0);
            initSeatsView();
            resetBuyButton();
            initRoomsList();
            
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
    
    @FXML
    private void close()
    {
        stage.fireEvent(
            new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)
        );
    }
    
    @FXML
    private void editSeats()
    {
        PasswordPromptStage pps = new PasswordPromptStage(
            stage,
            () ->
            {
                SeatsEditStage editStage = new SeatsEditStage(stage, activeRoom);
                editStage.show();
            }
        );
        pps.show();
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
