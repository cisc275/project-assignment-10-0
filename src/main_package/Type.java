package main_package;
//author Sicheng Tian
public enum Type {
	NH1("NH1Game"),
	TUTORIALNH1("NH1Tutorial"),
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
	// name getter
	public String getName() {
		return name;
	}
	
}
