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
    
    private ObservableList<ObservableList<Seat>> seatsList;
    
    public SeatsView(ObservableList<ObservableList<Seat>> seatsList)
    {
        super();
        
        this.seatsList = seatsList;
        
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
        for (int i = 0; i < ; ++i)
        {
            ColumnConstraints cc = new ColumnConstraints();
            columnConstraintsList.add(cc);
        }
        
        ObservableList<RowConstraints> rowConstraintsList = seatsGrid.getRowConstraints();
        rowConstraintsList.clear();
        for (int i = 0; i < rows; ++i)
        {
            RowConstraints rc = new RowConstraints();
            rowConstraintsList.add(rc);
        }

        for (int i = 0; i < columns; ++i)
        {
            if (i < columns / 2 - 1  ||  i >= columns / 2 + 1)
            
            for (int j = 0; j < rows; ++j)
            {
                ToggleButton tb = new ToggleButton();
                tb.getStylesheets().add("/styles/chair-button.css");
                tb.getStyleClass().add("chair-button");
                seatsGrid.add(tb, i, j);
    
                if (Math.random() < 0.33)
                    tb.setDisable(true);
            }
            else
            {
                seatsGrid.getColumnConstraints().get(i).setMinWidth(PREF_COLUMN_WIDTH);
            }
        }
        
    }
    
    public int getRows()
    {
        return rows;
    }
    
    public int getColumns()
    {
        return columns;
    }
    
    public ObservableList<Node> getSeatButtons()
    {
        return seatsGrid.getChildren();
    }
}
