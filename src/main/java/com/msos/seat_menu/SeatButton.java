package com.msos.seat_menu;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ToggleButton;

public class SeatButton extends ToggleButton
{
    private static final PseudoClass PSEUDO_DISABLED_CLASS = PseudoClass.getPseudoClass("pseudo-disabled");
    
    private final BooleanProperty pseudoDisabled = new BooleanPropertyBase(false) {
    
        @Override
        protected void invalidated()
        {
            pseudoClassStateChanged(PSEUDO_DISABLED_CLASS, get());
        }
    
        @Override
        public Object getBean()
        {
            return SeatButton.this;
        }
    
        @Override
        public String getName()
        {
            return "pseudoDisabled";
        }
    };
    
    private final ObjectProperty<Seat.State> seatState = new SimpleObjectProperty<>();
    
    private boolean usingPseudoDisable = false;
    
    public SeatButton()
    {
        this(Seat.State.EMPTY);
    }
    
    public SeatButton(Seat.State initialState)
    {
        getStylesheets().add("/styles/seat-button.css");
        getStyleClass().add("seat-button");
        
        selectedProperty().addListener(
            (observableValue, wasSelected, isSelected) ->
            {
                if (isSelected != wasSelected)
                    if (isSelected)
                    {
                        setPseudoDisabled(false);
                        setSeatState(Seat.State.SELECTED);
                    }
                    else if (!isDisabled() && !isPseudoDisabled())
                    {
                        setSeatState(Seat.State.EMPTY);
                    }
            }
        );
        
        disabledProperty().addListener(
            (observableValue, wasDisabled, isDisabled) ->
            {
                if (isDisabled != wasDisabled)
                {
                    setPseudoDisabled(false);
    
                    if (isDisabled)
                       setSeatState(Seat.State.OCCUPIED);
                    else if (isSelected())
                        setSeatState(Seat.State.SELECTED);
                    else
                        setSeatState(Seat.State.EMPTY);
                }
            }
        );
        
        pseudoDisabledProperty().addListener(
            (observableValue, wasDisabled, isDisabled) ->
            {
                if (isDisabled != wasDisabled)
                {
                    if (isDisabled != wasDisabled)
                    {
                        setDisable(false);
        
                        if (isDisabled)
                            setSeatState(Seat.State.OCCUPIED);
                        else if (isSelected())
                            setSeatState(Seat.State.SELECTED);
                        else
                            setSeatState(Seat.State.EMPTY);
                    }
                }
            }
        );
        
        seatStateProperty().addListener(
            (observableValue, oldState, newState) ->
            {
                if (newState != oldState)
                {
                    if (newState == Seat.State.SELECTED)
                    {
                        setDisable(false);
                        setPseudoDisabled(false);
                        setSelected(true);
                    }
                    else if (newState == Seat.State.EMPTY)
                    {
                        setDisable(false);
                        setPseudoDisabled(false);
                        setSelected(false);
                    }
                    else
                    {
                        setSelected(false);
                        
                        if (usingPseudoDisable)
                            setPseudoDisabled(true);
                        else
                            setDisable(true);
                    }
                }
            }
        );
        
        
        setSeatState(initialState);
    }
    
    public boolean isPseudoDisabled()
    {
        return pseudoDisabled.get();
    }
    
    public BooleanProperty pseudoDisabledProperty()
    {
        return pseudoDisabled;
    }
    
    public void setPseudoDisabled(boolean pseudoDisabled)
    {
        this.pseudoDisabled.set(pseudoDisabled);
    }
    
    public Seat.State getSeatState()
    {
        return seatState.get();
    }
    
    public ObjectProperty<Seat.State> seatStateProperty()
    {
        return seatState;
    }
    
    public void setSeatState(Seat.State seatState)
    {
        this.seatState.set(seatState);
    }
    
    public boolean isUsingPseudoDisable()
    {
        return usingPseudoDisable;
    }
    
    public void setUsingPseudoDisable(boolean usingPseudoDisable)
    {
        this.usingPseudoDisable = usingPseudoDisable;
    }
}
