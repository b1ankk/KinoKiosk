package com.msos;

import com.msos.seat_menu.*;
import com.msos.security.PasswordManager;
import com.msos.security.PasswordPromptStage;
import com.msos.serialization.Cluster;
import com.msos.serialization.DefaultSerializer;
import com.msos.ticket_menu.TicketMenuStage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class KioskController
{
    
    @FXML
    private BorderPane rootBorderPane;
    
    @FXML
    private ListView<RoomEntry> roomsListView;
    
    @FXML
    private ListView<SelectedSeatEntry> ticketListView;
    
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
            File defaultFile = new File(App.DEFAULT_FILE_PATH);
            if (defaultFile.exists())
            {
                loadFromFile(defaultFile);
            }
            else
            {
                activeRoom = new Room(6, 12);
                activeRoom.fill();
                rooms.add(activeRoom);
                loadUI();
            }
        }
    }
    
    private void loadUI()
    {
        initializeSeatsView();
        initializeTicketListView();
        
        for (Room room : rooms)
            addTicketListEvents(room);
        
        initRoomListView();
        addRoomListViewEvents();
        initBuyButton();
    }
    
    private void refreshUI()
    {
        initializeSeatsView();
        initializeTicketListView();
        initRoomListView();
        initBuyButton();
    }
    
    private void initializeSeatsView()
    {
        seatsView = new SeatsPickView(activeRoom);
        rootBorderPane.setCenter(seatsView);
    }
    
    private void initializeTicketListView()
    {
        ticketListView.getItems().clear();
        activeRoom.getSelectedSeats().forEach(
            seat -> ticketListView.getItems().add(seat.getSelectedEntry())
        );
        validateTicketNumbers();
        ticketListView.setSelectionModel(new NoSelectionModel<>());
    }
    
    private void addTicketListEvents(Room room)
    {
        room.getSelectedSeats().addListener(
            (ListChangeListener<? super Seat>) change ->
            {
                List<SelectedSeatEntry> items = ticketListView.getItems();
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
    }
    
    private void initRoomListView()
    {
        roomsListView.getItems().clear();
        roomsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        rooms.forEach(
            room -> roomsListView.getItems().add(
                room.getEntry()
            )
        );
        roomsListView.getSelectionModel().selectFirst();
    }
    
    private void addRoomListViewEvents()
    {
        roomsListView.setOnMouseClicked(
            mouseEvent ->
            {
                if (roomsListView.getSelectionModel().getSelectedIndex() != -1)
                {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    {
                        int index = roomsListView.getSelectionModel().getSelectedIndex();
                        if (rooms.get(index) != activeRoom)
                        {
                            activeRoom = rooms.get(index);
                            refreshUI();
                            roomsListView.getSelectionModel().select(index);
                        }
                    }
                }
            }
        );
        
        rooms.addListener(
            (ListChangeListener<? super Room>) change ->
            {
                while (change.next())
                {
                    change.getAddedSubList().forEach(
                        room ->
                        {
                            RoomEntry roomEntry = room.getEntry();
                            roomsListView.getItems().add(roomEntry);
                        }
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
    
    private void initBuyButton()
    {
        buyTicketsButton.disableProperty().unbind();
        buyTicketsButton.disableProperty().bind(
            activeRoom.emptyProperty()
        );
    }
    
    private void validateTicketNumbers()
    {
        List<SelectedSeatEntry> items = ticketListView.getItems();
        for (int i = 0; i < items.size(); ++i)
            items.get(i).setTicketNumber(i + 1);
    }
    
    
    @FXML
    private void openTicketsMenu() throws IOException
    {
        TicketMenuStage ticketMenu = new TicketMenuStage(activeRoom);
        ticketMenu.initModality(Modality.WINDOW_MODAL);
        ticketMenu.initOwner(stage);
        ticketMenu.setTitle("BUY TICKETS");
        ticketMenu.getIcons().addAll(stage.getIcons());
        ticketMenu.show();
    }
    
    @FXML
    private void newFile()
    {
        RoomSizePicker.RoomSize size = new RoomSizePicker.RoomSize();
        RoomSizePicker picker = new RoomSizePicker(size);
        Scene pickerScene = new Scene(picker);
        
        RoomSizePickerStage pickerStage = new RoomSizePickerStage(stage);
        pickerStage.setScene(pickerScene);
        
        pickerStage.showAndWait();
        
        if (size.getRows() > 0 && size.getColumns() > 0)
        {
            activeRoom = new Room( 1,
                size.getRows(),
                size.getColumns()
            );
            activeRoom.fill();
            
            rooms.clear();
            rooms.add(activeRoom);
            
            loadUI();
        }
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
            
            activeRoom = rooms.get(0);
            loadUI();
            
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
    
    @FXML
    private void chooseDefault()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose default file...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
            String.format("CinemaKiosk file(*%s)", App.SAVE_FILE_EXTENSION),
            "*" + App.SAVE_FILE_EXTENSION
        ));
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null && !file.equals(new File(App.DEFAULT_FILE_PATH)))
        {
            try
            {
                Files.copy(
                    file.toPath(),
                    Paths.get(App.DEFAULT_FILE_PATH),
                    StandardCopyOption.REPLACE_EXISTING
                );
            }
            catch (IOException e)
            {
                showErrorAlert(e, "Error setting default file");
            }
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
    private void newRoom()
    {
        RoomSizePicker.RoomSize size = new RoomSizePicker.RoomSize();
        
        RoomSizePickerStage pickerStage = new RoomSizePickerStage(stage);
        Scene pickerScene = new Scene(new RoomSizePicker(size));
        pickerStage.setScene(pickerScene);
        pickerStage.showAndWait();
    
        if (size.getColumns() > 0 && size.getRows() > 0)
        {
            SeatsCreateStage createStage = new SeatsCreateStage(stage);
            Room newRoom = new Room(
                rooms.size() + 1,
                size.getRows(),
                size.getColumns()
            );
            newRoom.fill();
            
            Scene createScene = new Scene(
                new SeatsCreateView(newRoom)
            );
            createStage.setScene(createScene);
            createStage.showAndWait();
            
            while (!newRoom.getSelectedSeats().isEmpty())
            {
                Seat seat = newRoom.getSelectedSeats().get(0);
                newRoom.removeSeat(
                    seat.getRowNumber(),
                    seat.getSeatNumber()
                );
                newRoom.getSelectedSeats().remove(0);
            }
            
            rooms.add(newRoom);
            activeRoom = newRoom;
            refreshUI();
            addTicketListEvents(newRoom);
            roomsListView.getSelectionModel().select(activeRoom.getEntry());
        }
    }
    
    @FXML
    private void editRoom()
    {
        Room editedRoom = new Room(activeRoom.getRowsCount(), activeRoom.getColumnsCount());
        editedRoom.fill();
        
        for (int i = 0; i < activeRoom.getRowsCount(); ++i)
        {
            for (int j = 0; j < activeRoom.getColumnsCount(); ++j)
            {
                if (activeRoom.getSeat(i, j) == null)
                    editedRoom.getSeat(i, j).setState(Seat.State.SELECTED);
            }
        }
    
        SeatsCreateStage editStage = new SeatsCreateStage(stage);
        Scene editScene = new Scene(
            new SeatsCreateView(editedRoom)
        );
        editStage.setScene(editScene);
        editStage.showAndWait();
        while (!editedRoom.getSelectedSeats().isEmpty())
        {
            Seat seat = editedRoom.getSelectedSeats().get(0);
            int row = seat.getRowNumber();
            int column = seat.getSeatNumber();
            
            if (activeRoom.getSeat(row, column) != null)
            {
                activeRoom.getSeat(row, column).setState(Seat.State.EMPTY);
                activeRoom.removeSeat(row, column);
            }
            editedRoom.getSelectedSeats().remove(0);
        }
        for (int i = 0; i < activeRoom.getRowsCount(); ++i)
        {
            for (int j = 0; j < activeRoom.getColumnsCount(); ++j)
            {
                if (activeRoom.getSeat(i, j) == null &&
                    editedRoom.getSeat(i, j).getState() != Seat.State.SELECTED)
                {
                    activeRoom.addSeat(i, j, new Seat(i, j));
                }
            }
        }
        
        refreshUI();
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
    
    @FXML
    private void clearSelectedTickets()
    {
        while (!activeRoom.getSelectedSeats().isEmpty())
        {
            activeRoom.getSelectedSeats().get(0).setState(
                Seat.State.EMPTY
            );
        }
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
