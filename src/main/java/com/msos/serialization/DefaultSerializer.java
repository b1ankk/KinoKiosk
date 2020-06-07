package com.msos.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
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
    
}
