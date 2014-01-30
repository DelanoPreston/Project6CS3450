import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameMap extends JPanel implements Serializable{
	char[][] map;
	int mapRows;
	int mapColumns;
	Adventure game;
	List<GameItem> itemsOnMap = new ArrayList<GameItem>();
	int tileHeight, tileWidth;
	char plainChar, mountChar, foresChar, waterChar, treasChar, outChar, persoChar;
	String plainPath, mountainPath, forestPath, waterPath, treasurePath, outPath, personPath;
	Image plain, mountain, forest, water, treasure, out, person;
	
	public GameMap(Adventure inGame, String inMapString)
	{
		game = inGame;
		this.setBackground(Color.DARK_GRAY);
		String[] mapList = inMapString.split("\r\n");
		
		String[] mapDimensions = mapList[0].split(" ");
		mapRows = Integer.parseInt(mapDimensions[0]);
		mapColumns = Integer.parseInt(mapDimensions[1]);
		map = new char[mapRows][mapColumns];
		
		char[] tempCharHolder = new char[mapColumns];
		
		int readInLine = 0;
		
		//constructs array map key
		for(int i = 0; i < mapRows; i++)//this starts at 1 because the first line is the dimensions of the map
		{
			tempCharHolder = mapList[i+1].toCharArray();
			for(int j = 0; j < mapColumns; j++)
			{
				map[i][j] = tempCharHolder[j];
			}
			readInLine = i + 1;
		}
		readInLine = readInLine + 1;
		//tile size
		String tileSize[] = mapList[readInLine].split(" ");
		tileWidth = Integer.parseInt(tileSize[0]);
		tileHeight = Integer.parseInt(tileSize[1]);
		
		//this gets the items, and puts them on the map
		try
		{
			//try and do something with directory, then the file
			Scanner input = new Scanner(new File("map1items.txt"));
			String stringInput = input.useDelimiter("\\Z").next();
			String list[] = stringInput.split("\r\n");
			//itemsOnMap = new GameItem[list.length];
			for(int i = 0; i < list.length; i++){
				String itemSpecs[] = list[i].split(";");
				itemsOnMap.add(new GameItem(Integer.parseInt(itemSpecs[0]), Integer.parseInt(itemSpecs[1]), itemSpecs[2]));
			}
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		readInLine = mapRows + 3;
		String terrainTxt[] = new String[7];
		for(int i = 0; i < 7;i++){
			terrainTxt[i] = mapList[readInLine + i];
		}
		ImageIcon ic;
		for(int i = 0; i < terrainTxt.length; i++){
			String terrainSpec[] = terrainTxt[i].split(";");
			if(terrainSpec[1].equals("plains")){
				plainChar = terrainSpec[0].charAt(0);
				plainPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//plain = ic.getImage();
			}else if(terrainSpec[1].equals("mountain")){
				mountChar = terrainSpec[0].charAt(0);
				mountainPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//mountain = ic.getImage();
			}else if(terrainSpec[1].equals("forest")){
				foresChar = terrainSpec[0].charAt(0);
				forestPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//forest = ic.getImage();
			}else if(terrainSpec[1].equals("water")){
				waterChar = terrainSpec[0].charAt(0);
				waterPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//water = ic.getImage();
			}else if(terrainSpec[1].equals("goal")){
				treasChar = terrainSpec[0].charAt(0);
				treasurePath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//treasure = ic.getImage();
			}else if(terrainSpec[1].equals("out")){
				outChar = terrainSpec[0].charAt(0);
				outPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//out = ic.getImage();
			}else if(terrainSpec[1].equals("person")){
				persoChar = terrainSpec[0].charAt(0);
				personPath = terrainSpec[2];
				//ic = new ImageIcon(Adventure.path + "/" +terrainSpec[2]);
				//person = ic.getImage();
			}
		}
	}
	public int NSMover(int NSMovement, int currRow)
	{
		if(NSMovement == -1 && currRow != 0)//////////////////North
		{
			currRow += NSMovement;//usually -1
		}
		else if(NSMovement == 1 && currRow < mapRows - 1)/////////South
		{
			currRow += 1;//usually 1
		}
		else{
			game.appendString("You cannot go that direction");
			//System.out.println("You cannot go that direction");
		}
		return currRow;
	}
	public int EWMover(int EWMovement,int currColumn)
	{
		if(EWMovement == 1 && currColumn < mapColumns - 1)///////East
		{
			currColumn += EWMovement;//usually 1
		}
		else if(EWMovement == -1 && currColumn != 0)/////////West
		{
			currColumn += EWMovement;//usually -1
		}
		else{
			game.appendString("You cannot go that direction");
		}
		return currColumn;
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		int viewRange = Adventure.character.viewRange;
		int charRow = Adventure.character.rowLocation;
		int charCol = Adventure.character.colLocation;
		
		for(int i = -viewRange + charRow; i <= viewRange + charRow; i++)
		{
				for(int j = -viewRange + charCol; j <= viewRange + charCol; j++)
				{
					if(j < 0 || j > mapColumns - 1 || i < 0 || i > mapRows - 1){
						//g2D.drawImage(out, (j + 2 - charCol)*tileWidth, (i + 2 - charRow)*tileHeight, null);
						drawTerrain(g2D, '-',  (j + 2 - charCol)*tileWidth, (i + 2 - charRow)*tileHeight);
					}
					else{
						drawTerrain(g2D, map[i][j], (j + 2 - charCol)*tileWidth, (i + 2 - charRow)*tileHeight);
					}
				}
		}
		ImageIcon ic = new ImageIcon(Adventure.path + "/" + personPath);
		g2D.drawImage(ic.getImage(), 2 * tileWidth, 2 * tileHeight, null);
	}
	public void drawTerrain(Graphics g2D, char terrain, int inX, int inY){
		String tempPath = null;
		
		if(terrain == waterChar){
			//g2D.drawImage(water, inX, inY, null);
			tempPath = waterPath;
		}else if(terrain == plainChar){
			//g2D.drawImage(plain, inX, inY, null);
			tempPath = plainPath;
		}else if(terrain == foresChar){
			//g2D.drawImage(forest, inX, inY, null);
			tempPath = forestPath;
		}else if(terrain == mountChar){
			//g2D.drawImage(mountain, inX, inY, null);
			tempPath = mountainPath;
		}else if(terrain == treasChar){
			//g2D.drawImage(treasure, inX, inY, null);
			tempPath = treasurePath;
		}else if(terrain == outChar){
			//g2D.drawImage(out, inX, inY, null);
			tempPath = outPath;
		}
		ImageIcon ic = new ImageIcon(Adventure.path + "/" +tempPath);
		g2D.drawImage(ic.getImage(), inX, inY, null);
	}
}