package com.msos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SeatsView extends StackPane implements Initializable
{
    private static final double PREF_COLUMN_WIDTH = 32;
    
    
    @FXML
    private GridPane seatsGrid;
    
//    private ObservableList<ObservableList<Seat>> seatsList;
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
        ObservableList<ColumnConstraints> columnConstraintsList = seatsGrid.getColumnConstraints();
        columnConstraintsList.clear();
        for (int i = 0; i < room.getColumnsCount(); ++i)
        {
            ColumnConstraints cc = new ColumnConstraints();
            columnConstraintsList.add(cc);
        }
        
        ObservableList<RowConstraints> rowConstraintsList = seatsGrid.getRowConstraints();
        rowConstraintsList.clear();
    
        for (int i = 0; i < room.getRowsCount(); ++i)
        {
            RowConstraints rc = new RowConstraints();
            rowConstraintsList.add(rc);
        }
    
        for (int i = 0; i < room.getColumnsCount(); ++i)
        {
            for (int j = 0; j < room.getRowsCount(); ++j)
            {
                Seat seat;
                if ((seat = room.getSeat(j, i)) != null)
                {
                    ToggleButton tb = new ToggleButton();
                    tb.getStylesheets().add("/styles/chair-button.css");
                    tb.getStyleClass().add("chair-button");
    
                    switch (seat.getState())
                    {
                        case EMPTY -> {tb.setSelected(false); tb.setDisable(false);}
                        case SELECTED -> {tb.setSelected(true); tb.setDisable(false);}
                        case OCCUPIED -> {tb.setSelected(false); tb.setDisable(true);}
                    }
                    
                    tb.selectedProperty().addListener(
                        (selectedProperty, oldValue, newValue) ->
                        {
                            if (newValue)
                                seat.setState(Seat.State.SELECTED);
                            else
                                seat.setState(Seat.State.EMPTY);
                        }
                    );
                    
                    tb.disabledProperty().addListener(
                        (disabledProperty, oldValue, newValue) ->
                        {
                            if (newValue)
                                seat.setState(Seat.State.OCCUPIED);
                            else if (tb.isSelected())
                                seat.setState(Seat.State.SELECTED);
                            else
                                seat.setState(Seat.State.EMPTY);
                        }
                    );
                    
                    seatsGrid.add(tb, i, j);
                }
                else
                {
                    seatsGrid.getColumnConstraints().get(i).setMinWidth(PREF_COLUMN_WIDTH);
                }

            }
        }
        
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
}
