package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class ProceduralGeneration 
{
	private int floorWidth, floorHeight;
	private Node[][] nodes;
	private HashSet<Location> allCovered;
	private ArrayList<ArrayList<Location>> locationsByType;
	private boolean goingDown;

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

	public char[][] generateFloor(boolean goingDown)
	{
		this.goingDown = goingDown;
		reset();
		generateBlankFloor();
		makeZones(1, 1, 1);
		printNodes();
		makeZones(10, 2, 1);
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
					c = (goingDown) ? '>' : '<';
					break;
				
				case 5:
					c = '.';
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

	private void makeZones(int num, int type, int boundarySize)
	{
		int y = 0;
		for(int x = 0; x < num; x++)
		{
			
			Rectangle add = new Rectangle(new Location((int)(floorHeight * Math.random()), (int)(floorWidth * Math.random())), (int)(3 * Math.random() + 3), (int)(3 * Math.random() + 3), boundarySize);
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
			allCovered.addAll(add.boundary);
		}
	}
	
	private void makePaths()
	{
		ArrayList<Node> path = findPath();
		for(int x = 0; x < path.size(); x++)
		{
			path.get(x).type = 5;
			locationsByType.get(1).remove(new Location(path.get(x)))
		}
	}
	
	private ArrayList<Node> findPath()
	{
		Queue<Node> q = new Queue<Node>();
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
		private Node prev;
		private boolean visited;
		private Node[] adj = new Node[9];

		private Node(int type)
		{
			this.type = type;
		}
	}

	private class Rectangle
	{
		private Location start, end;
		private int height, width, boundarySize;
		private HashSet<Location> covered = new HashSet<Location>(), boundary = new HashSet<Location>();
		private ArrayList<Location> border = new ArrayList<Location>();

		private Rectangle(Location start, int height, int width, int boundarySize)
		{
			this.start = new Location(start.y, start.x);
			this.end = new Location(start.y + height, start.x + width);
			this.height = height;
			this.width = width;
			this.boundarySize = boundarySize;

			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					covered.add(new Location(start.y + y, start.x + x));
					if(y == 0 || y == height - 1 || x == 0 || x == width - 1)
					{
						border.add(new Location(start.y + y, start.x + x));
					}
				}
			}

			for(int y = -boundarySize; y < height + boundarySize; y++)
			{
				for(int x = -boundarySize; x < width + boundarySize; x++)
				{
					boundary.add(new Location(start.y + y, start.x + x));
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
			for(Location loc : boundary)
			{
				for(Location loc2 : rect.boundary)
					if(loc.equals(loc2))
					{
						return true;
					}
			}
			return false;
		}

		private boolean intersects(HashSet<Location> allCovered)
		{
			for(Location loc : boundary)
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