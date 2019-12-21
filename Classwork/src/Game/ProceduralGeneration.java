package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class ProceduralGeneration 
{
	private int floorWidth, floorHeight;
	private Node[][] nodes;
	private HashSet<Location> allCovered;
	private ArrayList<ArrayList<Location>> locationsByType;
	private ArrayList<ArrayList<Rectangle>> rectanglesByType;
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
		rectanglesByType = new ArrayList<ArrayList<Rectangle>>();
		for(int x = 0; x < 10; x++)
		{
			locationsByType.add(new ArrayList<Location>());
			rectanglesByType.add(new ArrayList<Rectangle>());
		}
	}

	public char[][] generateFloor(boolean goingDown)
	{
		this.goingDown = goingDown;
		reset();
		generateBlankFloor();
		makeZones(10, 1, 1);
		printNodes();
		makeZones(5, 2, 1);
		printNodes();
		setStairs();
		printNodes();
		makePaths();
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
				nodes[y][x] = new Node(0, new Location(y, x));
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
		for(int x = 0; x < num; x++)
		{

			Rectangle add = new Rectangle(new Location((int)(floorHeight * Math.random()), (int)(floorWidth * Math.random())), (int)(3 * Math.random() + 3), (int)(3 * Math.random() + 3), boundarySize);
			if(!add.isValid() || add.intersects(allCovered))
			{
				x--;
				continue;
			}
			fillRectangle(add, type);
			locationsByType.get(type).addAll(add.covered);
			rectanglesByType.get(type).add(add);
			allCovered.addAll(add.boundary);
		}
	}

	private void makePaths()
	{
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		ArrayList<Rectangle> visited = new ArrayList<Rectangle>();
		do
		{
			for(int x = 0; x < rectanglesByType.get(1).size() - 1; x++)
			{
				rectanglesByType.get(1).get(x).connect(rectanglesByType.get(1).get(x + 1));
				if(!visited.contains(rectanglesByType.get(1).get(x)))
				{
					visited.add(rectanglesByType.get(1).get(x));
				}
				if(!visited.contains(rectanglesByType.get(1).get(x + 1)))
				{
					visited.add(rectanglesByType.get(1).get(x + 1));
				}
			}
		}
		while(visited.size() != rectanglesByType.get(1).size() && allConnected());
		for(int x = 0; x < rectanglesByType.get(1).size(); x++)
		{
			for(Connection connection : rectanglesByType.get(1).get(x).connections)
			{
				paths.add(findPath(connection));
				for(Node[] nodeArr : nodes)
				{
					for(Node node1 : nodeArr)
					{
						node1.visited = false;
					}
				}
			}
		}
		for(ArrayList<Node> path : paths)
		{
			for(Node pathNode : path)
			{
				locationsByType.get(0).remove(pathNode.loc);
				pathNode.type = 5;
			}
		}
	}

	private ArrayList<Node> findPath(Connection connection)
	{
		Node start = nodes[connection.rect1.start.y][connection.rect1.start.x];
		Node end = nodes[connection.rect2.start.y][connection.rect2.start.x];
		Queue<Node> q = new LinkedList<Node>();
		q.add(start);
		return findPath(q, end);
	}

	private ArrayList<Node> findPath(Queue<Node> q, Node end)
	{
		Node node = q.remove();

		for(int x = 0; x < node.adj.length; x++)
		{
			if(node.adj[x] != null && (node.adj[x].type == 0 || node.adj[x].type == 5) && !node.adj[x].visited)
			{
				if(node.adj[x] .equals(end))
				{
					return getPath(end, new ArrayList<Node>());
				}
				q.add(node.adj[x]);
				node.adj[x].visited = true;
			}
		}
		return findPath(q, end);
	}

	private ArrayList<Node> getPath(Node current, ArrayList<Node> path)
	{
		if(current.prev == null)
		{
			return path;
		}
		path.add(current);
		return getPath(current.prev, path);
	}

	private boolean allConnected()
	{
		ArrayList<Rectangle> visited = new ArrayList<Rectangle>();
		Queue<Rectangle> q = new LinkedList<Rectangle>();
		q.add(rectanglesByType.get(1).get(0));
		return allConnected(q, visited);
	}

	private boolean allConnected(Queue<Rectangle> q, ArrayList<Rectangle> visited)
	{
		if(visited.size() == rectanglesByType.get(1).size())
		{
			return true;
		}
		if(q.isEmpty())
		{
			return false;
		}
		q.addAll(q.remove().connected);
		return allConnected(q, visited);
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
		private Location loc;
		private Node[] adj = new Node[9];

		private Node(int type, Location loc)
		{
			this.type = type;
			this.loc = loc;
		}
	}

	private class Rectangle
	{
		private Location start, end;
		private int height, width, boundarySize;
		private HashSet<Location> covered = new HashSet<Location>(), boundary = new HashSet<Location>();
		private ArrayList<Rectangle> connected = new ArrayList<Rectangle>();
		private ArrayList<Connection> connections = new ArrayList<Connection>();

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

		private boolean connect(Rectangle rect)
		{
			if(this.equals(rect) || connected.contains(rect))
			{
				return false;
			}
			rect.connected.add(this);
			connected.add(rect);
			connections.add(new Connection(this, rect));
			return true;
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

	private class Connection
	{
		private Rectangle rect1, rect2;

		private Connection(Rectangle rect1, Rectangle rect2)
		{
			this.rect1 = rect1;
			this.rect2 = rect2;
		}
	}
}