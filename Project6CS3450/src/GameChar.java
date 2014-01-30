import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameChar implements Serializable{
//String[] inventory = {"brass lantern", "rope", "rations", "staff"};
int rowLocation;
int colLocation;
int viewRange;//this will be what the char uses to see further or something
Adventure game;
//public GameItem[] itemsInInventory;
List<GameItem> itemsInInventory = new ArrayList<GameItem>();

	public GameChar(Adventure inGame)
	{
		game = inGame;
		rowLocation = 0;
		colLocation = 0;
		viewRange = 2;
	}
	public void DirectionMover(String input)
	{
		if(input.equals("N")){
			rowLocation = Adventure.map.NSMover(-1, rowLocation);
		}
		else if(input.equals("E"))
		{
			colLocation = Adventure.map.EWMover(1, colLocation);
		}
		else if(input.equals("S"))
		{
			rowLocation = Adventure.map.NSMover(1, rowLocation);
		}
		else if(input.equals("W"))
		{
			colLocation = Adventure.map.EWMover(-1, colLocation);
		}
		else{
			game.appendString("You cannot go that direction");
		}
		game.appendString("your location is ("+rowLocation+","+colLocation+")");
		for(int i = 0; i < Adventure.map.itemsOnMap.size();i++){
			if(Adventure.map.itemsOnMap.get(i).rowLocation == this.rowLocation && Adventure.map.itemsOnMap.get(i).colLocation == this.colLocation){
				game.appendString("you found '" + Adventure.map.itemsOnMap.get(i).name + "' at this location");
			}
		}
	}
}