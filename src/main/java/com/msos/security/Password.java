package com.msos.security;

public class Password
{
    private final byte[] salt;
    private final byte[] hash;
    
    public Password(byte[] salt, byte[] hash)
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
