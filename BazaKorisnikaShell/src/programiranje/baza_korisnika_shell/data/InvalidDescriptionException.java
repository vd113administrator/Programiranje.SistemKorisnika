/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.data;

/**
 * Greske pri opisivanju
 * @author Mikec
 */
public class InvalidDescriptionException extends Exception{

    public InvalidDescriptionException() {
    }

    public InvalidDescriptionException(String message) {
        super(message);
    }

    public InvalidDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDescriptionException(Throwable cause) {
        super(cause);
    }

    protected InvalidDescriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
