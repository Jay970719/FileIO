// -----------------------------------------------------
// Part: Assignment 3
// Written by: Youngjae Kim, 40169282
// -----------------------------------------------------
/**
 * CSVFileInvalidException
 * @author Youngjae Kim,40169282
 *
 */
public class CSVFileInvalidException extends Exception {
	
	int column;
	/**
	 * call the column of missing attribute.
	 */
	CSVFileInvalidException(int i){
	   super("empty column is "+ i);
	   this.column =i;
	}

}
