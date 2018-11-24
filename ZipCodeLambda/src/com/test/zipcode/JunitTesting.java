package com.test.zipcode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class JunitTesting {

	@Test
	void testPatternMatching() {
		String strTest = "11111,21234";
		ZipCodeUtility obj1 = new ZipCodeUtility();
		obj1.patternMatcher(strTest);
	}
	
	
	@Test
	void testNumberOfIntervals() {
		
		ArrayList<ZipCodeInterval> list = new ArrayList();
		
		ZipCodeUtility objZipCodeUtility = new ZipCodeUtility();
		
		String strtest1 = "11155,10159";
		String str2 = "1,11111";
		
		ZipCodeInterval obj1 =  new ZipCodeInterval(strtest1);
		ZipCodeInterval obj2 = new ZipCodeInterval(str2);
		
		list.add(obj1);
		list.add(obj2);
		
		list.sort((ZipCodeInterval o1,ZipCodeInterval o2)->o1.getStart()-o2.getStart());
		ArrayList<ZipCodeInterval> finalList = objZipCodeUtility.mergedIntervals(list);
		
		System.out.println(finalList);
		assertEquals(1,finalList.size());
	}

}
