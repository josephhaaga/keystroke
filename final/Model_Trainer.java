// 1 Data Munging
// 	1.1	Read txt file
//  1.2 Generate vectors describing keystrokes
// 2 Weka Modelling
//	2.1 Probabilistic Neural Net?

import java.util.Scanner;
// import java.util.FileReader;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
// import java.util.Array;

public class Model_Trainer{

	public static String print(String words){
		System.out.println(words);
		return null;
	}

	public static List<List> readInputs(String filepath){
			try{
				String tempLine; // current string while iterating through file
				BigInteger timestamp; // epoch time the event occured
				String actionType; // KeyPressed, KeyReleased, or KeyTyped
				String key; // Key that triggered the event
				List<BigInteger> timestamps = new ArrayList<BigInteger>();
				List<String> keys = new ArrayList<String>();
				List<String> actions = new ArrayList<String>(); 
				Scanner sn = new Scanner(new FileReader("quick_brown_fox/AnDee/Sun Sep 25 13:35:27 EDT 2016.txt"));
				tempLine=sn.nextLine();
				BigInteger firstTimestamp = new BigInteger(tempLine.substring(tempLine.lastIndexOf(" ")+1));
				while(sn.hasNextLine()){
					tempLine = sn.nextLine();
					try{
						timestamp = new BigInteger(tempLine.substring(tempLine.lastIndexOf(" ")+1));
						timestamp = timestamp.subtract(firstTimestamp);
						actionType = tempLine.substring(tempLine.indexOf(" ")+1,tempLine.indexOf(":"));
						key = tempLine.substring(tempLine.indexOf("(")+1,tempLine.indexOf(")n"));
						if(!key.contains("no mods")){
							// print(timestamp + " " + key + " " + actionType);
							timestamps.add(timestamp);
							keys.add(key);
							actions.add(actionType);
						}
					}catch(Exception f){
						// f.printStackTrace();
					}

				}
				sn.close(); // safety first, then teamwork
				print(timestamps.get(10).toString());
				// List<List> results = new ArrayList<List>(){timestamps, keys, actions};
				List<List> results = new ArrayList<List>();
				

				// results = {timestamps, keys, actions};
				// results.add(timestamps);
				// results.add(keys);
				// results.add(actions);
				return results;
			}catch(Exception e){
				e.printStackTrace();

			}
			return null;
		}

	public static void main(String args[]){
		// Read txt file
		List<List> keystrokes = readInputs("A string");
	}

}