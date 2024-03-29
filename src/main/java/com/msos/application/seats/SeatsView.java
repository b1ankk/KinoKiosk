package com.msos.application.seats;

import com.msos.application.rooms.Room;
import com.msos.application.seats.seat.Seat;
import com.msos.application.seats.seat.SeatButton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class SeatsView extends StackPane implements Initializable
{
    private static final double MIN_COLUMN_WIDTH = 32;
    private static final double MIN_ROW_HEIGHT = 32;
    
    
    @FXML
    private GridPane seatsGrid;
    
    private Room room;
    
    public SeatsView(Room room)
    {
        super();
        
        this.room = room;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/seats.fxml"));
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
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // CREATE COLUMNS AND ROWS
        
        ObservableList<ColumnConstraints> columnConstraintsList = seatsGrid.getColumnConstraints();
        columnConstraintsList.clear();
        for (int i = 0; i < room.getColumnsCount(); ++i)
        {
            ColumnConstraints cc = new ColumnConstraints();
            columnConstraintsList.add(cc);
            cc.setMinWidth(MIN_COLUMN_WIDTH);
        }
        
        ObservableList<RowConstraints> rowConstraintsList = seatsGrid.getRowConstraints();
        rowConstraintsList.clear();
    
        for (int i = 0; i < room.getRowsCount(); ++i)
        {
            RowConstraints rc = new RowConstraints();
            rowConstraintsList.add(rc);
            rc.setMinHeight(MIN_ROW_HEIGHT);
        }
    
        // POPULATE COLUMNS AND ROWS WITH BUTTONS
        
        for (int i = 0; i < room.getColumnsCount(); ++i)
        {
            for (int j = 0; j < room.getRowsCount(); ++j)
            {
                Seat seat;
                if ((seat = room.getSeat(j, i)) != null)
                {
                    SeatButton sb = createButton(seat);
                    
                    sb.setFocusTraversable(false);
                    
                    seat.stateProperty().bindBidirectional(
                        sb.seatStateProperty()
                    );
                    
                    seatsGrid.add(sb, i, j);
                }
            }
        }
    }
    
    protected SeatButton createButton(Seat seat)
    {
        return new SeatButton(seat.getState());
    }
    
    public int getRows()
    {
        return seatsGrid.getRowCount();
    }
    
    public int getColumns()
    {
        return seatsGrid.getColumnCount();
    }
    
    public ObservableList<Node> getSeatButtons()
    {
        return seatsGrid.getChildren();
    }
    
    protected GridPane getSeatsGrid()
    {
        return seatsGrid;
    }
    
    protected Room getRoom()
    {
        return room;
    }
}
