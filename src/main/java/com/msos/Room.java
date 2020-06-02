package com.msos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room
{
    private int columns;
    private int rows;
    
    private ObservableList<ObservableList<Seat>> seats;
    
    public Room(int columns, int rows)
    {
        if (columns <= 0  ||  rows <= 0)
            throw new IllegalArgumentException(
                "columns and rows sizes must be greater than zero:" +
                " columns=" + columns + " rows=" + rows
            );
        
        this.columns = columns;
        this.rows = rows;
        this.seats = FXCollections.observableArrayList();
        
        for (int y = 0; y < rows; ++y)
        {
            ObservableList<Seat> seatColumnList = FXCollections.observableArrayList();
            seats.add(seatColumnList);
            
            for (int x = 0; x < columns; ++x)
                seatColumnList.add(null);
        }
    }
    
    public void fill()
    {
        for (int y = 0; y < rows; ++y)
        {
            for (int x = 0; x < columns; ++x)
            {
                Seat seat = seats.get(y).get(x);
                if (seat != null)
                    throw new UnsupportedOperationException("Room is not empty, cannot fill." +
                                                            " Seat encountered: " + seat +
                                                            ", row=" + y + ", column=" + x
                    );
                
                seats.get(y).set(x, new Seat());
            }
        }
    }
    
    
    public int getColumnsCount()
    {
        return columns;
    }
    
    public int getRowsCount()
    {
        return rows;
    }
    
    public Seat getSeat(int column, int row)
    {
        try
        {
            return seats.get(row).get(column);
        }
        catch (NullPointerException e)
        {
            if (column < columns)
                return null;
            else
                throw new IndexOutOfBoundsException("Index out of bounds: " + column +
                                                    ", room's columns count: " + columns
                );
        }
    }
    
    
}
