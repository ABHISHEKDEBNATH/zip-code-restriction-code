package com.test.zipcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abhishek Debnath
 *
 */
public class ZipCodeUtility {

	public static void main(String[] args) {
		
		String fileName = "E://eclipse//newworkspace//ZipCodeLambda//src//com//resources/zipcodes.txt";
		
		ArrayList<ZipCodeInterval> list = null;
		
		try(Stream<String> stream = Files.lines(Paths.get(fileName))){
			
			//creating an arraylist of ZipCodeInterval objects
			list = stream.distinct().map(m->new ZipCodeInterval(m)).collect(Collectors.toCollection(ArrayList::new));
			
			//sorting the list using Functional Interface Comparator
			list.sort((ZipCodeInterval o1,ZipCodeInterval o2)->o1.getStart()-o2.getStart());
			
			//calling the mergedIntervals method to create the final list
			ArrayList<ZipCodeInterval> finalList = mergedIntervals(list);
			System.out.println("********Final Merged List*********");
			finalList.stream().collect(Collectors.toCollection(ArrayDeque::new)).descendingIterator().forEachRemaining(System.out::println);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}

	
	public Boolean patternMatcher(String strPattern) {
		Matcher matcher = ZipCodeInterval.objPattern.matcher(strPattern);
		
		if(matcher.matches()) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Invalid ZIP code range::"+strPattern);
		}
	}
	
	
	
	/**
	 * @param sortedList
	 * Using stack data structure to merge the intersecting intervals
	 * @return ArrayList<ZipCodeInterval>
	 */
	public static ArrayList<ZipCodeInterval> mergedIntervals(ArrayList<ZipCodeInterval> sortedList) {
		ArrayList<ZipCodeInterval> mergedList = new ArrayList();
		
		Stack<ZipCodeInterval> objStack = new Stack();
		
		for(ZipCodeInterval currzipcodeInterval : sortedList) {
			if(objStack.empty()) {
				objStack.push(currzipcodeInterval);
			}
			else {
				ZipCodeInterval prevObjZipCodeInterval = objStack.pop();
				if(lambdaWrapper(prevObjZipCodeInterval,currzipcodeInterval)) {
					
					//Getting the max of end from the previous and current stacks
					int end = Math.max(prevObjZipCodeInterval.getEnd(),currzipcodeInterval.getEnd());
					
					//pushing a new object to the stack
					objStack.push(new ZipCodeInterval(prevObjZipCodeInterval.getStart(),end));
				}
				else {
					objStack.push(prevObjZipCodeInterval);
					objStack.push(currzipcodeInterval);
				}
				
			}
		}
		
		while(!objStack.empty()) {
			mergedList.add(objStack.pop());
		}
		return mergedList;
	}


	private static boolean lambdaWrapper(ZipCodeInterval prevObjZipCodeInterval, ZipCodeInterval currzipcodeInterval) {
		BiPredicate<ZipCodeInterval,ZipCodeInterval> bip = (previ,curri) -> previ.getEnd()>curri.getStart();
		return bip.test(prevObjZipCodeInterval, currzipcodeInterval);
	}

}
