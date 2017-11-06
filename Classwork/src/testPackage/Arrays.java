package testPackage;
import java.util.*;

public class Arrays
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int len = Integer.parseInt(input.nextLine());
		String[] strs = new String[len]; 
		for (int x = 0; x != len; x++)
		{
			strs[x] = input.nextLine();
		}
		for (int x = 0; x != len; x++);
		{
			System.out.println(strs[x]);
		}
		for (int x = len; x != 0; x--);
		{
			System.out.println(strs[x - 1]);
		}
		for (int x3 = len; x != 0; x--);
		{
			if (x % 2 == 0)
			{
				System.out.println(strs[x - 1]);
			}
		}
	}
}
