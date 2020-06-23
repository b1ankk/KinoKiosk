package com.msos.application.seats;

import com.msos.application.rooms.Room;
import com.msos.application.seats.seat.Seat;
import com.msos.application.seats.seat.SeatButton;
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
