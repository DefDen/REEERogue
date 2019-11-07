package Game;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Game.GameObjects.EmptySpace;
import Game.GameObjects.Player;
import Game.GameObjects.StairsDown;
import Game.GameObjects.StairsUp;
import Game.GameObjects.Wall;

public class GameManager 
{
	private static final int floorWidth = 22, floorHeight = 79, WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private GameObject[][] floor = new GameObject[floorWidth][floorHeight];
	private int playerX, playerY, isFirstMove = 1;
	private JFrame window;
	private JLabel floorLabel, messageLabel;
	private ArrayList<String> messages = new ArrayList<String>();
	private GameObject player = new Player(), underPlayer = new StairsUp();

	public GameManager()
	{
		messageLabel = new JLabel();
		makeWindow();
		loadLevel("a");
		floorLabel = new JLabel(floorToString(), SwingConstants.CENTER);
		JTextField text = makeJTextField();
		JPanel panel = new JPanel(new BorderLayout());
		for(int x = 0; x < 2; x++)
		{
			messages.add("a");
		}
		updateMessage("a");
		window.add(panel);
		panel.add(messageLabel, BorderLayout.PAGE_START);
		panel.add(floorLabel, BorderLayout.CENTER);
		
		panel.add(text, BorderLayout.PAGE_END);
		window.pack();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void loadLevel(String fileName)
	{
		try
		{
			loadLevel(new File(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.print("Error: Cannot load floor");
		}
	}

	private void loadLevel(File floorFile) throws FileNotFoundException
	{
		Scanner scan = new Scanner(floorFile);
		char[][] charFloor = new char[floorWidth][floorHeight];
		for(int x = 0; scan.hasNextLine(); x++)
		{
			charFloor[x] = scan.nextLine().toCharArray();
		}
		updateFloor(charFloor);
		scan.close();
	}

	private JTextField makeJTextField()
	{
		JTextField text = new JTextField("Is it a A?");
		text.setBounds(100, 0, 25, 10);
		KeyListener listener = new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent event)
			{
				updateMessage(playerMove(event.getKeyChar()));
				text.setText("");
				floorLabel.setText(floorToString());
			}
			@Override
			public void keyReleased(KeyEvent event){}
			@Override
			public void keyTyped(KeyEvent event){}
		};
		text.addKeyListener(listener);
		return text;
	}

	private void updateMessage(String message)
	{
		messages.add(message);
		String curMessage = "<html><font face=\"monospace\"\n<br>";
		for(int x = 3; x > 0; x--)
		{
			if(messages.get(messages.size() - x).equals("a"))
			{
				curMessage += "<p style=\"color:#ffffff\">";
			}
			else
			{
				switch(x)
				{
				case 1:
					curMessage += "<p style=\"color:#000000\">";
					break;

				case 2:
					curMessage += "<p style=\"color:#808080\">";
					break;

				case 3:
					curMessage += "<p style=\"color:#b3b3b3\">";
					break;
				}
			}
			curMessage += messages.get(messages.size() - x) + "\n<br>";
		}
		curMessage += "<html>";
		messageLabel.setText(curMessage);
	}

	private void makeWindow()
	{
		window = new JFrame("REEERogue");
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setVisible(true);
	}
	
	public String floorToString()
	{
		String strFloor = "<html><font face=\"monospace\"";
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				strFloor += floor[x][y].toChar();
			}
			strFloor += "\n<br>";
		}
		strFloor += "<html>";
		return strFloor;
	}
	
	public void updateFloor(char[][] charFloor)
	{
		for(int y = 0; y < floor.length; y++)
		{
			for(int x = 0; x < floor[y].length; x++)
			{
				this.floor[y][x] = charToGameObject(charFloor[y][x]);
				if(charFloor[y][x] == '@')
				{
					playerX = x;
					playerY = y;
				}
			}
		}
	}
	
	public GameObject[][] charArrayToGameObjectArray(char[][] charFloor)
	{
		GameObject[][] r = new GameObject[floorWidth][floorHeight];
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				r[x][y] = charToGameObject(charFloor[x][y]);
			}
		}
		return r;
	}
	
	private void loadFloor(int floorNum)
	{
		loadLevel("" + floorNum);
	}
	
	public String playerMove(char c)
	{
		String message = "";
		switch(c)
		{
			//Movement
			case '1':
				message = move(1, -1);
				break;
				
			case '2':
				message = move(1, 0);
				break;
				
			case '3':
				message = move(1, 1);
				break;
				
			case '4':
				message = move(0, -1);
				break;
				
			case '5':
				message = move(0, 0);
				break;
				
			case '6':
				message = move(0, 1);
				break;
				
			case '7':
				message = move(-1, -1);
				break;
				
			case '8':
				message = move(-1, 0);
				break;
				
			case '9':
				message = move(-1, 1);
				break;
				
			//Traversing stairs
			case '>':
				if(underPlayer.toChar() == '>')
				{
					//loadFloor(FG.getFloorNum() + 1);
					isFirstMove = 1;
					message = "You descend the stairs";
					break;
				}
				message = "There are no stairs here";
				break;
				
			case '<':
				if(underPlayer.toChar() == '<')
				{
					//loadFloor(FG.getFloorNum() - 1);
					isFirstMove = -1; 
					message = "You ascend the stairs";
					break;
				}
				message = "There are no stairs here";
				break;
				
			default: 
				message = "";
				break;
		}
		return message;
	}
	
	private String move(int y, int x)
	{
		//No direction
		if(y == 0 && x == 0)
		{
			return "You walk into yourself";
		}
		//Hits outer border
		if((y > 0 && playerY == floor.length - 1) || (y < 0 && playerY == 0) || (x > 0 && playerX == floor[0].length - 1) || (x < 0 && playerX == 0))
		{
			return "You walk into the wall";
		}
		//Hits terrain                                         
		if(floor[playerY + y][playerX + x].isTerrain())
		{
			return "You walk into the " + floor[playerY + y][playerX + x].getName();
		}
		//Attacking character
		if(floor[playerY + y][playerX + x].isCharacter())
		{
			return "You hit the " + floor[playerY + y][playerX + x].getName();
		}
		//Moves properly
		switch (isFirstMove)
		{
			case 1:
				isFirstMove = 0;
				floor[playerY][playerX] = new StairsUp();
				break;
				
			case -1:
				isFirstMove = 0;
				floor[playerY][playerX] = new StairsDown();
				break;
				
			default:
				floor[playerY][playerX] = underPlayer.copy();
				break;
		}
		underPlayer = floor[playerY + y][playerX + x].copy();
		floor[playerY + y][playerX + x] = player;
		playerY += y;
		playerX += x;
		for(GameObject[] gx : floor)
		{
			for(GameObject gy : gx)
			{
				System.out.print(gy.toChar());
			}
			System.out.println();
		}
		return "";
	}

	private GameObject charToGameObject(char c)
	{
		switch(c)
		{
			case '@':
				return player;
				
			case '#':
				return new Wall();
				
			case '>':
				return new StairsDown();
				
			case '<':
				return new StairsUp();
				
			default:
				return new EmptySpace();
		}
	}
	

	public static void main(String args[])
	{
		GameManager GM = new GameManager();
	}
}
