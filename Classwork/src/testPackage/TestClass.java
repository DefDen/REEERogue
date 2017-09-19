package testPackage;

public class TestClass 
{
	public static void main (String[] args)
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
