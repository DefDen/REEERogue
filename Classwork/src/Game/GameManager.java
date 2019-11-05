package Game;

import Game.GameObjects.EmptySpace;
import Game.GameObjects.Player;
import Game.GameObjects.StairsDown;
import Game.GameObjects.StairsUp;
import Game.GameObjects.Wall;

public class GameManager 
{
	private static final int floorWidth = 22, floorHeight = 79;
	private GameWindow GW;
	private FloorGenerator FG;
	private GameObject[][] floor = new GameObject[floorWidth][floorHeight];
	private int playerX, playerY;
	private GameObject player = new Player();
	private GameObject underPlayer = new StairsUp();
	private int isFirstMove = 1;

	public GameManager()
	{
		GW = new GameWindow(this);
		FG = new FloorGenerator(this);
	}
	
	public int getFloorWidth()
	{
		return floorWidth;
	}
	
	public int getFloorHeight()
	{
		return floorHeight;
	}
	
	public void updateFloor(char[][] floor)
	{
		for(int y = 0; y < floor.length; y++)
		{
			for(int x = 0; x < floor[y].length; x++)
			{
				this.floor[y][x] = charToGameObject(floor[y][x]);
				if(floor[y][x] == '@')
				{
					playerX = x;
					playerY = y;
				}
			}
		}
	}
	
	public char[][] getUpdatedFloor()
	{
		char[][] floor = new char[floorWidth][floorHeight];
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				floor[x][y] = this.floor[x][y].toChar();
			}
		}
		return floor;
	}
	
	private void loadFloor(int floorNum)
	{
		GW.loadLevel("" + floorNum);
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
		GW.updateFloor(getUpdatedFloor());
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
		underPlayer = floor[playerY + y][playerX + x];
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
}
