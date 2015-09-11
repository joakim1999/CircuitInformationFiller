package com.cif.calc;

import java.util.HashMap;

public class CommandedInfo {
	static HashMap<String,Integer> info = new HashMap<String,Integer>();
	
	public static void add(String key, int value){
		info.put(key,value);
		System.out.println("New command value " + key + ":" + value);
	}
	
	public static void get(String key){
		info.get(key);
	}
}
