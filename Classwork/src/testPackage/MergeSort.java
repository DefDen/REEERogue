package testPackage;
import java.util.ArrayList;
public class MergeSort 
{
	public static void main(String[] args)
	{
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		ArrayList<Integer> rand = new ArrayList<Integer>();
		for(int x = 0; x < 10; x++)
		{
			list1.add(x * 5);
		}
		for(int x = 0; x < 20; x++)
		{
			list2.add(x * 4);
		}
		for(int x = 0; x < 30; x++)
		{
			rand.add((int)(Math.random() * 100));
		}
		System.out.println(list1);
		System.out.println(list2);
		System.out.println(merge(list1, list2));
		System.out.println();
		System.out.println(rand);
		System.out.println(sort(rand));
	}
	public static ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2)
	{
		ArrayList<Integer> combined = new ArrayList<Integer>();
		int list1Index = 0, list2Index = 0;
		for(int x = list1.size() + list2.size(); x > 0; x--)
		{
			if(list1Index >= list1.size())
			{
				for(int y = list2Index; y < list2.size(); y++)
				{
					combined.add(list2.get(y));
				}
				return combined;
			}
			if(list2Index >= list2.size())
			{
				for(int y = list1Index; y < list1.size(); y++)
				{
					combined.add(list1.get(y));
				}
				return combined;
			}
			if(list1.get(list1Index) < list2.get(list2Index))
			{
				combined.add(list1.get(list1Index));
				list1Index += 1;
			}
			else
			{
				combined.add(list2.get(list2Index));
				list2Index += 1;
			}
		}
		return combined;
	}
	public static ArrayList<Integer> sort(ArrayList<Integer> list)
	{
		if(list.size() <= 1)
		{
			return list;
		}
		int a = list.size() / 2;
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for (int x = 0; x < a; x++)
		{
			list2.add(list.remove(x));
		}
		return merge(sort(list), sort(list2));
	}
}
