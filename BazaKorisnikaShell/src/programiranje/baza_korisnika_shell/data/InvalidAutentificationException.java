/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.data;

/**
 * Greske pri autentifikaciji 
 * @author Mikec
 */
public class InvalidAutentificationException extends Exception{

    public InvalidAutentificationException() {
    }

    public InvalidAutentificationException(String message) {
        super(message);
    }

    public InvalidAutentificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAutentificationException(Throwable cause) {
        super(cause);
    }

    protected InvalidAutentificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }   
}
