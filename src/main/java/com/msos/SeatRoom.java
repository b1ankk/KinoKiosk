package com.msos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SeatRoom
{
    private int columns;
    private int rows;
    
    private ObservableList<ObservableList<Seat>> seats;
    
    public SeatRoom(int columns, int rows)
    {
        this.columns = columns;
        this.rows = rows;
        this.seats = FXCollections.observableArrayList();
    }
}
