package com.msos.security;

import com.msos.serialization.Cluster;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    
    static
    {
        initWithDefaultPassword();
    }
    
    
    public static Map<String, Password> getUnmodifiableUserPasswords()
    {
        return Collections.unmodifiableMap(userPasswords);
    }
    
    
    /**
     * Retrieves all the passwords contained in the specified {@link Cluster} and adds them to the internal storage.
     * If password storage is empty after the operation, the default password is added to ensure a correct app behavior.
     *
     * @param cluster Cluster to retrieve data from
     */
    public static void retrievePasswords(Cluster cluster)
    {
        userPasswords.putAll(cluster.getPasswords());
        
        if (userPasswords.isEmpty())
            initWithDefaultPassword();
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
        System.out.println(Arrays.toString(veryTastySalt));
        System.out.println(Arrays.toString(hash));
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
    
    private static void initWithDefaultPassword()
    {
        byte[] defaultSalt = {-27, -42, 17, 93, -25, -99, 66, -67, -95, 52, -81, -22, 9, 9, 44, 84, -128, -66, -105, -6, -103, -23, 44, 28, 104, 2, -16, 10, 7, -88, -58, -20};
        byte[] defaultHash = {89, 7, 1, -112, -43, 6, -98, -103, 78, 70, 42, -29, 2, -75, 73, -46, 125, 121, -8, -60, -102, 101, 85, 37, -47, 55, -72, 120, -119, -14, 86, 117};
    
        userPasswords.put(
            "user",
            new Password(
                defaultSalt,
                defaultHash
            )
        );
    }
    
}
