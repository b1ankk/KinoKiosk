package com.msos.security;

import com.msos.serialization.Cluster;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PasswordManager
{
    private static final Map<String, Password> userPasswords = new HashMap<>();
    
    public static Map<String, Password> getUnmodifiableUserPasswords()
    {
        return Collections.unmodifiableMap(userPasswords);
    }
    
    public static void retrievePasswords(Cluster cluster)
    {
        userPasswords.putAll(cluster.getPasswords());
    }
    
    public static void removePassword(String user)
    {
        userPasswords.remove(user);
    }
    
    public static void removeAllPasswords()
    {
        userPasswords.clear();
    }
    
    public static void addPassword(String user, String passwordRaw) throws PasswordHashingException
    {
        SecureRandom random = new SecureRandom();
        byte[] veryTastySalt = random.generateSeed(32);
    
        byte[] hash = fetchPasswordHash(passwordRaw, veryTastySalt);
        
        Password password = new Password(veryTastySalt, hash);
        
        userPasswords.put(user, password);
    }
    
    public static boolean verify(String user, String passwordRaw) throws PasswordVerificationException
    {
        Password password = userPasswords.get(user);
        if (password == null)
            return false;
        
        byte[] checkHash;
        try
        {
            checkHash = fetchPasswordHash(passwordRaw, password.getSalt());
        }
        catch (PasswordHashingException e)
        {
            throw new PasswordVerificationException("An error occurred trying to verify the password", e);
        }
        
        return Arrays.equals(checkHash, password.getHash());
    }
    
    private static byte[] fetchPasswordHash(String password, byte[] salt) throws PasswordHashingException
    {
        byte[] hash;
        try
        {
            KeySpec keySpec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                0xffff,
                256
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(keySpec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new PasswordHashingException("An error occurred hashing the password", e);
        }
        
        return hash;
    }
    
}
