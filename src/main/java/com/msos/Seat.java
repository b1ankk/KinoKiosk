package com.msos;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleObjectProperty;

public class Seat
{
    enum State
    {
        OCCUPIED,
        EMPTY,
        SELECTED
    }
    
    private final SimpleObjectProperty<Seat.State> state = new SimpleObjectProperty<>();
    
    private final ReadOnlyIntegerProperty rowNumber;
    
    private final ReadOnlyIntegerProperty seatNumber;
    
    private SelectedSeatEntry selectedEntry;
    
    public Seat()
    {
        this(0, 0);
    }
    
    public Seat(int rowNumber, int seatNumber)
    {
        this(State.EMPTY, rowNumber, seatNumber);
    }
    
    public Seat(State state, int rowNumber, int seatNumber)
    {
        this.state.set(state);
        this.rowNumber = new ReadOnlyIntegerWrapper(rowNumber);
        this.seatNumber = new ReadOnlyIntegerWrapper(seatNumber);
    }
    
    public State getState()
    {
        return state.get();
    }
    
    public SimpleObjectProperty<State> stateProperty()
    {
        return state;
    }
    
    void setState(State state)
    {
        this.state.set(state);
    }
    
    
    public int getRowNumber()
    {
        return rowNumber.get();
    }
    
    public ReadOnlyIntegerProperty rowNumberProperty()
    {
        return rowNumber;
    }
    
    public int getSeatNumber()
    {
        return seatNumber.get();
    }
    
    public ReadOnlyIntegerProperty seatNumberProperty()
    {
        return seatNumber;
    }
    
    public SelectedSeatEntry getSelectedEntry()
    {
        if (selectedEntry == null)
            selectedEntry = new SelectedSeatEntry(rowNumber.get(), seatNumber.get());
            
        return selectedEntry;
    }
}
