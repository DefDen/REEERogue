package testPackage;

public class isPalindrome 
{
	public static void main (String[] args)
	{
		System.out.println(isPalindrom("racecar"));
	}
	public static boolean isPalindrom (String a)
	{
		if(a.length() > 1 && a.charAt(0) == a.charAt(a.length() - 1))
		{
			return isPalindrom(a.substring(1, a.length() - 1));
		}
		if(a.length() <= 1)
		{
			return true;
		}
		return false;
	}
}
