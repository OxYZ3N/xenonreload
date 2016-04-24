package universMap;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.SlickException;
import java.util.Random;
import org.newdawn.slick.geom.Circle;

public class Asteroids {
	private final int AST_SPEED = 1;//facteur de vitesse
	//on crée un nombre aléatoire pour la création des astéroides
	private Random rand = new Random();
	private Vector2f vitesse ; //le vecteur vitesse
	private double angle; //angle de rotation à chaque update
	private float x,y;//position à l'écran
	private float vx,vy; // En ++, CSS
	private int speed; //vitesse de cet Astéroid
	public Image img;//image de l'asteroide visible à l'écran || Défini en privé
	private boolean isAlive = false;
	public boolean isAliveAsteroide = true;
	private String message="Dans la classe Asteroids";
	private Circle bound; //Cercle pour la gestion des collisions de l'asteroide

	/* Animation de l'explosion */	
	private SpriteSheet explosionSheet;
	private Animation explosion;
	private boolean play = false;

	/** On instancie le cercle qui permettra de gérer les collisions sur l'asteroide */
	public Circle getBound()
	{
		return bound;
	}

	public void destroy()
	{
		play = true;
	}

	public Asteroids(Image img)
	{
		isAlive = true;
		isAliveAsteroide = true;
		this.img = img;
		vitesse = new Vector2f();
		try {
			createAsteroid();  //on crée les valeurs aléatoire (position,vitesse) de l'astéroide
			createAnimation(); //on appel a partir du constructeur la méthode createAnimation()
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
		explosion.stopAt(7);
	}

	public void createAsteroid() throws SlickException
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
		setVitesse((int)rand.nextInt(3)*AST_SPEED,vx,vy);
		/** 
		 * Crée la rotation initiale aléatoire
		 * 	Pour 2 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(2*5) donc AST_SPEED/10
		 * 	Pour 20 asteroides, un facteur 5 doit être appliqué à la formule ...AST_SPEED/(20*5) donc AST_SPEED/100
		 * 	etc ...
		 */
		angle = (float)rand.nextInt(90)*AST_SPEED/100;

		bound = new Circle(x,y,img.getWidth()/2); //On instancie le cercle
	}

	public void setVitesse(int i, float vx2, float vy2) {
		//System.out.println("Valeur de Speed : " + i);
		//System.out.println("Valeur de vx : " + vx);
		//System.out.println("Valeur de vy : " + vy);
		if (i==0) {speed = i+1;} // Si la valeur est égale à 0, on ajoute 1 pour éviter que la météorite soit immobile !
		else {speed = i;}
		vx = vx2;
		vy = vy2;
	}

	public void setPosition(float x2, float y2) {
		x = x2;
		y = y2;

	}

	public void render (Graphics g) throws SlickException
	{
		if (play)
		{
			if(explosion.getFrame()==0){play=true;} //si elle n'est pas en cours(farme = 0, donc au début) on la démarre

			if(play==true){explosion.draw(x,y);}

			if(explosion.getFrame()==7)// si on arrive à la fin
			{
				explosion.restart();
				play=false;
				//this.setAlive(false);// on tue l'astéroide
				isAliveAsteroide = false;
			}
		}
		else
		{
			img.draw(x,y);
			/**
			 * Pour les tests : On affiche le cercle autour de l'asteroide
			 */
			g.draw(bound);
		}
		g.drawString(message, 0, 556);
	}

	public boolean setAlive(boolean isAliveAsteroide) {
		return isAliveAsteroide;
	}

	public void update ()
	{
		x = (x + vitesse.x)+speed; //on peut pondérer en multipliant par speed...
		y = (y + vitesse.y)+speed;
		img.rotate((float)angle);
		warp();

		/**
		 * On déplace et raffraichi le cercle dès que l'asteroide bouge
		 */
		bound.setLocation(x, y);
	}

	public void warp()
	{
		int w = img.getWidth()-1;
		int h = img.getHeight()-1;
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

