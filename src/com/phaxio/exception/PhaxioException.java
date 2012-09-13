package com.phaxio.exception;

/**
 *
 * @author jnankin
 */
public class PhaxioException extends Exception {
    
    public PhaxioException(String message){
        super(message);
    }
    
    public PhaxioException(String message, Throwable thrw){
        super(message, thrw);
    }

}
