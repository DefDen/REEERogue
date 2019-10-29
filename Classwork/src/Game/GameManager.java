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
		switch(c)
		{
			//Movement
			case '1':
				return move(1, -1);
				
			case '2':
				return move(1, 0);
				
			case '3':
				return move(1, 1);
				
			case '4':
				return move(0, -1);
				
			case '5':
				return move(0, 0);
				
			case '6':
				return move(0, 1);
				
			case '7':
				return move(-1, -1);
				
			case '8':
				return move(-1, 0);
				
			case '9':
				return move(-1, 1);
				
			//Traversing stairs
			case '>':
				if(underPlayer.toChar() == '>')
				{
					//loadFloor(FG.getFloorNum() + 1);
					return "You descend the stairs";
				}
				return "There are no stairs here";
				
			case '<':
				if(underPlayer.toChar() == '<')
				{
					//loadFloor(FG.getFloorNum() - 1);
					return "You ascend the stairs";
				}
				return "There are no stairs here";
				
			default: 
				return "";
		}
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
		underPlayer = floor[playerY + y][playerX + x];
		floor[playerY + y][playerX + x] = player;
		playerY += y;
		playerX += x;
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
