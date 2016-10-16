// 1 Data Munging
// 	1.1	Read txt file
//  1.2 Generate vectors describing keystrokes
// 2 Weka Modelling
//	2.1 Probabilistic Neural Net?

import java.util.Scanner;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

// import weka.classifiers.Classifier;
// import weka.classifiers.Evaluation;
// import weka.classifiers.trees.J48;


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

	public static Integer findAverageTime(List<String> timesList){
		Integer runningAverage = 0;
		int dwellTimeLength = 0;
		for(String i: timesList ){
			dwellTimeLength+=1;
			runningAverage+= Integer.parseInt(i.substring(i.indexOf(" ")+1));
		}
		return (runningAverage/=dwellTimeLength);

	}

	public static void writeToCSV(String[] array_of_strings){
		for(String a : array_of_strings){
			print(a);
		}
	}

	public static void main(String args[]){
		// Read single txt file
		List<List> anDeeKeystrokes = readInputs("quick_brown_fox/AnDee/Sun Sep 25 13:35:27 EDT 2016.txt");

		String andeestring1 = "andee "+findAverageTime(findDwellTimes(anDeeKeystrokes)).toString();

		print("AnDee Average Dwell Time: "+andeestring1);

		List<List> anDeeKeystrokes2 = readInputs("quick_brown_fox/AnDee/Sat Oct 08 21:23:05 EDT 2016.txt");

		String andeestring2 = "andee "+findAverageTime(findDwellTimes(anDeeKeystrokes2)).toString();

		print("AnDee Average Dwell Time: "+andeestring2);

		List<List> anDeeKeystrokes3 = readInputs("quick_brown_fox/AnDee/Sat Oct 08 21:22:43 EDT 2016.txt");

		String andeestring3 = "andee "+findAverageTime(findDwellTimes(anDeeKeystrokes3)).toString();

		print("AnDee Average Dwell Time: "+andeestring3);
		


		List<List> joeKeystrokes = readInputs("quick_brown_fox/joehaaga/Sun Sep 25 13:50:34 EDT 2016.txt");

		String joestring1 = "joehaaga "+findAverageTime(findDwellTimes(joeKeystrokes)).toString();

		print("Joe Average Dwell Time: "+joestring1);

		List<List> joeKeystrokes2 = readInputs("quick_brown_fox/joehaaga/Sun Sep 25 13:52:17 EDT 2016.txt");

		String joestring2 = "joehaaga "+findAverageTime(findDwellTimes(joeKeystrokes2)).toString();

		print("Joe Average Dwell Time: "+joestring2);

		List<List> joeKeystrokes3 = readInputs("quick_brown_fox/joehaaga/Sun Oct 09 18:47:57 EDT 2016.txt");

		String joestring3 = "joehaaga "+findAverageTime(findDwellTimes(joeKeystrokes3)).toString();

		print("Joe Average Dwell Time: "+joestring3);


		String[] input_strings = {andeestring1, andeestring2, andeestring3, joestring1, joestring2, joestring3};

		// Calculate features (Dwell Time, Flight Time, Shift-key Dwell Time)

		// int averageDwellTime = averageDwellTime(keystrokes);
		// print("AverageDwellTime: "+averageDwellTime);

		writeToCSV(input_strings);



		// int averageFlightTime = averageFlightTime(keystrokes);
		// int averageShiftKeyDwellTime = averageShiftKeyDwellTime(keystrokes);

		// Add feature vector to List of training Data (Labelled)
	}

}