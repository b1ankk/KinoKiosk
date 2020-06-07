package com.msos.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultSerializer
{
    public static void writeAsJson(Cluster data, String path) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        
        String mappedData = mapper.writeValueAsString(data);
    
        Files.writeString(
            Path.of(path),
            mappedData
        );
    }
    
    public static Cluster readFromJson(String path) throws IOException
    {
        String readJson = Files.readString(
            Path.of(path)
        );
        
        JsonMapper mapper = new JsonMapper();
        
        Cluster cluster = mapper.readValue(readJson, Cluster.class);
        
        return cluster;
    }
    
}
