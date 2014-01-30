import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class GameExport implements Serializable {
	Adventure game;
	GameMap map;
	GameChar charac;
	//GameItem items[];
	
	public GameExport(){
		
	}
	
	public GameExport(Adventure inGame, GameMap inMap, GameChar inChar){//, GameItem inItems[]){
		game = inGame;
		map = inMap;
		charac = inChar;
		//items = inItems;
	}
	public void DoExport(){
		try{
			FileOutputStream fileOut = new FileOutputStream(Adventure.path + "/" + "saveFile.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public GameExport DoImport(String path) throws ClassNotFoundException
    {
		GameExport e = new GameExport();
        try
        {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            e = (GameExport) in.readObject();
        }
        catch(IOException exception){
            exception.printStackTrace();
        }
        catch(ClassNotFoundException exception){
        	exception.printStackTrace();
        }
        return e;
    }
}
