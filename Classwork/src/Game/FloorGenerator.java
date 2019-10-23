package Game;

public class FloorGenerator 
{
	private int floorWidth, floorHeight;
	
	public FloorGenerator(int floorWidth, int floorHeight)
	{
		this.floorWidth = floorWidth;
		this.floorHeight = floorHeight;
	}
	
	public char[][] generate()
	{
		return generate(floorWidth, floorHeight);
	}
	
	public char[][] generate(int floorWidth, int floorHeight)
	{
		char[][] floor = new char[floorWidth][floorHeight];
		for(int x = 0; x < floorWidth; x++)
		{
			floor[x][0] = '#';
		}
		for(int x = 0; x < floorWidth; x++)
		{
			for(int y = 1; y < floorHeight; y++)
			{
				floor[][];
			}
		}
		return floor;
	}
}
