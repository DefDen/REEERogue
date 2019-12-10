package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ProceduralGeneration 
{
	private int floorWidth, floorHeight;
	private Node[][] nodes;
	private HashSet<Location> allCovered;
	private ArrayList<ArrayList<Location>> locationsByType;

	public ProceduralGeneration(int floorWidth, int floorHeight)
	{
		this.floorWidth = floorWidth;
		this.floorHeight = floorHeight;
	}
	
	private void reset()
	{
		nodes = null;
		allCovered = new HashSet<Location>();
		locationsByType = new ArrayList<ArrayList<Location>>();
		for(int x = 0; x < 10; x++)
		{
			locationsByType.add(new ArrayList<Location>());
		}
	}

	public char[][] generateFloor()
	{
		reset();
		generateBlankFloor();
		makeZones(10, 1);
		printNodes();
		makeZones(10, 2);
		printNodes();
		setStairs();
		printNodes();
		return nodesToCharArray();
	}
	
	private char[][] nodesToCharArray()
	{
		char[][] r = new char[floorWidth][floorHeight];
		for(int x = 0; x < nodes.length; x++)
		{
			for(int y = 0; y < nodes[x].length; y++)
			{
				char c = ' ';
				switch(nodes[x][y].type)
				{
				case 0:
					c = '#';
					break;
				
				case 1:
					c = '.';
					break;
					
				case 2:
					c = '!';
					break;
					
				case 3:
					c = '@';
					break;
					
				case 4:
					c = '>';
					break;
				}
				r[y][x] = c;
			}
		}
		return r;
	}

	private void generateBlankFloor()
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

	private void makeZones(int num, int type)
	{
		int y = 0;
		for(int x = 0; x < num; x++)
		{
			
			Rectangle add = new Rectangle(new Location((int)(floorHeight * Math.random()), (int)(floorWidth * Math.random())), (int)(3 * Math.random() + 3), (int)(3 * Math.random() + 3));
			if(!add.isValid() || add.intersects(allCovered))
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
			fillRectangle(add, type);
			locationsByType.get(type).addAll(add.covered);
			allCovered.addAll(add.boundry);
		}
	}

	private void setStairs()
	{
		for(int x = 3; x < 5; x++)
		{
			Location rand = locationsByType.get(1).remove((int)(locationsByType.get(1).size() * Math.random()));
			nodes[rand.y][rand.x].type = x;
			locationsByType.get(x).add(rand);
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
		System.out.println();
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
		private static final int BORDERSIZE = 1;
		private Location start, end;
		private int height, width;
		private HashSet<Location> covered = new HashSet<Location>(), boundry = new HashSet<Location>();

		private Rectangle(Location start, int height, int width)
		{
			this.start = new Location(start.y, start.x);
			this.end = new Location(start.y + height, start.x + width);
			this.height = height;
			this.width = width;

			for(int y = 0; y <= height; y++)
			{
				for(int x = 0; x <= width; x++)
				{
					covered.add(new Location(start.y + y, start.x + x));
				}
			}

			for(int y = -BORDERSIZE; y < height + BORDERSIZE; y++)
			{
				for(int x = -BORDERSIZE; x < width + BORDERSIZE; x++)
				{
					boundry.add(new Location(start.y + y, start.x + x));
				}
			}
		}

		private boolean isValid()
		{
			if(start.y < 0 || start.x < 0 || end.y >= floorHeight || end.x >= floorWidth)
			{
				return false;
			}
			return true;
		}

		private boolean intersects(Rectangle rect)
		{
			for(Location loc : boundry)
			{
				for(Location loc2 : rect.boundry)
					if(loc.equals(loc2))
					{
						return true;
					}
			}
			return false;
		}

		private boolean intersects(HashSet<Location> allCovered)
		{
			for(Location loc : boundry)
			{
				for(Location loc2 : allCovered)
					if(loc.equals(loc2))
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