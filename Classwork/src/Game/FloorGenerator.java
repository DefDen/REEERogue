package Game;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
			String line = "";
			for(int y = 1; y < floorHeight; y++)
			{
				floor[x][y] = '.';
				line += floor[x][y];
			}
			
		}
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		for(int x = 0; x < floorWidth; x++)
		{
			String line = "";
			for(int y = 0; y < floorHeight; y++)
			{
				line += floor[x][y];
			}
			
		}
		return floor;
	}
}
