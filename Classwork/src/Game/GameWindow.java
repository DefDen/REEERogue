package Game;

import java.awt.event.KeyListener;
import java.util.Stack;
import java.awt.Container;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GameWindow 
{
	public GameWindow()
	{
		JFrame window = new JFrame("REEERogue");
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
		window.setSize(300, 300);
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