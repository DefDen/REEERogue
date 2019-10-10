package Game;

public class GameManager 
{
	private GameObject[][] floor = new GameObject[21][79];

	public void updateFloor(char[][] floor)
	{
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				this.floor[x][y] = charToGameObject(floor[x][y]);
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
				
				break;
			case 'a':
				
				break;
				
			case 's':
				
				break;
				
			case 'd':
			
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
