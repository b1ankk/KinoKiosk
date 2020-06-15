package com.msos;

import com.msos.seat_menu.Seat;
import com.msos.serialization.RoomPojo;
import com.msos.serialization.SeatPojo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Room
{
    private int number;
    
    private final int rows;
    private final int columns;
    
    private BooleanProperty empty;
    
    private ObservableList<ObservableList<Seat>> seats;
    private ObservableList<Seat> selectedSeats;
    
    private RoomEntry entry;
    
    public Room(int rows, int columns)
    {
        this(1, rows, columns);
    }
    
    public Room(int number, int rows, int columns)
    {
        if (columns <= 0  ||  rows <= 0)
            throw new IllegalArgumentException(
                "column and row amounts must be greater than zero:" +
                " columns=" + columns + " rows=" + rows
            );
        
        this.number = number;
        this.columns = columns;
        this.rows = rows;
        this.seats = FXCollections.observableArrayList();
        this.selectedSeats = FXCollections.observableList(new LinkedList<>());
        this.empty = new SimpleBooleanProperty(selectedSeats.isEmpty());
        this.entry = new RoomEntry(number);
        
        selectedSeats.addListener(
            (ListChangeListener<Seat>) change -> empty.set(selectedSeats.isEmpty())
        );
        
        for (int y = 0; y < rows; ++y)
        {
            ObservableList<Seat> seatRowList = FXCollections.observableArrayList();
            seats.add(seatRowList);
            
            for (int x = 0; x < columns; ++x)
                seatRowList.add(null);
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
    
    public void removeSeat(int row, int column)
    {
        seats.get(row).set(column, null);
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
        
        if (seat == null)
            return;
        
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
        
        if (seat.getState() == Seat.State.SELECTED)
            selectedSeats.add(seat);
    }
    
    public boolean isEmpty()
    {
        return empty.get();
    }
    
    public BooleanProperty emptyProperty()
    {
        return empty;
    }
    
    public RoomEntry getEntry()
    {
        return entry;
    }
    
    
    public static RoomPojo toPojo(Room room)
    {
        List<List<SeatPojo>> seats = new ArrayList<>();
        for (int i = 0; i < room.rows; ++i)
            seats.add(new ArrayList<>());
        
        for (int j = 0; j < room.rows; ++j)
            for (int i = 0; i < room.columns; ++i)
            {
                SeatPojo seatPojo = Seat.toPojo(
                    room.getSeat(j, i)
                );
                seats.get(j).add(seatPojo);
            }
        
        return new RoomPojo(
            room.number,
            room.rows,
            room.columns,
            room.empty.get(),
            seats
        );
    }
    
    public static Room fromPojo(RoomPojo roomPojo)
    {
        Room room = new Room(
            roomPojo.getNumber(),
            roomPojo.getRows(),
            roomPojo.getColumns()
        );

        for (int i = 0; i < roomPojo.getRows(); ++i)
            for (int j = 0; j < roomPojo.getColumns(); ++j)
                room.addSeat(
                    i, j,
                    Seat.fromPojo(
                        roomPojo.getSeats().get(i).get(j)
                    )
                );

        return room;
    }
    

    
    
    
}
