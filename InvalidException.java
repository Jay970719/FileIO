// -----------------------------------------------------
// Part: Assignment 3
// Written by: Youngjae Kim, 40169282
// -----------------------------------------------------
/**
 * InvalidException
 * @author Youngjae Kim,40169282
 *
 */
public class InvalidException extends Exception{
	/**
	 * call the default message
	 */
    InvalidException(){
    	super("Error: Input row cannot be parsed due to missing information");
    }
    /**
     * call the message that is input.
     * @param message which is written by writer.
     */
    InvalidException(String message){
    	super(message);
    }
}
