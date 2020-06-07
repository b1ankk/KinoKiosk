package com.msos.serialization;

import com.msos.security.Password;

import java.util.*;

public class Cluster
{
    private List<RoomPojo> rooms = new ArrayList<>();
    private Map<String, Password> passwords = new HashMap<>();
    
    
    public List<RoomPojo> getRooms()
    {
        return rooms;
    }
    
    public void setPasswords(Map<String, Password> passwords)
    {
        this.passwords = passwords;
    }
    
    public void setRooms(List<RoomPojo> rooms)
    {
        this.rooms = rooms;
    }
}
