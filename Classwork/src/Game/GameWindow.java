package Game;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameWindow 
{
	private JFrame window;
	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 300;
	private char[][] floor;
	
	public GameWindow()
	{
		makeWindow();
		JLabel label = new JLabel("Test");
		Container content = window.getContentPane();
		JTextField text = new JTextField();
		Stack<String> moveHistory = new Stack<String>();
		KeyListener listener = new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent event)
			{
				ifA(event.getKeyChar());
				moveHistory.add("" + event.getKeyChar());
				text.setText("");
			}
			@Override
			public void keyReleased(KeyEvent event) 
			{
			}
			@Override
			public void keyTyped(KeyEvent event)
			{
			}
		};
		content.add(text);
		content.add(label);
		text.addKeyListener(listener);
		window.pack();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	private void loadLevel(File floorFile) throws FileNotFoundException
	{
		Scanner scan = new Scanner(floorFile);
		for(int x = 0; scan.hasNextLine(); x++)
		{
			
		}
	}
	
	private void makeWindow()
	{
		window = new JFrame("REEERogue");
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setVisible(true);
	}
	
	public void ifA(char x)
	{
		if(x == 'a')
		System.out.println("It's a A");
	}

	public static void main(String args[])
	{
		GameWindow g = new GameWindow();
	}
}