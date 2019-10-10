package Game;

public class Player extends GameObject
{
	public Player()
	{
		id = '@';
		character = true;
	}
	
	public Player(int x, int y)
	{
		id = '@';
		character = true;
		this.x = x;
		this.y = y;
	}
}
