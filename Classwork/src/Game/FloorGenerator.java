package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
			for(int y = 1; y < floorHeight; y++)
			{
				floor[x][y] = '.';
			}
			
		}
		return floor;
	}
	
	public void generateFloorFile(String fileName)
	{
		File file = new File(fileName);
		FileWriter fr;
		try
		{
			fr = new FileWriter(file);
			fr.write(generate().toString());
		}
		catch(IOException e)
		{
		}
	}
}
