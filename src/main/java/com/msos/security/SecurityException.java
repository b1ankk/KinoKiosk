package com.msos.security;

class SecurityException extends Exception
{
    public SecurityException()
    {
    }
    
    public SecurityException(String message)
    {
        super(message);
    }
    
    public SecurityException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public SecurityException(Throwable cause)
    {
        super(cause);
    }
    
    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

class PasswordException extends SecurityException
{
    public PasswordException()
    {
    }
    
    public PasswordException(String message)
    {
        super(message);
    }
    
    public PasswordException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public PasswordException(Throwable cause)
    {
        super(cause);
    }
    
    public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

class PasswordHashingException extends PasswordException
{
    public PasswordHashingException()
    {
    }
    
    public PasswordHashingException(String message)
    {
        super(message);
    }
    
    public PasswordHashingException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public PasswordHashingException(Throwable cause)
    {
        super(cause);
    }
    
    public PasswordHashingException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

class PasswordVerificationException extends PasswordException
{
    public PasswordVerificationException()
    {
    }
    
    public PasswordVerificationException(String message)
    {
        super(message);
    }
    
    public PasswordVerificationException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public PasswordVerificationException(Throwable cause)
    {
        super(cause);
    }
    
    public PasswordVerificationException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

