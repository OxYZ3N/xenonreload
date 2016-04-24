package universMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author http://www.developpez.net/forums/d900256/java/interfaces-graphiques-java/debuter/courbe-bezier-java/
 */
public class Bezier extends JComponent {

	List<Point> aaa = new LinkedList<Point>();  // Vector contenant des points
	Ellipse2D circle = new Ellipse2D.Double();
	private Paint circleBackground = Color.RED;
	Line2D line = new Line2D.Double();
	private Paint lineForeground = Color.GRAY;
	private Stroke lineStroke = new BasicStroke(0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0);
	CubicCurve2D curve = new CubicCurve2D.Double();
	private Paint curveForeground = Color.BLACK;
	private Stroke curveStroke = new BasicStroke(1f);

	public Bezier() {
		aaa.add(new Point(100, 150));
		aaa.add(new Point(150, 50));
		aaa.add(new Point(250, 125));
		aaa.add(new Point(400, 0));
		aaa.add(new Point(550, 150));
		aaa.add(new Point(700, 100));
		aaa.add(new Point(800, 50));
		aaa.add(new Point(850, 50));
	}

	/**
	 * {@inheritDoc}
	 */
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Dimension size = getSize();
		 Insets insets = getInsets();
		 Graphics2D g2d = (Graphics2D) g.create(insets.left, insets.top, size.width - (insets.left + insets.right), size.height - (insets.top + insets.bottom));
		 try {
			 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			 g2d.setPaint(lineForeground);
			 g2d.setStroke(lineStroke);
			 // afficher une droite brisée
			 for (int j = 0; j < aaa.size() - 1; j++) {
				 Point AA = (Point) aaa.get(j);
				 Point BB = (Point) aaa.get(j + 1);
				 line.setLine(AA, BB);
				 g2d.draw(line);
			 }
			 g2d.setPaint(curveForeground);
			 g2d.setStroke(curveStroke);
			 // afficher la courbe de bezier spline
			 for (int i = 0; i < aaa.size() - 3; i += 3) {
				 Point AA = aaa.get(i);
				 Point BB = aaa.get(i + 1);
				 Point CC = aaa.get(i + 2);
				 Point DD = aaa.get(i + 3);
				 curve.setCurve(AA.x, AA.y, BB.x, BB.y, CC.x, CC.y, DD.x, DD.y);
				 g2d.draw(curve);
			 }
			 // afficher un cercle rouge
			 g2d.setPaint(circleBackground);
			 for (int j = 0; j < aaa.size(); j++) {
				 Point AA = (Point) aaa.get(j);
				 circle.setFrameFromCenter(AA.x, AA.y, AA.x + 2.5, AA.y + 2.5);
				 g2d.fill(circle);
			 }
		 } finally {
			 g2d.dispose();
		 }
		 int[] x={10,60,100,80,150,10};
		 int[] y={15,15,25,35,45,15};
		 
		 int xCircle = 150;
		 int yCircle = 250;
		 int largeur = 100;
		 int hauteur = 100;
		 g.drawOval(xCircle, yCircle, largeur, hauteur);
		 
		 Polygon p = new Polygon(x, y,x.length );
		 g.drawPolygon(p);
		 g.drawString("(" + x + "," + y + ")", xCircle, yCircle );
	 }

	 /**
	  * @param args the command line arguments
	  */
	 public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {

			 /**
			  * {@inheritDoc}
			  */
			 public void run() {
				 // TODO code application logic here
				 JFrame f = new JFrame("Amplitude d'une courbe de Bezier");
				 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 f.setSize(1000, 400);
				 f.setLayout(new BorderLayout());
				 f.add(new Bezier(), BorderLayout.CENTER);
				 f.setLocationRelativeTo(null);
				 f.setVisible(true);
			 }
		 });
	 }
}
