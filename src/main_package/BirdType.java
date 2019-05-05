package main_package;
// Author: Steven Soranno
public enum BirdType {
    NH("nh"),
	OSPREY("osprey");
	
	private String name = null;
	
	private BirdType(String s){
		name = s;
	}
	public String getName() {
		return name;
	}
}
