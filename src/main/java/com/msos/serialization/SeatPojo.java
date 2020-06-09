package com.msos.serialization;

import com.msos.seat_menu.Seat;

public class SeatPojo
{
    private Seat.State state;
    private int rowNumber;
    private int seatNumber;
    
    public SeatPojo()
    {
    }
    
    public SeatPojo(Seat.State state, int rowNumber, int seatNumber)
    {
        this.state = state;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }
    
    public Seat.State getState()
    {
        return state;
    }
    
    public void setState(Seat.State state)
    {
        this.state = state;
    }
    
    public int getRowNumber()
    {
        return rowNumber;
    }
    
    public void setRowNumber(int rowNumber)
    {
        this.rowNumber = rowNumber;
    }
    
    public int getSeatNumber()
    {
        return seatNumber;
    }
    
    public void setSeatNumber(int seatNumber)
    {
        this.seatNumber = seatNumber;
    }
}
