package universMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Xenites {
	private final int XenitesSpeed = 1; //facteur de vitesse
	private Random rand = new Random(); //on crée un nombre aléatoire pour la création des astéroides
	private Vector2f vitesse ; //le vecteur vitesse
	private double angle; //angle de rotation à chaque update
	private float x,y; //position à l'écran
	private float vx,vy; // En ++, CSS
	private int speed; //vitesse
	public Image xenites; //image d'un Xenites visible à l'écran || Défini en privé
	public boolean isAlive = false;
	public boolean isAliveXenite = true;
	private String message="Dans la classe Xenites";
	private Circle bound;  //Cercle pour la gestion des collisions des Xenites
	
	/* Animation de l'explosion */	
	private SpriteSheet explosionSheet;
	private Animation explosion;
	private boolean play = false;
	/**
	 * Test CSS
	 */
	private int a = 250;
	private int b = 250;
	private int n = 180;//Nombre de point
	private int m = Math.min(a, b);
	private int r = 4 * m / 5;
	/**
	 * 
	 * Fin de Test CSS
	 */

	public Circle getBound()
	{
		return bound;
	}
	
	public void destroy()
	{
		play = true;
	}
	
	public Xenites(Image xenites)
	{
		isAlive = true;
		isAliveXenite = true;
		this.xenites = xenites;
		vitesse = new Vector2f();
		try {
			createXenites();  //on crée les valeurs aléatoire (position,vitesse) des Xenites
			createAnimation();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Asteroids.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void createAnimation() throws SlickException
	{
		explosionSheet = new SpriteSheet("res/explosion2.png", 40,40);
		//Constructeur : Animation(SpriteSheet frames, int x1, int y1, int x2, int y2, boolean horizontalScan, int duration, boolean autoUpdate)
		explosion = new Animation( explosionSheet, 0,0,1,1,false, 50, true);
		for (int i=0;i < 2;i++)
		{
			for (int j=0;j < 4;j++)
			{explosion.addFrame( explosionSheet.getSprite(j,i),100);}
		}
		explosion.stopAt(8);
	}
	
	private void createXenites() throws SlickException
	{	
		//crée une position initiale aléatoire
		float x = rand.nextInt(800-128);
		float y = rand.nextInt(600-128);
		setPosition(x, y);
		//On crée vitesse de déplacement aléatoire
		//entre -2 et 2 : entier = rnd.nextInt(5)-2;
		vx = (float) rand.nextInt(5)-2;
		vy = (float) rand.nextInt(5)-2;
		while(vx == 0 || vy == 0 )/*on évite les valeurs 0 = astéroides immobiles !*/
		{
			vx = (float) rand.nextInt(5)-2;
			vy = (float) rand.nextInt(5)-2;
		}
		setVitesse((int)rand.nextInt(3)*XenitesSpeed,vx,vy);
		/** 
		 * Crée la rotation initiale aléatoire
		 * 	Pour 2 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(2*5) donc XenitesSpeed/10
		 * 	Pour 20 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(20*5) donc XenitesSpeed/100
		 * 	etc ...
		 */
		angle = (float)rand.nextInt(90)*XenitesSpeed/100;

		bound = new Circle(x,y,xenites.getWidth()/2); //On instancie le cercle
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
		if (play)
		{
			if(explosion.getFrame()==0){play=true;} //si elle n'est pas en cours(farme = 0, donc au début) on la démarre

			if(play==true){explosion.draw(x,y);}

			if(explosion.getFrame()==8)// si on arrive à la fin
			{
				explosion.restart();
				play=false;
				//this.setAlive(false);// on tue l'astéroide
				isAliveXenite = false;
			}
		}
		else
		{
			xenites.draw(x,y);
			/**
			 * Pour les tests : On affiche le cercle autour de l'asteroide
			 */
			g.draw(bound);
		}
		g.drawString(message, 0, 584);
	}

	public boolean setAlive(boolean isAliveAsteroide) {
		return isAliveAsteroide;
	}
	
	public void update ()
	{
		x = (x + vitesse.x)+speed;
		y = (y + vitesse.y)+speed;
		xenites.rotate((float)angle);
		xenites.draw();
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
		int w = xenites.getWidth()-1;  
		int h = xenites.getHeight()-1;  
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
