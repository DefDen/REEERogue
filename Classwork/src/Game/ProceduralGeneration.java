package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ProceduralGeneration 
{
	private int floorWidth, floorHeight;
	private Node[][] nodes;
	private HashSet<Location> allCovered = new HashSet<Location>();
	private ArrayList<ArrayList<Location>> locationsByType = new ArrayList<ArrayList<Location>>();

	public ProceduralGeneration(int floorWidth, int floorHeight)
	{
		this.floorWidth = floorWidth;
		this.floorHeight = floorHeight;
		for(int x = 0; x < 10; x++)
		{
 			locationsByType.add(new ArrayList<Location>());
		}
	}

	private void GenerateBlankFloor()
	{
		nodes = new Node[floorHeight][floorWidth];
		for(int y = 0; y < nodes.length; y++)
		{
			for(int x = 0; x < nodes[y].length; x++)
			{
				nodes[y][x] = new Node(0);
			}
		}
		for(int x = 0; x < nodes.length; x++)
		{
			for(int y = 0; y < nodes.length; y++)
			{
				for(int i = -1; i < 2; i++)
				{
					for(int j = 1; j < 2; j++)
					{
						int z;
						String check = j + " " + i;
						switch(check)
						{
						case "-1 -1":
							z = 6;
							break;

						case "0 -1":
							z = 3;
							break;

						case "1 -1":
							z = 0;
							break;

						case "-1 0":
							z = 7;
							break;

						case "0 0":
							z = 4;
							break;

						case "1 0":
							z = 1;
							break;

						case "-1 1":
							z = 8;
							break;

						case "0 1":
							z = 5;
							break;

						case "1 1":
							z = 2;
							break;

						default:
							z = -1;
						}
						try
						{
							nodes[y][x].adj[z] = nodes[y + j][x + i];
						}
						catch(Exception e)
						{

						}
					}
				}
			}
		}
	}

	public void GenerateFloor()
	{
		GenerateBlankFloor();
		MakeZones(5, 1);
		MakeZones(5, 2);
	}

	private void MakeZones(int num, int type)
	{
		int y = 0;
		for(int x = 0; x < num; x++)
		{
			if(y > 1000)
			{
				break;
			}
			y++;
			Rectangle add = new Rectangle(new Location((int)(floorHeight * Math.random()), (int)(floorHeight * Math.random())), (int)(3 * Math.random() + 3), (int)(3 * Math.random() + 3));
			if(!add.isValid())
			{
				x--;
				continue;
			}
			//				for(int z = 0; z < allRectangles.size(); z++)
			//				{
			//					if(add.intersects(allRectangles.get(z)))
			//					{
			//						x--;
			//						continue;
			//					}
			//				}
			if(add.intersects(allCovered))
			{
				x--;
				continue;
			}
			fillRectangle(add, type);
			locationsByType.get(type).addAll(add.covered);
			allCovered.addAll(add.covered);
		}
	}

	private void fillRectangle(Rectangle rectangle, int type)
	{
		for(int y = 0; y < rectangle.height; y++)
		{
			for(int x = 0; x < rectangle.width; x++)
			{
				nodes[rectangle.start.y + y][rectangle.start.x + x].type = type;
			}
		}
	}

	public int[][] getIntNodes()
	{
		int[][] r = new int[floorWidth][floorHeight];
		for(int x = 0; x < nodes.length; x++)
		{
			for(int y = 0; y < nodes[x].length; y++)
			{
				r[y][x] = nodes[x][y].type;
			}
		}
		return r;
	}

	public void printNodes()
	{
		int[][] p = getIntNodes();
		for(int[] outer : p)
		{
			for(int inner : outer)
			{
				System.out.print(inner);
			}
			System.out.println();
		}
	}

	private class Node
	{
		private int type;
		private Node[] adj = new Node[9];

		private Node(int type)
		{
			this.type = type;
		}
	}

	private class Rectangle
	{
		private Location start, end;
		private int height, width;
		private HashSet<Location> covered = new HashSet<Location>(), boundry = new HashSet<Location>();

		private Rectangle(Location start, int height, int width)
		{
			this.start = new Location(start.y, start.x);
			this.end = new Location(start.y + height, start.x + width);
			this.height = height;
			this.width = width;

			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					covered.add(new Location(start.y + y, start.x + x));
				}
			}

			for(int y = -1; y <= height; y++)
			{
				for(int x = -1; x <= width; x++)
				{
					boundry.add(new Location(start.y + y, start.x + x));
				}
			}
		}

		private boolean isValid()
		{
			if(start.y < 0 || start.x < 0 || end.y >= floorHeight || end.y >= floorWidth)
			{
				return false;
			}
			return true;
		}

		private boolean intersects(Rectangle rect)
		{
			for(Object point : boundry.toArray())
			{
				if(rect.boundry.contains((String) point))
				{
					return true;
				}
			}
			return false;
		}

		private boolean intersects(HashSet<Location> allCovered)
		{
			for(Location loc : covered)
			{
				if(allCovered.contains(loc))
				{
					return true;
				}
			}
			return false;
		}
	}

	private class Location
	{
		int y, x;
		private Location(int y, int x)
		{
			this.y = y;
			this.x = x;
		}

		private int hash()
		{
			return y * floorWidth + x;
		}

		private boolean equals(Location loc)
		{
			if(y == loc.y && x == loc.x)
			{
				return true;
			}
			return false;
		}

		private boolean equals(int y, int x)
		{
			if(this.y == y && this.x == x)
			{
				return true;
			}
			return false;
		}
	}
}