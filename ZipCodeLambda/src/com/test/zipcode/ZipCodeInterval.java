package com.test.zipcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Abhishek Debnath
 *
 */
public class ZipCodeInterval {
	
	private int start;
	private int end;
	public static Pattern objPattern = Pattern.compile("\\[?\\s*(\\d{1,5})\\s*,\\s*(\\d{1,5})\\s*]?");
	
	public ZipCodeInterval(String strPattern) {
		
		Matcher matcher = objPattern.matcher(strPattern);
		if(matcher.matches()) {
			ObjectBuilder(Integer.valueOf(matcher.group(1)),Integer.valueOf(matcher.group(2)));
		}
		else {
			throw new IllegalArgumentException("Invalid zip code provided ::"+ strPattern);
		}
		
	}
	
	private void ObjectBuilder(Integer istart, Integer iend) {
		if(istart<iend) {
			this.start = istart;
			this.end = iend;
		}
		else if(istart > iend) {
			this.start = iend;
			this.end = istart;
		}
		else {
			this.start=istart;
			this.end = istart;
		}
	}

	public ZipCodeInterval(int istart,int iend) {
		this.start = istart;
		this.end = iend;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ZipCodeInterval [start=" + String.format("%05d", this.start) + " , end=" + String.format("%05d", this.end)+"]";
	}
	
	

}
