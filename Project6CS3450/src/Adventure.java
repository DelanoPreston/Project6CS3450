import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.security.CodeSource;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class Adventure extends JFrame implements ActionListener, Serializable{
	public enum Action {go, inventory, quit}
	public static int row = 0;
	public static int column = 0;
	public static GameMap map;
	public static GameChar character;
	public static String stringInput;
	public JTextField inputBox;
	public JTextArea commandList;
	public JScrollPane commandBox;
	
	public GameExport exporthere;
	
	public static File path;
		public static void main(String[] args)
		{
			try
			{
				//Scanner input = new Scanner(new File(args[0]));
				path = new File(System.getProperty("user.dir"));
				//Scanner input = new Scanner(new File("C:/Users/Preston Delano/AdventureMap.txt"));
				Scanner input = new Scanner(new File(path + "/AdventureMap.txt"));
				stringInput = input.useDelimiter("\\Z").next();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			
			//Scanner userInput = new Scanner( System.in );
			//String temp;
			
			//System.out.println("Preston Delano CS 3450 Java Programming - Program 6 - Adventure Game");
			//System.out.println("Type help to get started");
			
			Adventure window = new Adventure();
			window.setSize(450,600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
			
			/*do{
				temp = GetInput(userInput);
				if(temp.equals("inventory"))
				{
					character.GetInventory();
				}
				
				System.out.println("");
			}while(!temp.equals("quit"));*/
		}
		
		public void appendString(String input){
			commandList.append(input + "\n");
		}
		
		public Adventure(){
			map = new GameMap(this, stringInput);//needs the command line text file
			character = new GameChar(this);
			
			
			
			//make map pane
			//mapView = new GameFrame();
			//map.setBackground(Color.DARK_GRAY);
			//mapView.setSize(600, 300);
			
			//Button Panel creation
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
			buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			
			//create textfield & buttons
			inputBox = new JTextField();
			JButton enterButton = new JButton("Enter");
			JButton saveButton = new JButton("Save");
			JButton openButton = new JButton("Open");
			JButton quitButton = new JButton("Quit");
			
			//put textfield and buttons on panel
			buttonPane.add(Box.createHorizontalGlue());
			buttonPane.add(inputBox);
			buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPane.add(enterButton);
			buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPane.add(saveButton);
			buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPane.add(openButton);
			buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
			buttonPane.add(quitButton);
			
			//button actionlistener
			inputBox.addActionListener(this);
			enterButton.addActionListener(this);
			saveButton.addActionListener(this);
			openButton.addActionListener(this);
			quitButton.addActionListener(this);
			
			//key Listener
			inputBox.addKeyListener(new KVInput(this));
			
			commandList = new JTextArea(12, 1);
			commandList.setLineWrap(true);
			commandList.setWrapStyleWord(true);
			commandList.setEditable(false);
			commandBox = new JScrollPane(commandList);
			
			DefaultCaret caret = (DefaultCaret)commandList.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			//appendString("To use arrow keys, focus must be in the text box.");
			
			JPanel textButtonArea = new JPanel();
			textButtonArea.setLayout(new BorderLayout());
			textButtonArea.add(commandBox, BorderLayout.CENTER);
			textButtonArea.add(buttonPane, BorderLayout.PAGE_END);
			
			
			Container contentPane = getContentPane();
			contentPane.setLayout(new BorderLayout());
			contentPane.add(map, BorderLayout.CENTER);
			contentPane.add(textButtonArea, BorderLayout.PAGE_END);
			
			commandList.setText("To use arrow keys, focus must be in the text box.\n" +
					"Make sure this program is run in same directory as the AdventureMap.txt,\n" +
					"the items text file, and the mappics folder");
		}
		
		public String GetInput(String inUserInput)//Scanner inUserInput)
		{
			String temp = "";
			boolean repeat = true;
			String[] parts;
			String[] letters;
			
			if(inUserInput.equals("")){
				temp = inUserInput;
			}
			else{
				temp = "asdf;lkj";
			}
			
			//do
			//{
				temp = inUserInput;//.nextLine();
				parts = temp.split(" ");
				letters = parts[0].split("");
				if(!temp.equals("")){
					temp = letters[1].toUpperCase();////location 1 because when a string is split on "" the first entry is blank
				}
				
				if(temp.equals("G"))
				{
					try
					{
						letters = parts[1].split("");
						temp = letters[1].toUpperCase();//location 1 because when a string is split on "" the first entry is blank
						
						character.DirectionMover(temp);
						
						repeat = false;
					}
					catch(ArrayIndexOutOfBoundsException e){}
					temp = "go";
				}
				else if(temp.equals("I"))
				{
					repeat = false;
					//temp = "inventory";
					if(character.itemsInInventory.size() == 0){
						appendString("Your inventory is empty");
					}
					else{
						for(int i = 0; i < character.itemsInInventory.size(); i++){
							appendString(" - " + character.itemsInInventory.get(i).name);
						}
					}
				}
				else if(temp.equals("Q"))
				{
					repeat = false;
					temp = "quit";
				}
				else if (temp.equals("H"))
				{
					HelpMethod();
				}
				else if (temp.equals("T")){
					String itemName = "";
					for(int j = 1; j < parts.length; j++){
						if(j==1){
							itemName = parts[j];
						}
						else if(j>=2){
							itemName = itemName + " " + parts[j];
						}
					}
					try
					{
						//letters = parts[1].split("");
						//temp = letters[1].toUpperCase();//location 1 because when a string is split on "" the first entry is blank
						for(int i = 0; i < map.itemsOnMap.size(); i++){
							if(map.itemsOnMap.get(i).rowLocation == character.rowLocation && map.itemsOnMap.get(i).colLocation == character.colLocation){
								
								//appendString(itemName);
								
								if(itemName.equals(map.itemsOnMap.get(i).name)){
									character.itemsInInventory.add(map.itemsOnMap.get(i));
									appendString("you picked up " + map.itemsOnMap.get(i).name);
									map.itemsOnMap.remove(i);
									//appendString(Integer.toString(map.itemsOnMap.size()));
									break;
								}
							}
						}
						repeat = false;
					}
					catch(ArrayIndexOutOfBoundsException e){}
					temp = "take";
				}
				else if (temp.equals("D")){
					String itemName = "";
					for(int j = 1; j < parts.length; j++){
						if(j==1){
							itemName = parts[j];
						}
						else if(j>=2){
							itemName = itemName + " " + parts[j];
						}
					}
					try
					{
						for(int i = 0; i < character.itemsInInventory.size(); i++){
							if(character.itemsInInventory.get(i).name.equals(itemName)){
								character.itemsInInventory.get(i).rowLocation = character.rowLocation;
								character.itemsInInventory.get(i).colLocation = character.colLocation;
								appendString("you dropped " + character.itemsInInventory.get(i).name);
								map.itemsOnMap.add(character.itemsInInventory.get(i));
								character.itemsInInventory.remove(i);
							}
						}
						repeat = false;
					}
					catch(ArrayIndexOutOfBoundsException e){}
					temp = "drop";
					}
				else if(repeat){
					appendString("Please enter a correct command");
					appendString("For list of command type 'help'");
					appendString("");
				}
			//}while(repeat);
			return temp;
		}
		
		public void HelpMethod()
		{
			appendString("List of Commands\n" +
					"-Go <north, east, south, west>\n" +
					"-Take <item name>\n" +
					"-Drop <item name>\n" +
					"-Inventory\n");
		}
		public void keyPressed(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				String input = inputBox.getText();
				appendString("cmd: " + input);
				GetInput(input);
				inputBox.setText("");
			}
			else if (e.getKeyCode() == KeyEvent.VK_UP){
				appendString("Go North");
				GetInput("Go North");
		    }
			else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				appendString("Go South");
				GetInput("Go South");
		    }
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				appendString("Go East");
				GetInput("Go East");
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT){
				appendString("Go West");
				GetInput("Go West");
			}
			map.repaint();
		}
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			if(actionEvent.getActionCommand().equals("Enter")){// || ((KeyEvent) actionEvent).getKeyCode() == KeyEvent.VK_ENTER ){
				String input = inputBox.getText();
				appendString("cmd: " + input);
				GetInput(input);
				inputBox.setText("");
			}
			if(actionEvent.getActionCommand().equals("Save")){
				exporthere = new GameExport(this, map, character);
				exporthere.DoExport();
				//use the exporthere object(construct then call it)
			}
			if(actionEvent.getActionCommand().equals("Open")){
				try{
					exporthere.DoImport(Adventure.path + "/" + "saveFile.ser");
				}
				catch(ClassNotFoundException exception){
					exception.printStackTrace();
				}
				//some XML read in thing
			}
			if(actionEvent.getActionCommand().equals("Quit")){
				System.exit(0);
				//this.dispose(); //this just gets rid of the window, not the program
			}
			map.repaint();
		}
}
