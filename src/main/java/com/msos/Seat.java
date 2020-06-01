package com.msos;

import javafx.beans.property.SimpleObjectProperty;

public class Seat
{
    enum State
    {
        OCCUPIED,
        EMPTY,
        CHOSEN
    }
    
    private SimpleObjectProperty<Seat.State> state = new SimpleObjectProperty<>();
    
    public Seat()
    {
        this(State.EMPTY);
    }
    
    public Seat(State state)
    {
        this.state.set(state);
    }
    
    public State getState()
    {
        return state.get();
    }
    
    public SimpleObjectProperty<State> stateProperty()
    {
        return state;
    }
}
