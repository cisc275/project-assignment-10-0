package main_package;

import java.io.Serializable;

// Author: Steven Soranno
public enum BirdType implements Serializable{
    NH("nh"),
	OSPREY("osprey");
	
	private String name = null;
	
	private BirdType(String s){
		name = s;
	}
	// name getter
	public String getName() {
		return name;
	}
}
