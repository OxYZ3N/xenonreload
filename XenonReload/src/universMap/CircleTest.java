package universMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** @see http://stackoverflow.com/questions/2508704 */ 
public class CircleTest extends JPanel 
{ 
	private static final int SIZE = 256;
	private int a = SIZE / 2;
	private int b = a;
	private int r = 4 * SIZE / 5;
	private int n;

	/** @param n the desired number of circles. */

	public CircleTest(int n)
	{
		super(true);
		this.setPreferredSize(new Dimension(SIZE, SIZE));
		this.n = n;
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		a = getWidth() / 2;
		b = getHeight() / 2;
		//a = 30;
		//b = 30;
		int m = Math.min(a, b);
		r = 4 * m / 5;
		int r2 = Math.abs(m - r) / 2;
		//g2d.drawOval(a - r, b - r, 2 * r, 2 * r);
		g2d.setColor(Color.blue);
		
		for (int i = 0; i < n; i++)
		{ 
			double t = 2 * Math.PI * i / n;
			int x = (int) Math.round(a + r * Math.cos(t));
			int y = (int) Math.round(b + r * Math.sin(t));
			//g2d.fillOval(x - r2, y - r2, 2 * r2, 2 * r2);
			//g2d.drawOval(x - r2, y - r2, 2 * r2, 2 * r2);
			g2d.drawOval(x, y, 1,1);
		}
	}
	
	private static void plotplot()
	{
		double radius = 3;
		for (double x = -radius; x <= radius; x += 0.01) { double y = Math.sqrt(radius * radius - x * x); 
		plot(x, y);//top half of the circle plot(x, -y);//bottom half of the circle }
		}
		
	}
	

	private static void create()
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new CircleTest(180));
		f.pack(); f.setVisible(true);
	}
	private static void plot(double x, double y) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			//@Override
			public void run()
			{
				create();
			}
		}
				);
	}
}