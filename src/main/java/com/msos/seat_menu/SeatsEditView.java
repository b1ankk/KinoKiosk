package com.msos.seat_menu;

import com.msos.Room;
import javafx.scene.input.MouseButton;

public class SeatsEditView extends SeatsView
{
    public SeatsEditView(Room room)
    {
        super(room);
    }
    
    @Override
    protected SeatButton createButton(Seat seat)
    {
        SeatButton seatButton = new SeatButton(seat.getState(), true);
        
        seatButton.setOnMouseClicked(
            mouseEvent ->
            {
                if (mouseEvent.getButton() == MouseButton.SECONDARY)
                    seatButton.setPseudoDisabled(
                        !seatButton.isPseudoDisabled()
                    );
            }
        );
     
        return seatButton;
    }
}
