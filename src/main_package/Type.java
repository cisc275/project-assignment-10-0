package main_package;
//author Sicheng Tian
public enum Type {
	NH1("NH1Game"),
	NH2("NH2Game"),
	OP("OPgame"),
	MAINMENU("MainMenu"),
	GAMEOVER("GameOver"),
	OPREVIEW("opReview"),
	NHREVIEW("nhreview"),
	WIN("Win");
	
	private String name = null;
	
	private Type(String s){
		name = s;
	}
	public String getName() {
		return name;
	}
	
}
