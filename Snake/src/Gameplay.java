import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{


	//first index of both the arrays store the position of the head of the snake
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;

	private ImageIcon snakeimage;
	private int length = 3;
	private int moves = 0;
	private Timer timer;
	private int delay = 100;


	private ImageIcon titleImage;
	public Gameplay(){
		//focusable can be set to true and listeners can be added in the main class
		//as well. when Gameplay's object is created, do obj.setfocussable and .addKeyListener
		addKeyListener(this);

		setFocusable(true);

		setFocusTraversalKeysEnabled(false);

		timer = new Timer(delay,this);
		timer.start();

	}

	public void paint(Graphics g)
	{
		//System.out.println("paint called");
		if(moves==0)
		{
			snakeXLength[2] = 50;
			snakeXLength[1] = 75;
			snakeXLength[0] = 100;

			snakeYLength[2] = 100;
			snakeYLength[1] = 100;
			snakeYLength[0] = 100;

		}
		//first draw border for title image

		g.setColor(Color.white);
		g.drawRect(24,10,851,55);

		//draw image for title

		titleImage = new ImageIcon("snaketitle.jpg");

		titleImage.paintIcon(this,g,25,11);

		//draw border for playing area and then set colour
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		//fill the rectangle with colour
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);			

		rightMouth = new ImageIcon("rightmouth.png");
		rightMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);


		for (int i = 0; i < length; i++) {
			if(i==0 && right)
			{
				rightMouth = new ImageIcon("rightmouth.png");
				rightMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && left)
			{
				leftMouth = new ImageIcon("leftmouth.png");
				leftMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && up)
			{
				upMouth = new ImageIcon("upmouth.png");
				upMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && down)
			{
				downMouth = new ImageIcon("downmouth.png");
				downMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i!=0)
			{
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

			} 

		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println("action performed"+e.getSource());
		
		//timer fires every 100ms, that means action performed is called every 100ms
		//action performed updates the array values i.e the positions of the snake parts
		//and calls paint again (repaint()), which draws. this is done every 100ms as every 
		//actionPerformed's timer fires one call to actionPerformed every 100ms.
		//whenever key is pressed, the keyPressed method updates the direction flags
		//and actionPerformed takes care of the rest according to the updates direction flags.
		//timer.start();
		
		if(right)
		{
			for(int r = length-1; r>=0;r--)
			{
				snakeYLength[r+1] = snakeYLength[r];
			}
			
			for(int r = length; r>=0;r--)
			{
				if(r==0)
					snakeXLength[r] = snakeXLength[r]+25;
				else
					snakeXLength[r] = snakeXLength[r-1];
				if(snakeXLength[r]>850)
					snakeXLength[r] = 25;
			}
			
			repaint();
		}
		
		if(left)
		{
			for(int r = length-1; r>=0;r--)
			{
				snakeYLength[r+1] = snakeYLength[r];
			}
			
			for(int r = length; r>=0;r--)
			{
				if(r==0)
					snakeXLength[r] = snakeXLength[r]-25;
				else
					snakeXLength[r] = snakeXLength[r-1];
				if(snakeXLength[r]<25)
					snakeXLength[r] = 850;
			}
			
			repaint();
		}
		
		if(up)
		{
			for(int r = length-1; r>=0;r--)
			{
				snakeXLength[r+1] = snakeXLength[r];
			}
			
			for(int r = length; r>=0;r--)
			{
				if(r==0)
					snakeYLength[r] = snakeYLength[r]-25;
				else
					snakeYLength[r] = snakeYLength[r-1];
				if(snakeYLength[r]<75)
					snakeYLength[r] = 625;
			}
			
			repaint();
		
		}
		
		if(down)
		{
			for(int r = length-1; r>=0;r--)
			{
				snakeXLength[r+1] = snakeXLength[r];
			}
			
			for(int r = length; r>=0;r--)
			{
				if(r==0)
					snakeYLength[r] = snakeYLength[r]+25;
				else
					snakeYLength[r] = snakeYLength[r-1];
				if(snakeYLength[r]>625)
					snakeYLength[r] = 75;
			}
			
			repaint();
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		System.out.println("key pressed");
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			moves++;
			if(left==true)
				right=false;
			else
				right=true;			
			up=false;
			down=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			moves++;

			left=true;
			if(right==false)
				left=true;
			else
			{
				left=false;
				right=true;
			}
			up=false;
			down=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP){
			moves++;

			up=true;
			if(down==false)
				up=true;
			else
			{
				up=false;
				down=true;
			}
			right=false;
			left=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			moves++;

			down=true;
			if(up==false)
				down=true;
			else
			{
				down=false;
				up=true;
			}
			right=false;
			left=false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
