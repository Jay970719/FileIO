// -----------------------------------------------------
// Part: Assignment 3
// Written by: Youngjae Kim, 40169282
// -----------------------------------------------------
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class for the system.
 * @author Youngjae Kim,40169282
 *
 */
public class CSV2JSON {
	   /**
	    * method to change delimiter
	    * @param str  input the string to change delimiter
	    * @return final_result  which is changed string.
	    */
		public static String[] changeDelimeter(String str) {
			String x = new String(str);    //check if there is a double quotes
			String find_char = "\"";
			int num = x.indexOf(find_char);
			String index = "";
			
			// Getting all the index of double quotes
			while (num >=0) {
			   index = index + Integer.toString(num) + ',';
			    num = x.indexOf(find_char,num+1);}    //we are adding num with 1 because we have to find the next "
			
			if(index.length()>=1)     //do it only when there is a double quote
				index = index.substring(0, index.length()-1);    //remove , at the last.
			
			String[] indexs = index.split(",");
			
			for(int i = 0; i <= indexs.length -2; i=i+2) {
				String temp = x.substring(Integer.parseInt(indexs[i]), Integer.parseInt(indexs[i+1])).replace(",", "|");   //replace , into | which is between the ""
				x = x.replace(x.substring(Integer.parseInt(indexs[i]), Integer.parseInt(indexs[i+1])), temp);
			}
			
			String[] final_result = x.split(",");
			
			for(int i = 0; i < final_result.length; i++) {
				final_result[i] = final_result[i].replace("\"", "").replace("|", ",").strip();   //put strip to remove space in front and the end.
			}
			return final_result;
		}
		
