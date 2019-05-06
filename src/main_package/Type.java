package main_package;

import java.io.Serializable;

//author Sicheng Tian
public enum Type implements Serializable{
	NH1("NH1Game"),
	NH2("NH2Game"),
	OP("OPgame"),
	MAINMENU("MainMenu"),
	GAMEOVER("GameOver"),
	WIN("Win");
	
	private String name = null;
	
	private Type(String s){
		name = s;
	}
	public String getName() {
		return name;
	}
	
}
