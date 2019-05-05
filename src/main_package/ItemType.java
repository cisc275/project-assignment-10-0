package main_package;
// Author: Steven Soranno
public enum ItemType {
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
	public String getName() {
		return name;
	}
}
