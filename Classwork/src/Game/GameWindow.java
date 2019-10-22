package Game;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	private static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
	private char[][] floor = new char[21][79];
	private GameManager GM;
	private JLabel floorLabel;
	private JLabel messageLabel = new JLabel();
	private ArrayList<String> messages = new ArrayList<String>();

	public GameWindow(GameManager GM)
	{
		this.GM = GM;
		makeWindow();
		loadLevel("a");
		JTextField text = makeJTextField();
		JPanel panel = new JPanel(new BorderLayout());
		for(int x = 0; x < 2; x++)
		{
			messages.add("a");
		}
		updateMessage("a");
		window.add(panel);
		panel.add(messageLabel, BorderLayout.PAGE_START);
		panel.add(floorLabel, BorderLayout.CENTER);
		panel.add(text, BorderLayout.PAGE_END);
		window.pack();
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void loadLevel(String fileName)
	{
		try
		{	
			loadLevel(new File("a"));
			floorLabel = new JLabel(floorToString(), SwingConstants.CENTER);
		}
		catch(FileNotFoundException e)
		{
			System.out.print("Error: Cannot load floor");
		}
	}

	private void loadLevel(File floorFile) throws FileNotFoundException
	{
		Scanner scan = new Scanner(floorFile);
		for(int x = 0; scan.hasNextLine(); x++)
		{
			floor[x] = scan.nextLine().toCharArray();
		}
		GM.updateFloor(floor);
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
				updateMessage(GM.playerMove(event.getKeyChar()));
				text.setText("");
				floor = GM.getUpdatedFloor();
				floorLabel.setText(floorToString());
			}
			@Override
			public void keyReleased(KeyEvent event){}
			@Override
			public void keyTyped(KeyEvent event){}
		};
		text.addKeyListener(listener);
		return text;
	}

	private void updateMessage(String message)
	{
		messages.add(message);
		String curMessage = "<html><font face=\"monospace\"\n<br>";
		for(int x = 3; x > 0; x--)
		{
			if(messages.get(messages.size() - x).equals("a"))
			{
				curMessage += "<p style=\"color:#ffffff\">";
			}
			else
			{
				switch(x)
				{
				case 1:
					curMessage += "<p style=\"color:#000000\">";
					break;

				case 2:
					curMessage += "<p style=\"color:#808080\">";
					break;

				case 3:
					curMessage += "<p style=\"color:#b3b3b3\">";
					break;
				}
			}
			curMessage += messages.get(messages.size() - x) + "\n<br>";
		}
		curMessage += "<html>";
		messageLabel.setText(curMessage);
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
		String strFloor = "<html><font face=\"monospace\"";
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				strFloor += floor[x][y];
			}
			strFloor += "\n<br>";
		}
		strFloor += "<html>";
		return strFloor;
	}

	public void printSample()
	{
		for(int x = 0; x < floor.length; x++)
		{
			for(int y = 0; y < floor[x].length; y++)
			{
				System.out.print(".");
			}
			System.out.println();
		}
	}

	public static void main(String args[]) throws FileNotFoundException
	{
		GameManager GM = new GameManager();
	}
}