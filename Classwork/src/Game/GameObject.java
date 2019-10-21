package Game;

public abstract class GameObject 
{
	protected char id;
	protected String name;
	protected int x;
	protected int y;
	protected boolean terrain = false;
	protected boolean item = false;
	protected boolean character = false;
	protected boolean emptySpace = false;

	public char toChar()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public boolean isTerrain()
	{
		return terrain;
	}
	public boolean isItem()
	{
		return item;
	}
	public boolean isCharacter()
	{
		return character;
	}
	public boolean isEmptySpace()
	{
		return emptySpace;
	}
}
