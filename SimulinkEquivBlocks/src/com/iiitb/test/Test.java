package com.iiitb.test;

import java.util.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Map<String, Integer> incomingCount = new HashMap<String,Integer>();
		incomingCount.put("test",4);
		incomingCount.put("test",5);
		System.out.println(incomingCount.get("test"));
		System.out.println(incomingCount.size());
	}

}
