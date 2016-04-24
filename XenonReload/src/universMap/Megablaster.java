package universMap;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

public class Megablaster {
	/** Image du vaisseau */
	private Image joueur;
	/** Image des vies */
	private Image life;
	/** Image des Etoiles */
	private Image etoile;
	/** Emplacement du vaisseau */
	private String Megablaster = "res/Megablaster.png";
	/** Emplacement des points de vies */
	private String Life = "res/life.png";
	/** Emplacement des étoiles */
	private String Etoile = "res/Etoile.png";
	
	/** Instance du gestionnaire Input */
	private Input input;
	private int x,y; // coordonnées du vaisseau
	private String message="";
	private String messageCheatmode="Cheatmode";
	public boolean isInCheatmode = false;
public static int coordY;
public static int coordX;
	private int numberOflife = 6;

	/** Cercle autour du vaisseau */
	private Circle bound;

	/** Instance de sons */
	private Sound tir;
	private Sound acceleration;
	private Music ambiance;

	/** Etat pour la gestion des collisions sur le vaisseau */
	public static final int normal = 0;
	public static final int impact = 1;
	private int state = normal;
	private boolean collision = false;
	private long collisionTimer = 0;

	private ArrayList<Megablaster> spriteMegablaster;

	private boolean isAlive = false; 

	public Circle getBound()
	{
		return bound;
	}

	public Megablaster(Image joueur, Circle bound) {
		isAlive = true;
		this.joueur = joueur;
		this.bound = bound;
	}

	public Megablaster(Input input) {
		this.input = input;
	}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		x = 300;// on initialise la position du personnage
		y = 300;

		/** On charge les différents sprites (vaisseau, vies, etc... */
		joueur = new Image(Megablaster);
		life = new Image(Life);
		etoile = new Image(Etoile);
		
		input = container.getInput();
		//initialisation des sons et musiques
		tir = new Sound("res/shoot.wav");
		acceleration = new Sound("res/thrust.wav");
		ambiance = new Music("res/military.xm");
		ambiance.loop(); //on joue en boucle l'ambiance

		bound = new Circle(x,y,joueur.getWidth()/2); //On instancie le cercle sur le vaisseau MegaBlaster
		spriteMegablaster = new ArrayList();
		Megablaster vais = new Megablaster(joueur, bound);
		spriteMegablaster.add(vais);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		joueur.draw(x,y);	// on dessine le personnage e† la position actuelle
		g.drawString(message, 590, 584);// on écrit le message de controle des input (714, 584)
		g.draw(bound);
		etoile.draw(450,11);	// on dessine nos étoiles
		for (int i=0;i< numberOflife;i++)
		{
			this.life.draw(700+12*i,11);
		}		
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Megablaster megablaster = spriteMegablaster.get(0);
		if((collision==true) && (isInCheatmode == false))
		{
			if (state==normal)
			{
				collisionTimer=System.currentTimeMillis();
				state=impact;
				numberOflife -= 1;
				if (numberOflife<0)
				{
					numberOflife=-1;
				}
			}
			else if (state==impact)
			{
				if(collisionTimer+3000<System.currentTimeMillis())  //on attend 3 secondes (0.5s=500) avant de pouvoir encore lui enlever de la vie!
				{
					state=normal;
					collision=false;
				}
			}
		} // fin gestion collision

		/** gestion des input **/
		// on demande de déplacer le personnage
		if ( input.isKeyDown(Input.KEY_UP))
		{
			/** On limite les déplacements sur l'axe y */
			if (y==0) y=0;else y -= 10;
			
			if (y==30)
			{
				coordY = y+9-30;
			}
			if (y==150)
			{
				coordY = y+6-150;
			}
			if (y==300)
			{
				coordY = y+6-300;
			}
//coordY = y+9-300;
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			/** On limite les déplacements sur l'axe y */
			if (y==560) y=560;else y += 10;

			if (y==300)
			{
				coordY = y-6-300;
			}
			if (y==450)
			{
				coordY = y-6-450;
			}
			if (y==530)
			{
				coordY = y-9-530;
			}
//coordY = y-9-300;
		}
		else if (input.isKeyDown(Input.KEY_LEFT))
		{
			/** On limite les déplacements sur l'axe x */
			if (x==0) x=0;else x -= 10;
			
			if (x==0)
			{
				coordX = x+9-30;
			}
			if (x==150)
			{
				coordX = x-9-150;
			}
			if (x==295)
			{
				coordX = x-9-295;
			}
			if (x==450)
			{
				coordX = x-9-450;
			}
//coordX = x+9-300;
		}
		else if (input.isKeyDown(Input.KEY_RIGHT))
		{
			/** On limite les déplacements sur l'axe x */
			if (x==760) x=760;else x += 10;
			
			if (x==150)
			{
				coordX = x-9-150;
			}		
			if (x==305)
			{
				coordX = x-9-305;
			}
			if (x==450)
			{
				coordX = x-9-450;
			}
			if (x==730)
			{
				coordX = x+9-740;
			}
//coordX = x+9-300;
		}
		/************** Nouvelles entrées "surveillées" !! **********************/
		// on appuie sur Escape
		if ( input.isKeyPressed(Input.KEY_ESCAPE))
		{
			game.enterState(MenuJoueur.ID); //on retoune au menu du jeu
			//System.exit(0); //on sort proprement du jeu
		}
		// on appuie sur Espace
		if ( input.isKeyPressed(Input.KEY_LALT))
		{
			message = "Accélération du vaisseau";
			acceleration.play();
		}
		if ( input.isKeyPressed(Input.KEY_RALT) && input.isKeyPressed(Input.KEY_RSHIFT) && input.isKeyPressed(Input.KEY_C) )
		{
			message = messageCheatmode;
			isInCheatmode = true;
		}
		// on clique avec la souris
		else if(input.isKeyPressed(Input.KEY_SPACE))
		{
			message ="Tir de plasma";
			tir.play();
		}
		else { if(isInCheatmode) {message=messageCheatmode;} else { message = "Normal" + "    x(" + x + ")" + ":y(" + y + ")"; }}
		bound.setLocation(x, y);
	}
	public void setCollision(boolean value)
	{
		collision=value;
	}
	public boolean isAlive() {
		return true;
	}
	public int getLife() {
		return numberOflife;
	}
}
