import java.io.Serializable;


public class GameItem implements Serializable{
	
	public String name = "null";
	public int rowLocation = -1;
	public int colLocation = -1;
	
	
	public GameItem(int rowLoc, int colLoc, String inName){
		name = inName;
		rowLocation = rowLoc;
		colLocation = colLoc;
	}
	public void updateGameItem(int rowLoc, int colLoc, String inName){
		name = inName;
		rowLocation = rowLoc;
		colLocation = colLoc;
	}
}
