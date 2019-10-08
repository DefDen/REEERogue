package Game;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameWindow 
{
	private JFrame window;
	private static final int WINDOW_WIDTH = 300, WINDOW_HEIGHT = 300;
	private char[][] floor = new char[21][79];
	private Stack<String> moveHistory = new Stack<String>();

	public GameWindow()
	{
		makeWindow();
		try
		{	
			loadLevel(new File("C:\\Users\\s-jouv\\Desktop\\a.txt\\"));
		}
		catch(FileNotFoundException e)
		{
			System.out.print("Error: Cannot load floor");
		}
		JLabel floorLabel = new JLabel(floorToString(), SwingConstants.CENTER);
		JTextField text = makeJTextField();
		JPanel panel = new JPanel(new BorderLayout());
		window.add(panel);
		panel.add(floorLabel, BorderLayout.PAGE_START);
		panel.add(text, BorderLayout.PAGE_END);
		window.pack();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
	}

	private void loadLevel(File floorFile) throws FileNotFoundException
	{
		Scanner scan = new Scanner(floorFile);
		for(int x = 0; scan.hasNextLine(); x++)
		{
			floor[x] = scan.nextLine().toCharArray();
		}
	}
	
	private JTextField makeJTextField()
	{
		JTextField text = new JTextField("Is it a A?");
		text.setBounds(100, 0, 25, 10);
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
		text.addKeyListener(listener);
		return text;
	}

	private void makeWindow()
	{
		window = new JFrame("REEERogue");
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setVisible(true);
	}

	private void ifA(char x)
	{
		if(x == 'a')
			System.out.println("It's a A");
	}
	
	public String floorToString()
	{
		String strFloor = "<html>";
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				strFloor += floor[x][y];
			}
			strFloor += "\n<br/>";
		}
		strFloor += "<html>";
		return strFloor;
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
		GameWindow g = new GameWindow();
		System.out.print(g.floorToString());
	}
}