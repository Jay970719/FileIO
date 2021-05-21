// -----------------------------------------------------
// Part: Assignment 3
// Written by: Youngjae Kim, 40169282
// -----------------------------------------------------
/**
 * CSVDataMissing
 * @author Youngjae Kim,40169282
 *
 */
public class CSVDataMissing extends Exception{
	/**
	 * call the row which has a missing data
	 */
	int rowi;
	/**
	 * call the column of missing data
	 */
	int columni;
	/**
	 * for the message
	 */
	String i;
	CSVDataMissing(String i, int row, int column){
		super(i);
		this.i= i;
		this.rowi = row;
		this.columni = column;

	}

}
