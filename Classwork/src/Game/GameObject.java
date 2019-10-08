package Game;

public abstract class GameObject 
{
	protected boolean terrain = false;
	private boolean item = false;
	
	public boolean isTerrain()
	{
		return terrain;
	}
	public boolean isItem()
	{
		return item;
	}
}
