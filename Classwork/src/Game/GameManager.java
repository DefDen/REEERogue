package Game;

public class GameManager 
{
	private GameObject[][] floor = new GameObject[21][79];
	private int playerX;
	private int playerY;

	public void updateFloor(char[][] floor)
	{
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				this.floor[x][y] = charToGameObject(floor[x][y]);
				if(floor[x][y] == '@')
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
		switch(c)
		{
			case 'w':
				floor[playerY + 1][playerX] = floor[playerY][playerX];
				floor[playerY][playerX] = new EmptySpace();
				playerY++;
				break;
				
			case 'a':
				floor[playerY][playerX - 1] = floor[playerY][playerX];
				floor[playerY][playerX] = new EmptySpace();
				playerX--;
				break;
				
			case 's':
				floor[playerY - 1][playerX] = floor[playerY][playerX];
				floor[playerY][playerX] = new EmptySpace();
				playerY--;
				break;
				
			case 'd':
				floor[playerY][playerX + 1] = floor[playerY][playerX];
				floor[playerY][playerX] = new EmptySpace();
				playerX++;
				break;
		}
		return "a";
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
