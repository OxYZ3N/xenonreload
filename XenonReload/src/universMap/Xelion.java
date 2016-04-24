package universMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Xelion {
	private final int XelionSpeed = 1; //facteur de vitesse
	private Random rand = new Random(); //on crée un nombre aléatoire pour la création des astéroides
	private Vector2f vitesse ; //le vecteur vitesse
	private double angle; //angle de rotation à chaque update
	private float x,y; //position à l'écran
	private float vx,vy; // En ++, CSS
	private int speed, i = 0; //vitesse
	public Image Xelion; //image d'un Xelion visible à l'écran || Défini en privé
	public boolean isAlive = false;    
	private String message="Dans la classe Xelion";
	private Circle bound;  //Cercle pour la gestion des collisions des Xelion

	/**
	 * Test CSS
	 */
	private int a = 250;
	private int b = 250;
	private int n = 360;//Nombre de points
	private int m = Math.min(a, b);
	private int r = 4 * m / 5;
	private double t = 2 * Math.PI * 0 / n;
	/**
	 * 
	 * Fin de Test CSS
	 */

	public Circle getBound()
	{
		return bound;
	}

	public Xelion(Image Xelion)
	{
		isAlive = true;
		this.Xelion = Xelion;
		vitesse = new Vector2f();
		try {
			createXelion();  //on crée les valeurs aléatoire (position,vitesse) des Xelion                    
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Asteroids.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void createXelion() throws SlickException
	{	
		setPosition(x, y);
		//On crée vitesse de déplacement aléatoire
		vx = (float) rand.nextInt(5)-2;
		vy = (float) rand.nextInt(5)-2;
		while(vx == 0 || vy == 0 )	// on évite les valeurs 0 = astéroides immobiles !
		{
			vx = (float) rand.nextInt(5)-2;
			vy = (float) rand.nextInt(5)-2;
		}
		setVitesse((int)rand.nextInt(3)*XelionSpeed,vx,vy);
		/** 
		 * Crée la rotation initiale aléatoire
		 * 	Pour 2 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(2*5) donc XelionSpeed/10
		 * 	Pour 20 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(20*5) donc XelionSpeed/100
		 * 	etc ...
		 */
		angle = (float)rand.nextInt(90)*XelionSpeed/10;

		bound = new Circle(x,y,Xelion.getWidth()/2); //On instancie le cercle
	}

	public void setVitesse(int i, float vx2, float vy2) {
		if (i==0) {speed = i+1;} // Si la valeur est égale à 0, on ajoute 1 pour éviter que la météorite soit immobile !
		else {speed = i;}
		vx = vx2;
		vy = vy2;
	}

	public void setPosition(float x2, float y2) {
		x = x2;
		y = y2;
	}

	public void render (Graphics g)
	{
		g.drawString(message, 0, 570); // Dans la classe Xelion
		/**
		 * Pour les tests : On affiche le cercle autour de l'asteroide
		 */
		g.draw(bound);
		int xc,yc;
		Xelion.draw(x, y);
	}

	public void update ()
	{
		int xc,yc;
		t = 2 * Math.PI * i / n;
		x = ((int) Math.round(a + r * Math.cos(t)) + vitesse.x)+speed;
		y = ((int) Math.round(b + r * Math.sin(t)) + vitesse.y)+speed;
		Xelion.rotate((float)angle);
		Xelion.draw(x, y);
		// Une fois i=360 remettre sa valeur à zéro et repartir
		i = i + 1;
		if (i == 360) 
		{
			i = 0;
		}
		warp();

		/**
		 * On déplace et raffraichi le cercle dès que l'asteroide bouge
		 */
		bound.setLocation(x, y);
	}

	public void trajectoireCercle(int x,int y, Graphics2D g2d)
	{
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.blue);
		int a = 250;
		int b = 250;
		int n = 180;//Nombre de point
		int m = Math.min(a, b);
		int r = 4 * m / 5;
		for (int i = 0; i < n; i++)
		{
			double t = 2 * Math.PI * i / n;
			x = (int) Math.round(a + r * Math.cos(t));
			y = (int) Math.round(b + r * Math.sin(t));
			g2d.drawOval(x, y, 1,1);
		}
	}

	public void warp()
	{
		int w = Xelion.getWidth()-1;  
		int h = Xelion.getHeight()-1;  
		float newx = x;
		float newy = y;
		//controle si ne sort pas de l'écran...
		if(x < -w) {newx = 800 + w;}
		else if(x > 800+w){newx = -w;}
		if(y < -h) {newy = 600 + h;}
		else if(y > 600+h){newy = -h;}
		//enregistre les changements
		setPosition(newx,newy);

	}

	public boolean isAlive() {
		return true;
	}
}
