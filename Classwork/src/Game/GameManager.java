package Game;

public class GameManager 
{
	private GameWindow GW;
	private GameObject[][] floor = new GameObject[21][79];
	private int playerX;
	private int playerY;

	public GameManager()
	{
		GW = new GameWindow(this);
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
		char[][] floor = new char[21][79];
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				floor[x][y] = this.floor[x][y].toChar();
			}
		}
		return floor;
	}
	
	public String playerMove(char c)
	{
		String r = "";
		switch(c)
		{
			case '1':
				r = move(1, -1);
				break;	
				
			case '2':
				r = move(1, 0);
				break;
				
			case '3':
				r = move(1, 1);
				break;
				
			case '4':
				r = move(0, -1);
				break;
			
			case '6':
				r = move(0, 1);
				break;
				
			case '7':
				r = move(-1, -1);
				break;
				
			case '8':
				r = move(-1, 0);
				break;
				
			case '9':
				r = move(-1, 1);
				break;
		}
		return r;
	}

	private String move(int y, int x)
	{
		if((y > 0 && playerY == floor.length - 1) || (y < 0 && playerY == 0) || (x > 0 && playerX == floor[0].length - 1) || (x < 0 && playerX == 0))
		{
			return "You walk into the wall";
		}
		floor[playerY + y][playerX + x] = floor[playerY][playerX];
		floor[playerY][playerX] = new EmptySpace();
		playerY += y;
		playerX += x;
		return "";
	}
	
	private GameObject charToGameObject(char c)
	{
		switch(c)
		{
			case '@':
				return new Player();
			default:
				return new EmptySpace();
		}
	}
}
