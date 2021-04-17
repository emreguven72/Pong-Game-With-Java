package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle
{
	static int Game_Width;
	static int Game_Height;
	int player1 = 0;
	int player2 = 0;
	
	Score(int Game_Width, int Game_Height)
	{
		Score.Game_Width = Game_Width;
		Score.Game_Height = Game_Height;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 20));
		
		g.drawLine((Game_Width / 2), 0, (Game_Width / 2), Game_Height);
		g.setColor(Color.blue);
		g.drawString("Player1: " + player1, 250, 30);
		g.setColor(Color.red);
		g.drawString("Player2: " + player2,  750, 30);
	}
}
