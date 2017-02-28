/*referred youtube tutorial : https://www.youtube.com/watch?v=_SqnzvJuKiA
 * changes implemented by AM 
 * 1. High score
 * 2. Corrected a flaw. When space pressed , reset only if game was over and then space was pressed. Used flags 
 * to correct this.
 * 3. Used random function to generate new positions of food in multiples of 25 within given range instead of storing
 * all possible values in an array.
 * 4. Implemented a game pause feature (Press enter to pause the game). 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private int high_score = 0;

	//first index of both the arrays store the position of the head of the snake
	private int[] snakeXLength = new int[750];
	private int[] snakeYLength = new int[750];

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private int foodX=800;
	private int foodY=600;
	private ImageIcon rightMouth;
	private ImageIcon leftMouth;
	private ImageIcon upMouth;
	private ImageIcon downMouth;
	private boolean flag=true;
	private ImageIcon snakeimage;
	private int length = 3;
	private int moves = 0;
	private Timer timer;
	private int delay = 100;
	private int score=0;
	private ImageIcon food;

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

		//titleImage = new ImageIcon("ress/snaketitle.jpg");
		//System.out.println(System.getProperty("java.class.path"));
		//System.out.println(this.getClass().getResource("snaketitle.jpg"));
		titleImage = new ImageIcon(this.getClass().getResource("snaketitle.jpg"));
		
		
		titleImage.paintIcon(this,g,25,11);
	
		
		//draw border for playing area and then set colour
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		//fill the rectangle with colour
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);			
		
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,14));
		g.drawString("Scores: "+score, 780, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,14));
		g.drawString("High Score: "+high_score, 600, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD,13));
		g.drawString("Press Enter anytime to pause the game.", 30, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN,14));
		g.drawString("Length: "+length, 780, 50);
		//rightMouth = new ImageIcon("ress/rightmouth.png");
		rightMouth = new ImageIcon(this.getClass().getResource("rightmouth.png"));
		rightMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
		
		food = new ImageIcon(this.getClass().getResource("enemy.png"));
		food.paintIcon(this, g, foodX, foodY);

		for (int i = 0; i < length; i++) { 
			if(i==0 && right)
			{
				//rightMouth = new ImageIcon("ress/rightmouth.png");
				rightMouth = new ImageIcon(this.getClass().getResource("rightmouth.png"));
				rightMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && left)
			{
				leftMouth = new ImageIcon(this.getClass().getResource("leftmouth.png"));
				leftMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && up)
			{
				upMouth = new ImageIcon(this.getClass().getResource("upmouth.png"));
				upMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i==0 && down)
			{
				downMouth = new ImageIcon(this.getClass().getResource("downmouth.png"));
				downMouth.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
			}

			if(i!=0)
			{
				snakeimage = new ImageIcon(this.getClass().getResource("snakeimage.png"));
				snakeimage.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

			} 
			

		}
		if(snakeYLength[0]==foodY && snakeXLength[0]==foodX)
		{
			score++;
		//25, 75, 850, 575
		int a = (25+(int)(Math.random()*(850-25)));
		foodX = a-(a%25);
		int b = (75+(int)(Math.random()*(625-75)));
		foodY = b-(b%25);
		length++;
		repaint();
		}
		
		for (int i = 1; i < length; i++) {
			if(snakeXLength[i]==snakeXLength[0] && snakeYLength[i]==snakeYLength[0])
			{
				right=false;left=false;up=false;down=false;
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD,50));
				g.drawString("GAME OVER!", 300, 300	);
				
				g.setFont(new Font("arial", Font.BOLD,40));
				g.drawString("Press space bar to restart.", 300, 360);
				
				flag=false;
								
			}
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//System.out.println(length);
		
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
//				if(snakeYLength[r]==foodY && snakeXLength[r]==foodX)
//				{
//				//25, 75, 850, 575
//				int a = (25+(int)(Math.random()*(850-25)));
//				foodX = a-(a%25);
//				int b = (75+(int)(Math.random()*(625-75)));
//				foodY = b-(b%25);
//				length++;
//				}
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
//				if(snakeYLength[r]==foodY && snakeXLength[r]==foodX)
//				{
//				//25, 75, 850, 575
//				int a = (25+(int)(Math.random()*(850-25)));
//				foodX = a-(a%25);
//				int b = (75+(int)(Math.random()*(625-75)));
//				foodY = b-(b%25);
//				length++;
//				}
//			}
			
			repaint();
		}
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
//				if(snakeYLength[r]==foodY && snakeXLength[r]==foodX)
//				{
//				//25, 75, 850, 575
//				int a = (25+(int)(Math.random()*(850-25)));
//				foodX = a-(a%25);
//				int b = (75+(int)(Math.random()*(625-75)));
//				foodY = b-(b%25);
//				length++;
//				}
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
//				if(snakeYLength[r]==foodY && snakeXLength[r]==foodX)
//					{
//					//25, 75, 850, 575
//					int a = (25+(int)(Math.random()*(850-25)));
//					foodX = a-(a%25);
//					int b = (75+(int)(Math.random()*(625-75)));
//					foodY = b-(b%25);
//					length++;
//					}
			}
			
			
			repaint();
		
		
		
	}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//System.out.println("key pressed");
		// TODO Auto-generated method stub
		
		
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER && flag==true){
			right=false;
			left=false;
			up=false;
			down=false;
			repaint();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE && flag==false){
			moves=0;
			length=3;
			if(score>high_score)
					high_score=score;
			score = 0;
			
			flag=true;
			repaint();
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && flag){
			moves++;
			if(left==true)
				right=false;
			else
				right=true;			
			up=false;
			down=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT && flag){
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
		
		if(e.getKeyCode()==KeyEvent.VK_UP && flag){
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
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN && flag){
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
