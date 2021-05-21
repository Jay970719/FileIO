//--------------------------------------------------------------
//Assignment 4- part1
//Written by:
//Youngjae-Kim(40169282), Julian Colantuono(40109849)
//---------------------------------------------------------------
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Part1 class which is the main class. 
 */
public class Part1 {
	/**
	 * method to find the words which have more than 3 vowels
	 * @param ArrayList<String> object
	 * @param String outputfile
	 */
	static void forVowel(ArrayList<String> a, String outputfile) {
	  ArrayList <String> VowelWords = new ArrayList<String>();
		for(int i =0; i< a.size();i++) {
			int count = 0;
			for(int j=0;j<a.get(i).length();j++) {
		       String temp = a.get(i).toLowerCase();
			   if(temp.charAt(j)=='a'||temp.charAt(j)=='e'||temp.charAt(j)=='i'||temp.charAt(j)=='o'||temp.charAt(j)=='u') {
				   count++;}  //put count to check how many vowels each word has .
			   }
			 if(count>3) {
				   VowelWords.add(a.get(i));}// if there are more than 3 vowels then we add it into the array list.
			
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(outputfile));
			writer.println("Word Count: "+ VowelWords.size());
			for(int i= 0;i<VowelWords.size();i++) {
			  writer.println(VowelWords.get(i));
			  writer.flush();}     //we write into the files.
			writer.close();
		 
		}
		catch(FileNotFoundException f) {
			System.out.println("File is not found.");
		}
		catch(IOException e) {
			System.out.println("Some problem here!");
		}
	}
	/**
	 * method to find the words which start with o
	 * @param ArrayList<String> object
	 * @param String outputfile
	 */
	static void forO (ArrayList<String> a, String outputfile) {
		ArrayList <String> oWords = new ArrayList<String>();
		
		for(int i =0; i< a.size();i++) {
		    String temp= a.get(i).toLowerCase();
		  if(a.get(i).length()!=0)     //to check if it is an empty string.
		   if(temp.charAt(0)=='o') {
			  oWords.add(a.get(i));     //if it starts with o, then add it to the array list.
		  }
		}
		PrintWriter writer1 = null;
		try {
			writer1 = new PrintWriter(new FileOutputStream(outputfile));
			writer1.println("Word Count: "+ oWords.size());
			for(int i= 0;i<oWords.size();i++) {
			  writer1.println(oWords.get(i));
			  writer1.flush();}   //write it into the file. 
		 writer1.close();
		}
		catch(FileNotFoundException f) {
			System.out.println("File is not found.");
		}
		catch(IOException e) {
			System.out.println("Some problem here!");
		}
	}
	/**
	 * method to find distinct words
	 * @param ArrayList<String> object
	 * @param String outputfile
	 */
	static void distinct(ArrayList<String> a, String outputfile) {
		ArrayList <String> distinct = new ArrayList<String>();
		for(int i =0; i< a.size();i++)
			for(int j =i+1; j< a.size();j++) {
				 if(a.get(i).equals(a.get(j)))  //if they are same, we set the later one as @@.
					 a.set(j, "@@");
			}
		for(int i =0; i< a.size();i++)
		    if(a.get(i)!="@@")            //if they are not @@, then add it to the array list.
			  distinct.add(a.get(i));
		
		PrintWriter writer2 = null;
		try {
			writer2 = new PrintWriter(new FileOutputStream(outputfile));
			writer2.println("Word Count: "+ distinct.size());
			for(int i= 0;i<distinct.size();i++) {
			  writer2.println(distinct.get(i));
			  writer2.flush();}          //write it to the file.
		 writer2.close();
		}
		catch(FileNotFoundException f) {
			System.out.println("File is not found.");
		}
		catch(IOException e) {
			System.out.println("Some problem here!");
		}
				
	}
	
	/**
	 * driver method
	 * @param args
	 */
     public static void main(String[] args) {
	System.out.println("Hello input the file you want to divide into several files: ");
	Scanner scan = new Scanner(System.in);
	String filename = scan.nextLine();
	
	ArrayList <String> list= new ArrayList <String> ();
	Scanner read = null;
	
	try {
		
		read= new Scanner(new FileInputStream(filename));
		while(read.hasNext())
		   { list.add(read.next().replaceAll("[^a-zA-Z0-9]", ""));}   
		//we put all words into the list except non alphabetic ones.
		
		forVowel(list,"vowel_verbiage.txt");
		forO(list,"obsessive_o.txt");
		distinct(list,"distinct_data.txt");  //use the methods with the name of the output files
		System.out.println("We made files. Finished. Bye!");
	}
	catch(FileNotFoundException f) {
		System.out.println("File is not found.");
	}
	}

}
