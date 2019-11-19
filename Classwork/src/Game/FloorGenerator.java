package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FloorGenerator 
{
	private int floorWidth, floorHeight, floorNum = 0;
	
	public FloorGenerator(int floorWidth, int floorHeight)
	{
		this.floorWidth = floorWidth;
		this.floorHeight = floorHeight;
	}
	
	public FloorGenerator(GameManager GM)
	{
		this.floorWidth = GM.getFloorWidth();
		this.floorHeight = GM.getFloorWidth();
	}
	
	private char[][] generate()
	{
		return generate(floorWidth, floorHeight);
	}
	
	private char[][] generate(int floorWidth, int floorHeight)
	{
		char[][] floor = new char[floorWidth][floorHeight];
		for(int x = 0; x < floorHeight; x++)
		{
			floor[0][x] = '#';
		}
		for(int x = 0; x < floorHeight; x++)
		{
			for(int y = 1; y < floorWidth; y++)
			{
				floor[y][x] = '.';
			}
		}
		return floor;
	}

	private String generateToString()
	{
		char[][] floor = generate();
		String strFloor = "";
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				strFloor += floor[x][y];
			}
			strFloor += "\n";
		}
		return strFloor;
	}

	private void generateFloorFile(String fileName)
	{
		BufferedWriter writer;
		try 
		{
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(generateToString());
			writer.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public int getFloorNum()
	{
		return floorNum;
	}

	public int generateNextFloor()
	{
		floorNum++;
		generateFloorFile("" + floorNum);
		return floorNum;
	}
}
