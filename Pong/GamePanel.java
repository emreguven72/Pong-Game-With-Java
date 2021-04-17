package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable
{
	static final int Game_Width = 1000;
	static final int Game_Height = (int)(Game_Width * (0.5555));
	static final Dimension Screen_Size = new Dimension(Game_Width, Game_Height);
	static final int Ball_Diameter = 20;
	static final int Paddle_Width = 25;
	static final int Paddle_Height = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	GamePanel()
	{
		newPaddles();
		newBall();
		score = new Score(Game_Width, Game_Height);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(Screen_Size);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall()
	{
		random = new Random();
		ball = new Ball((Game_Width / 2) - (Ball_Diameter / 2), (Game_Height / 2) - (Ball_Diameter / 2), Ball_Diameter, Ball_Diameter);
	}
	public void newPaddles()
	{
		paddle1 = new Paddle(0, (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 1);
		paddle2 = new Paddle((Game_Width - Paddle_Width), (Game_Height / 2) - (Paddle_Height / 2), Paddle_Width, Paddle_Height, 2);
	}
	public void paint(Graphics g)
	{
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	public void draw(Graphics g)
	{
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	public void move()
	{
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision()
	{
		//stops paddles at window edges
		if(paddle1.y <= 0)
		{
			paddle1.y = 0;
		}
		if(paddle1.y >= (Game_Height - Paddle_Height))
		{
			paddle1.y = Game_Height - Paddle_Height;
		}
		
		if(paddle2.y <= 0)
		{
			paddle2.y = 0;
		}
		if(paddle2.y >= (Game_Height - Paddle_Height))
		{
			paddle2.y = Game_Height - Paddle_Height;
		}
		
		//bounce ball of the window edges
		if(ball.y <= 0)
		{
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= Game_Height - Ball_Diameter)
		{
			ball.setYDirection(-ball.yVelocity);
		}
		
		//bounce ball when collides paddles
		if(ball.intersects(paddle1))
		{
			ball.setXDirection(-(ball.xVelocity - 2));
		}
		if(ball.intersects(paddle2))
		{
			ball.setXDirection(-(ball.xVelocity + 2));
		}
		
		//give a player 1 point and create new paddles and ball
		if(ball.x <= 0)
		{
			score.player2++;
			newPaddles();
			newBall();
		}
		if(ball.x >= Game_Width)
		{
			score.player1++;
			newPaddles();
			newBall();
		}
	}
	public void run()
	{
		//basic game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1)
			{
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	
	public class AL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e)
		{
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}