   /**
    * Main method to run the system.
    * @param inputfile  name of the inputfile that you want to read
    * @param outputfile name of the outputfile that you want to write on
    * @param writer name of the writer that writes to log file
    * @return boolean to check if it should read file or not.
    */
	static boolean processFilesForValidation(String inputfile, String outputfile, PrintWriter writer) {
        String filename = inputfile.split("\\.")[0]+".csv";  //this is for output the filename in exceptions
		int column=0;
		int row=0;
		int countmiss= 0;
		Scanner inputStream = null;
		PrintWriter outstream =null;
		PrintWriter outstream1 = null;
		Scanner newinputStream = null;
		String [][] arr= null;
		String[] strarr= null;
		try{inputStream = new Scanner(new FileInputStream(inputfile));
		    newinputStream = new Scanner(new FileInputStream(inputfile)); //for counting the rows
		   
		    while(newinputStream.hasNextLine())
		    	{newinputStream.nextLine();
		    	 row++;}
		    
		    strarr =inputStream.nextLine().split(",");
		    
		    for(int i=0; i<strarr.length; i++)
		    	if(strarr[i].equals(""))
		    		countmiss++;                     //count how many data are missing
		    for(int i=0; i<strarr.length; i++)
		    	if(strarr[i].equals(""))
		           throw new CSVFileInvalidException(i);  //throw CSVFileInvalidException when some attribute doesn't exist.
		    
		    column= strarr.length;
		    
		    arr=  new String[row][column];

            for(int i=0; i<column; i++)
            {
              arr[0][i]= strarr[i];                  //input the attributes into the array
            }
            for(int i=1; i<row; i++ )
            {
              String[] temp = changeDelimeter(inputStream.nextLine());
              arr[i]= temp;                          //input data into the array
              
            }
            modifydata(arr,column);
            
            outstream = new PrintWriter(new FileOutputStream(outputfile));
		    
            for(int i=1; i<row;i++)
		    	for(int j=0; j< column; j++)
                  if(arr[i][j].equals(""))              //if some data is missing, we print except that row and throw CSVDataMissing Exception.
                	  { outstream.println("[");
                	    outstream.flush();
                	    for(int k=1;k<row; k++) 
                	    if(k!=i){
       		             outstream.println(" {");
       		             outstream.flush();
       		             for(int z=0; z<column;z++)
       		            	 {if(z<column-1&& z != column-1)
       		    		      {outstream.println("\""+ arr[0][z]+"\": "+ "\""+arr[k][z]+"\",");
       		    		       outstream.flush();}
       		            	 else if(z== column-1 && z!= row-1)
       		            	 {outstream.println("\""+ arr[0][z]+"\": "+ "\""+arr[k][z]+"\""+"\n },");
       		    		       outstream.flush();}
       		            	 else if(z==column-1 && z==row-1)
       		            	 {outstream.println("\""+ arr[0][z]+"\": "+ "\""+arr[k][z]+"\""+"\n }");
       		    		       outstream.flush();}}
       		                                  }
                	   String ja = String.valueOf(j+1);        //to send which line doesn't have a data.
                	   outstream.close();
                	   throw new CSVDataMissing(ja,i,j);}      
		   
		    
		   
		    outstream1 =new PrintWriter(new FileOutputStream(outputfile));
		    outstream1.println("[");                          //when there is no missing attributes or data.
		    outstream1.flush();
		       for(int j=1;j<row; j++) {
		             outstream1.println(" {");
		             outstream1.flush();
		             for(int i=0; i<column;i++)
		            	 {if(i<column-1&& i != column-1)
		    		      {outstream1.println("\""+ arr[0][i]+"\": "+ "\""+arr[j][i]+"\",");
		    		       outstream1.flush();}
		            	 else if(i== column-1 && j!= row-1)
		            	 {outstream1.println("\""+ arr[0][i]+"\": "+ "\""+arr[j][i]+"\""+"\n },");
		    		       outstream1.flush();}
		            	 else if(i==column-1 && j==row-1)
		            	 {outstream1.println("\""+ arr[0][i]+"\": "+ "\""+arr[j][i]+"\""+"\n }");
		    		       outstream1.flush();}}
		                                  }
		       outstream1.println("]");
		       outstream1.close();
		 return true;}
	    catch(FileNotFoundException e){               //catch FilenotFoundException
	    	System.out.println("Could not open file "+ filename 
	    			+" for reading.\nPlease check if file exists! Program will terminate after closing all open files.");
            return false;
           
	    } 
		catch(CSVDataMissing c) {                        //catch CSVDataMissingException
			System.out.println("In file "+ filename + " line "+ c.i + " not converted to JSON: Missing data.");
			writer.write("\nIn file "+ filename + " line "+ c.i+"\n");        //write values into the log file.
			writer.flush();
			for(int i = 0; i< column; i++)
			      {if(arr[c.rowi][i]!="")
				     {writer.write(arr[c.rowi][i]+"\t");
			         writer.flush();}
			      else if(arr[c.rowi][i].equals(""))
			         {writer.write("***\t");
			    	  writer.flush();
			         }
			    	   }
			writer.write("\nMissing: "+ arr[0][c.columni]);
			writer.flush();
			writer.println();
			writer.flush();
			
			
			return true;

			
		}
		catch(CSVFileInvalidException d) {               //catch CSVFileInvalidException
		    System.out.println("File "+ filename +" is invalid: field is missing.\nFile is not converted to JSON." );
		    
		    writer.write("File "+ filename +" is invalid");            //write some message to the log file.
		    writer.flush();
		    writer.write("\nMissing field: "+ strarr.length+ " detected, "+ countmiss + "missing\n");
		    writer.flush();
		    for(int i=0; i<strarr.length;i++)
		       {if(i!=d.column&&i!=strarr.length-1)
		    	 {writer.write(strarr[i]+", ");
		    	  writer.flush();}
		       else if(i!=d.column&&i==strarr.length-1)
		         {writer.write(strarr[i]);
		    	  writer.flush();}
		       else if(i==d.column&&i!=strarr.length-1)
		         {writer.write("***, ");
		    	  writer.flush();}
		       else if(i==d.column&&i==strarr.length-1)
		         {writer.write("***");
		    	  writer.flush();}
		       
		       }
		    writer.println();
		    writer.flush();
		    return false;
		} 
		catch(InvalidException i) {          //catch invalidException
			System.out.println(i); 
			
			return true;
		}
		
		
		
	
	}
/**
 * method to modify the data
 * @param arr  put array that has all the value inside
 * @param column1  to check if the row has an missing data.
 * @throws InvalidException
 */
public static void modifydata(String [][] arr, int column1) throws InvalidException{   //method to modify the data
	int row;
	int column;
	String change;
    System.out.print("Do you want to modify data? Press Y or N: ");
    Scanner mo = new Scanner(System.in);
    String answer = mo.next();
    if(answer.equals("Y"))
    	{System.out.println("Input the row and column of the data that you want to change.");
    	System.out.print("row: ");
    	 row =mo.nextInt();
    	 System.out.print("column: ");
    	 column = mo.nextInt();
    	 for(int i=0;i<column1;i++)
    		if(arr[row-1][i].equals(""))
    			throw new InvalidException();
         Scanner mew = new Scanner(System.in);
    	 System.out.print("how do you want to change?: ");
    	 change = mew.nextLine();
    	 arr[row-1][column-1]= arr[row-1][column-1].replace(arr[row-1][column-1], change);      
    	}
   
}

/**
 * method to print the data 
 * @param filename call the file to read.
 */
public static void readdata(String filename) {  //method to read data 
	String line="";
	try{
	    File ff = new File(filename);
		boolean exist = ff.exists();       //to check the file exists or not
		if(!exist)
		 { System.out.println("Could not open file "+ filename      //if the file not exists, give one more chance.
	    			+" for reading.\nInput a file name again. It is your last chance!");
		 Scanner jay1=  new Scanner(System.in);
		 filename = jay1.nextLine();
		
		}
	BufferedReader read = new BufferedReader(new FileReader(filename));
	line= read.readLine();
	while(line!=null) {
		System.out.println(line);
		line= read.readLine();}
	read.close();
	System.out.println("\n All printed.");
	}
	
	catch(FileNotFoundException e){       //catch FileNotFoundException
		System.out.println("File is not found. Bye");

	}
	catch(IOException r) {                   //catch IOException
		System.out.println("There is some problem to read this file.Bye");
	}
}
/**
 * drive the system
 */
public static void main(String[] args) {
	   PrintWriter writer= null;
	   try {
	    writer = new PrintWriter(new FileOutputStream("logfile.txt"));}        //call the print writer.
	   catch(FileNotFoundException e) {
		   System.out.println("File is not found.");
	   }
	    String filename;
		System.out.println("Hello, please input the name of the file that you want to call(Press 'no' to stop): ");
		Scanner jay = new Scanner(System.in);
		
		String multiple_files = "";
		String [] arrfile;
		while(true) {
		String inputfile = jay.nextLine();
		if(inputfile.equals("no"))
			break;
		multiple_files = multiple_files + inputfile + ",";
		}
		
		
		arrfile = multiple_files.substring(0, multiple_files.length()-1).split(",");
		for(String inputfile: arrfile) {
		filename= inputfile.split("\\.")[0]+".txt";               //change the type of file from csv to txt.
		System.out.println("Now, please input the name of the file that you want to write on(in order): ");
		String outputfile = jay.nextLine();      //make user input the name of the file that they want to write on.
		boolean check= processFilesForValidation(filename, outputfile, writer);       //All process.
		if(check)  //check if we should continue to read file
		{System.out.println("Input the file name that you want to read(in order): ");   
		String readfile = jay.nextLine();      //make user to input the name of the file that they want to write on.
		System.out.println(readfile);
		readdata(readfile);
		}
		else continue;}
		System.out.println("All finished!! bye!!");
		writer.close();
		
		
	}

}
