// 1 Data Munging
// 	1.1	Read txt file
//  1.2 Generate vectors describing keystrokes
// 2 Weka Modelling
//	2.1 Probabilistic Neural Net?

import java.util.Scanner;
// import java.util.FileReader;
import java.io.*;
import java.math.BigInteger;
// import java.util.ArrayList;
// import java.util.List;
import java.util.*;

public class Model_Trainer{

	public static String print(String words){
		System.out.println(words);
		return null;
	}

	// public static List<Integer> findIndices(List<String> list, Object obj){
	public static List<String> findDwellTimes(List<List> list){
		// String a[][] = new String[][];
		List<String> dwellTimes = new ArrayList<String>();
		LinkedList<String> pressedButNotReleasedKeys = new LinkedList<String>();
		LinkedList<String> pressedButNotReleasedTimestamps = new LinkedList<String>();
		print("size: ");
		System.out.println(list.get(0).size());
		String tempAction, tempKey, tempTimestamp, releasedData, releaseTimestampString;
		int index, releaseTimestamp;
		for(int i=0; i<list.get(0).size()-1; i++){
			tempAction = list.get(2).get(i).toString(); // PRESSED or RELEASED
			tempKey = list.get(1).get(i).toString(); // Letter of key pressed/released
			tempTimestamp = list.get(0).get(i).toString(); // Unix epoch timestamp
			if(tempAction.toString().contains("PRESSED")){
				pressedButNotReleasedKeys.push(tempKey);
				pressedButNotReleasedTimestamps.push(tempTimestamp);
			}else if(tempAction.toString().contains("RELEASED")){
				// Key Released
				index = pressedButNotReleasedKeys.indexOf(tempKey);
				releasedData = pressedButNotReleasedKeys.remove(index);
				// releasedTimestamp = ;
				releaseTimestamp = Integer.parseInt(tempTimestamp) - Integer.parseInt(pressedButNotReleasedTimestamps.remove(index));
				releaseTimestampString = new Integer(releaseTimestamp).toString();
				dwellTimes.add(tempKey+" "+releaseTimestampString);
				// print(tempKey+" "+releaseTimestampString);
			}
		}
		return dwellTimes;
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
			Scanner sn = new Scanner(new FileReader(filepath));
			Boolean firstLine = true;
			BigInteger firstTimestamp = new BigInteger("0");
			while(sn.hasNextLine()){
				tempLine = sn.nextLine();
				try{
					if(firstLine){
						firstTimestamp = new BigInteger(tempLine.substring(tempLine.lastIndexOf(" ")+1));
						firstLine = false;
					}
					timestamp = new BigInteger(tempLine.substring(tempLine.lastIndexOf(" ")+1));
					timestamp = timestamp.subtract(firstTimestamp);
					actionType = tempLine.substring(tempLine.indexOf(" ")+1,tempLine.indexOf(":"));
					key = tempLine.substring(tempLine.indexOf("(")+1,tempLine.indexOf(")n"));
					if(!key.contains("no mods") && !actionType.contains("TYPED")){
						// print(timestamp + " " + key + " " + actionType);
						timestamps.add(timestamp);
						keys.add(key);
						actions.add(actionType);
					}
				}catch(Exception f){
					// f.printStackTrace();
				}

			}
			sn.close(); // Close FileBuffer
			List<List> results = new ArrayList<List>(); // Construct and return results object
			results.add(timestamps);
			results.add(keys);
			results.add(actions);
			return results;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]){
		// Read single txt file
		List<List> keystrokes = readInputs("quick_brown_fox/AnDee/Sun Sep 25 13:35:27 EDT 2016.txt");
		
		// for(int i=0;i<20;i++){
		// 	print(keystrokes.get(0).get(i).toString()+" "+keystrokes.get(1).get(i).toString()+" "+keystrokes.get(2).get(i).toString());
		// }

		// DEBUG
		findDwellTimes(keystrokes); 

		// Calculate features (Dwell Time, Flight Time, Shift-key Dwell Time)

		// int averageDwellTime = averageDwellTime(keystrokes);
		// print("AverageDwellTime: "+averageDwellTime);


		// int averageFlightTime = averageFlightTime(keystrokes);
		// int averageShiftKeyDwellTime = averageShiftKeyDwellTime(keystrokes);

		// Add feature vector to List of training Data (Labelled)
	}

}