package com.msos.serialization;

import com.msos.Seat;

import java.util.List;

public class RoomPojo
{
    private int number;
    private int rows;
    private int columns;
    private boolean empty;
    
    private List<List<SeatPojo>> seats;
    
    public RoomPojo()
    {
    }
    
    public RoomPojo(int number, int rows, int columns, boolean empty,
                    List<List<SeatPojo>> seats, List<SeatPojo> selectedSeats)
    {
        this.number = number;
        this.columns = columns;
        this.rows = rows;
        this.empty = empty;
        this.seats = seats;
//        this.selectedSeats = selectedSeats;   can be deduced
    }
    
    public int getNumber()
    {
        return number;
    }
    
    public void setNumber(int number)
    {
        this.number = number;
    }
    
    public int getColumns()
    {
        return columns;
    }
    
    public void setColumns(int columns)
    {
        this.columns = columns;
    }
    
    public int getRows()
    {
        return rows;
    }
    
    public void setRows(int rows)
    {
        this.rows = rows;
    }
    
    public boolean isEmpty()
    {
        return empty;
    }
    
    public void setEmpty(boolean empty)
    {
        this.empty = empty;
    }
    
    public List<List<SeatPojo>> getSeats()
    {
        return seats;
    }
    
    public void setSeats(List<List<SeatPojo>> seats)
    {
        this.seats = seats;
    }
}
