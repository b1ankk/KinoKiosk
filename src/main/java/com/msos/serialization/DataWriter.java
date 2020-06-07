package com.msos.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DataWriter
{
    public static void saveAsJson(Object object, String path)
    {
        ObjectMapper mapper = new ObjectMapper();
        
        try
        {
            mapper.writeValue(new File(path), object);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    
    
    }
    
}
