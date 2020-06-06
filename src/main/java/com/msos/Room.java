package com.msos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class Room
{
    private int number;
    
    private int columns;
    private int rows;
    
    private ObservableList<ObservableList<Seat>> seats;
    private ObservableList<Seat> selectedSeats;
    
    public Room(int number, int rows, int columns)
    {
        this(rows, columns);
        this.number = number;
    }
    
    public Room(int rows, int columns)
    {
        if (columns <= 0  ||  rows <= 0)
            throw new IllegalArgumentException(
                "column and row amounts must be greater than zero:" +
                " columns=" + columns + " rows=" + rows
            );
        
        this.columns = columns;
        this.rows = rows;
        this.seats = FXCollections.observableArrayList();
        this.selectedSeats = FXCollections.observableList(new LinkedList<>());
        
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
                Seat checkSeat = seats.get(y).get(x);
                if (checkSeat != null)
                    throw new UnsupportedOperationException("Room is not empty, cannot fill." +
                                                            " Seat encountered: " + checkSeat +
                                                            ", row=" + y + ", column=" + x
                    );
                Seat seat = new Seat(y, x);
                addSeat(y, x, seat);
            }
        }
    }
    
    public int getNumber()
    {
        return number;
    }
    
    public int getColumnsCount()
    {
        return columns;
    }
    
    public int getRowsCount()
    {
        return rows;
    }
    
    public Seat getSeat(int row, int column)
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
    
    public ObservableList<Seat> getSelectedSeats()
    {
        return selectedSeats;
    }
    
    public void addSeat(int row, int column, Seat seat)
    {
        if (getSeat(row, column) != null)
            throw new UnsupportedOperationException("Place already occupied: row=" + row + " column=" + column);
        
        seat.stateProperty().addListener(
            (observableValue, oldState, newState) ->
            {
                if (newState == Seat.State.SELECTED && oldState != Seat.State.SELECTED)
                    selectedSeats.add(seat);
                else if (newState != Seat.State.SELECTED && oldState == Seat.State.SELECTED)
                    selectedSeats.remove(seat);
                System.out.println(selectedSeats);
            }
        );
        
        seats.get(row).set(column, seat);
    }
}
