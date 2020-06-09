package com.msos.seat_menu;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
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
    
    public SeatButton()
    {
        getStylesheets().add("/styles/seat-button.css");
        getStyleClass().add("seat-button");
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
}
