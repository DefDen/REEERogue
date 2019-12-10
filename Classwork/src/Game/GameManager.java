package Game;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Game.GameObjects.EmptySpace;
import Game.GameObjects.ImpassableWall;
import Game.GameObjects.Player;
import Game.GameObjects.StairsDown;
import Game.GameObjects.StairsUp;
import Game.GameObjects.Wall;

public class GameManager 
{
	private static final int floorWidth = 22, floorHeight = 79, WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private GameObject[][] floor = new GameObject[floorWidth][floorHeight];
	private int playerX, playerY, floorNum = 0;
	private JFrame window;
	private JLabel floorLabel, messageLabel, statusLabel;
	private ArrayList<String> messages = new ArrayList<String>();
	private GameObject player = new Player(), underPlayer = new StairsUp();
	private ProceduralGeneration PG = new ProceduralGeneration(floorWidth, floorHeight);
	private boolean goingDown;
	public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };

	public GameManager()
	{
		messageLabel = new JLabel();
		statusLabel = new JLabel();
		makeWindow();
		deleteAllFloors();
		loadFloor("a");
		updateFileToFloor();
		floorLabel = new JLabel(floorToHTMLString(), SwingConstants.CENTER);
		JTextField text = makeJTextField();
		JPanel panel = new JPanel(new BorderLayout());
		for(int x = 0; x < 2; x++)
		{
			messages.add("a");
		}
		updateStatus();
		updateMessage("a");
		window.add(panel);
		panel.add(messageLabel, BorderLayout.PAGE_START);
		panel.add(floorLabel, BorderLayout.CENTER);
		panel.add(statusLabel, BorderLayout.EAST);
		panel.add(text, BorderLayout.PAGE_END);
		window.pack();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		PG.generateFloor(true);
		PG.printNodes();
	}

	private final char getAscii(int code)
	{
        if (code >= 0x80 && code <= 0xFF)
        {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }

	private void loadFloor(String fileName)
	{
		if(!(new File("" + floorNum)).exists())
		{
			generateFloorFile("" + floorNum);
		}
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

	private void deleteAllFloors()
	{
		for(int x = -50; x <= 50; x++)
		{
			(new File("" + x)).delete();
		}
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
				floorLabel.setText(floorToHTMLString());
			}
			@Override
			public void keyReleased(KeyEvent event){}
			@Override
			public void keyTyped(KeyEvent event){}
		};
		text.addKeyListener(listener);
		return text;
	}

	private void updateFileToFloor()
	{
		(new File("" + floorNum)).delete();
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("" + floorNum));
			writer.write(floorToString());
			writer.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private void updateStatus()
	{
		statusLabel.setText("Floor: " + floorNum + " ");
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

	private String floorToHTMLString()
	{
		String strFloor = "<html><font face=\"monospace\"";
		for(GameObject[] gx : floor)
		{
			for(GameObject gy : gx)
			{
				//Since the string is using HTML formatting, '<' is a special case and is represented as "&lt;"
				if(gy.toChar() == '<')
				{
					strFloor += "&lt;";
				}
				else
				{
					strFloor += gy.toChar();
				}
			}
			strFloor += "\n<br>";
		}
		strFloor += "<html>";
		return strFloor;
	}

	public String floorToString()
	{
		String strFloor = "";
		for(GameObject[] gx : floor)
		{
			for(GameObject gy : gx)
			{
				strFloor += gy.toChar();
			}
			strFloor += "\n";
		}
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

	private void generateFloorFile(String fileName)
	{
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("" + fileName));
			writer.write(generateToString());
			writer.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	private char[][] generateBlankFloor()
	{
		char[][] floor = new char[floorWidth][floorHeight];
		for(int x = 0; x < floorHeight; x++)
		{
			floor[0][x] = '#';
		}
		for(int x = 0; x < floorHeight; x++)
		{
			for(int y = 1; y < floorWidth; y++)
			{
				floor[y][x] = '.';
			}
		}
		return floor;
	}

	private char[][] generateNewFloor()
	{
		char[][] floor = PG.generateFloor(goingDown);
		return floor;
	}

	private String generateToString()
	{
		char[][] floor = generateNewFloor();
		String strFloor = "";
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				strFloor += floor[x][y];
			}
			strFloor += "\n";
		}
		return strFloor;
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
				floorNum++;
				goingDown = true;
				loadFloor("" + floorNum);
				underPlayer = new StairsUp();
				message = "You descend the stairs";
				break;
			}
			message = "There are no stairs here";
			break;

		case '<':
			if(underPlayer.toChar() == '<')
			{
				floorNum--;
				goingDown = false;
				loadFloor("" + floorNum);
				underPlayer = new StairsDown();
				message = "You ascend the stairs";
				break;
			}
			message = "There are no stairs here";
			break;

		default: 
			message = "";
			break;
		}
		updateFileToFloor();
		updateStatus();
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
		floor[playerY][playerX] = underPlayer.copy();
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
		System.out.println();
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
			
		case '!':
			return new ImpassableWall();

		default:
			return new EmptySpace();
		}
	}

	public static void main(String args[])
	{
		GameManager GM = new GameManager();
	}
}