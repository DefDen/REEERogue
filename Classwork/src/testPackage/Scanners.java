package testPackage;
import java.util.Scanner;
public class Scanners 
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("What is your name? ");
		String strName = input.nextLine();
		System.out.print("What is your age? ");
		String strAge = input.nextLine();
		System.out.print("Hello " + strName + ". You are " + strAge + " years old.");
	}
}
