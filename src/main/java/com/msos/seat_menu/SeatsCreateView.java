package com.msos.seat_menu;

import com.msos.Room;

public class SeatsCreateView extends SeatsView
{
    int a  = 0;
    
    public SeatsCreateView(Room room)
    {
        super(room);
    }
    
    @Override
    protected SeatButton createButton(Seat seat)
    {
        SeatButton seatButton = new SeatButton(seat.getState());
        seatButton.setOnAction(
            e ->
                seatButton.setOpacity(
                    seatButton.getOpacity() == 1 ? 0 : 1
                )
        );
        
        if (seat.getState() == Seat.State.SELECTED)
            seatButton.setOpacity(0);
        
        return seatButton;
    }
}
