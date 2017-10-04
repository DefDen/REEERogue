package testPackage;
import java.util.Scanner;
public class Scanners 
{
	public static void main(String[] args)
	{
		Scanner name = new Scanner(System.in);
		Scanner age = new Scanner(System.in);
		System.out.print("What is your name? ");
		String strName = name.nextLine();
		System.out.print("What is your age? ");
		String strAge = age.nextLine();
		System.out.print("Hello " + strName + ". You are " + strAge + " years old.");
	}
}
