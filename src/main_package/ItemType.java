package main_package;

import java.io.Serializable;

// Author: Steven Soranno
public enum ItemType implements Serializable{
	AIRPLANE("airplane"),
	FOX("fox"),
    FISH("fish"),
    RAT("rat"),
    STICK("stick"),
    WINFLAG("winflag"),
    EGG("egg"),
	SHIP("ship"),
	NEST("nest");
	
	private String name = null;
	
	private ItemType(String s){
		name = s;
	}
	
	// name getter
	public String getName() {
		return name;
	}
}
