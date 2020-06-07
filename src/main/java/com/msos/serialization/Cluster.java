package com.msos.serialization;

import com.msos.security.Password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster
{
    private List<RoomPojo> rooms = new ArrayList<>();
    private Map<String, Password> passwords = new HashMap<>();
    
    
    public Map<String, Password> getPasswords()
    {
        return passwords;
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
