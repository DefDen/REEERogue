package testPackage;

public class TestClass 
{
	public static void main (String[] args)
	{
		System.out.print(getPlusOne(1++));
	}
	public static int getPlusOne(int x)
	{
		return x++;
	}
	public static int min(int x, int y, int z)
	{
		return Math.min(Math.min(x, y), z);
	}
	public static int max(int x, int y, int z)
	{
		return Math.max(Math.max(x, y), z);
	}
	public static void ellipse()
	{
		int y;
		for (int x = 0; x <= 2; x++)
		{
			for (y = x ; y < 2; y++)
			{
				System.out.print("-");
			}
			System.out.print("o");
			for (y = 3 - x ; y < 3; y++)
			{
				System.out.print("--");
			}
			System.out.print("o");
			for (y = x ; y < 2; y++)
			{
				System.out.print("-");
			}
			System.out.println();
		}
		for (int x = 2; x >= 0; x--)
		{
			for (y = x ; y < 2; y++)
			{
				System.out.print("-");
			}
			System.out.print("o");
			for (y = 3 - x ; y < 3; y++)
			{
				System.out.print("--");
			}
			System.out.print("o");
			for (y = x ; y < 2; y++)
			{
				System.out.print("-");
			}
			System.out.println();
		}
	}
	public static void diamond()
	{
		for (int x = 1; x != 6; x++)
		{
		    for (int y = x; y <= 4; y++)
		    {
		        System.out.print("-");
		    }
		    for (int a = 1; a <= 2 * x - 1; a++)
		    {
		    	System.out.print(x);
			}
		    for (int y = x; y <= 4; y++)
		    {
		        System.out.print("-");
		    }
		    System.out.println();
		}
		for (int x = 4; x != 0; x--)
		{
		    for (int y = x; y <= 4; y++)
		    {
		        System.out.print("-");
		    }
		    for (int a = 1; a <= 2 * x - 1; a++)
		    {
		    	System.out.print(x);
			}
		    for (int y = x; y <= 4; y++)
		    {
		        System.out.print("-");
		    }
		    System.out.println();
		}
	}
}
