package com.msos.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Password
{
    private final byte[] salt;
    private final byte[] hash;
    
    @JsonCreator
    public Password(
        @JsonProperty("salt") byte[] salt,
        @JsonProperty("hash") byte[] hash)
    {
        this.salt = salt;
        this.hash = hash;
    }
    
    public byte[] getSalt()
    {
        return salt;
    }
    
    public byte[] getHash()
    {
        return hash;
    }
}
