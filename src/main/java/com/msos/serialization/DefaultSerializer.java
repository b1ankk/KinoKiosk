package com.msos.serialization;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultSerializer
{
    public static void writeAsJson(Cluster cluster, String path) throws IOException
    {
        writeAsJson(cluster, Path.of(path));
    }

    public static void writeAsJson(Cluster cluster, Path path) throws IOException
    {
        JsonMapper mapper = new JsonMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        String mappedData = mapper.writeValueAsString(cluster);
    
        Files.writeString(
            path,
            mappedData
        );
    }
    
    public static Cluster readFromJson(String path) throws IOException
    {
        return readFromJson(Path.of(path));
    }
    
    public static Cluster readFromJson(Path path) throws IOException
    {
        String readJson = Files.readString(path);
        
        JsonMapper mapper = new JsonMapper();
        
        Cluster cluster = mapper.readValue(readJson, Cluster.class);
        
        return cluster;
    }
    
}
