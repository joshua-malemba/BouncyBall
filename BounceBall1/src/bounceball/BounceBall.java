package bounceball;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import bounceball.Angle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

//// https://ssaurel.medium.com/learn-to-make-a-mvc-application-with-swing-and-java-8-3cd24cf7cb10

public class BounceBall implements ActionListener, KeyListener, MouseListener
{

	public static BounceBall bounceBall;

	public final int WIDTH = 800, HEIGHT = 800;

	public int width = 500;
	public int height = 500;
	
	public Renderer renderer;

	public Rectangle ball;

	public ArrayList<Angle> pillars;

	public int yMotion;
	public int score;

	public boolean gameOver, started;
	public int num;


	public BounceBall()
	{
		
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		num = 0;
		renderer = new Renderer();
		jframe.add(renderer);
		jframe.setTitle("Flappy ball");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);
		score = 0;
		
		ball = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		pillars = new ArrayList<Angle>();

		
		addpillar(true);
		addpillar(true);
		addpillar(true);
		addpillar(true);
		
		
		timer.start();
	}

	public void addpillar(boolean start)
	{
		
		int rectwidth = 100;
		int rectheight = 100;
		
///		int space = 300;
///		int width = 100;
///		int height = 50 + rand.nextInt(300);

		if (start)
		{
			for(int i = 0; i < 1000; i++) {
			Angle pillar = new Angle(width + rectwidth + pillars.size() * 200, height - rectheight - 100, rectwidth, rectheight);
			for (Angle p : pillars) {
				if(p.getId() == num) {
					num += 1;
					pillar.setId(num);
				}
				
				else {
					pillar.setId(num);
				}
			}
			pillars.add(pillar);
			}
		}
		else
		{
			pillars.add(new Angle(pillars.get(pillars.size() - 1).x + 600, height - rectheight - 120, rectwidth, rectheight));
		}
		
		}
	

	public void paintpillar(Graphics g, Angle pillar)
	{
		g.setColor(Color.green.darker());
		g.fillRect(pillar.x, pillar.y, pillar.width, pillar.height);
	}

	public void jump()
	{
		if (gameOver)
		{
			ball = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			pillars.clear();
			yMotion = 0;
			score = 0;

			/// keep adding pillars but keep ball at level 0;
			
			addpillar(true);
			addpillar(true);
			addpillar(true);
			addpillar(true);

			gameOver = false;
		}

		if (!started)
		{
			started = true;
		}
 		else if (!gameOver)
		{
			if (yMotion > 0)
			{
				yMotion = 0;
			}

			yMotion -= 10;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int speed = 10;


		if (started)
		{
			for (int i = 0; i < pillars.size(); i++)
			{
				Angle pillar = pillars.get(i);

				pillar.x -= speed;
			}

			if (yMotion < 15)
			{
				yMotion += 2;
			}

			for (int i = 0; i < pillars.size(); i++)
			{
				Angle pillar = pillars.get(i);

				if (pillar.x + pillar.width < 0)
				{
					pillars.remove(pillar);

					if (pillar.y == 0)
					{
						addpillar(false);
					}
				}
			}

			ball.y += yMotion;
			
			for (Angle pillar : pillars)
			{
				
				if (pillar.intersects(ball))
				{
					score ++;
					
					
					if (ball.x <= pillar.x)
					{
						ball.x = pillar.x - ball.width;

					}
					else
					{
						if (pillar.y != 0)
						{
							ball.y = pillar.y - ball.height;
						}
						else if (ball.y < pillar.height)
						{
							ball.y = pillar.height;
						}
					}
				}
			}

			if (ball.y > HEIGHT - 120 || ball.y < 0)
			{
				gameOver = true;
			}

			if (ball.y + yMotion >= HEIGHT - 120)
			{
				ball.y = HEIGHT - 120 - ball.height;
				gameOver = true;
			}
		}
 
		renderer.repaint();
	}
	

	public void repaint(Graphics g)
	{
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);

		g.setColor(Color.red);
		g.fillRect(ball.x, ball.y, ball.width, ball.height);

		for (Angle pillar : pillars)
		{
			paintpillar(g, pillar);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if (!started)
		{
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}

		if (gameOver)
		{
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
		}

		if (!gameOver && started)
		{
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	public static void main(String[] args)
	{
	      bounceBall = new BounceBall();
	      
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		jump();		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump();
		}		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}