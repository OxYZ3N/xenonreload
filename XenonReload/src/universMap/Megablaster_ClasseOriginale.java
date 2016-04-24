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

public class Megablaster_ClasseOriginale {
	private Image joueur;// le vaisseau
	private String Megablaster = "res/ufo.png";// l'adresse de la 2nde image
	
	/** Instance du gestionnaire Input */
	private Input input;
	private int x,y; // coordonnées du vaisseau
	private String message="";
	private String messageCheatmode="Cheatmode";
	private boolean isInCheatmode = false;
	
	/** Cercle autour du vaisseau */
	private Circle bound;
	
	/** Instance de sons */
	private Sound tir;
	private Sound acceleration;
	private Music ambiance;
	
	private ArrayList<GameState> spriteMegablaster;
	
	private boolean isAlive = false; 
	
	public Megablaster_ClasseOriginale(Image joueur, Circle bound) {
		isAlive = true;
		this.joueur = joueur;
		//this.imgXenites = imgXenites;
		this.bound = bound;
		//this.boundXenites = boundXenites;
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		joueur = new Image(Megablaster); // on charge l'image
		
		x = 300;// on initialise la position du personnage
		y = 300;
		
		input = container.getInput();
		//initialisation des sons et musiques
		tir = new Sound("res/shoot.wav");
		acceleration = new Sound("res/thrust.wav");
		ambiance = new Music("res/military.xm");
		ambiance.loop(); //on joue en boucle l'ambiance
		
		bound = new Circle(x,y,joueur.getWidth()/2); //On instancie le cercle sur le vaisseau MegaBlaster
		//boundXenites = new Circle(x,y,imgXenites.getWidth()/2); //On instancie le cercle sur les Xenites
		spriteMegablaster = new ArrayList();
		GameState vais = new GameState(joueur, bound);
		spriteMegablaster.add(vais);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		joueur.draw(x,y);// on dessine le personnage e† la position actuelle
		g.draw(bound);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		GameState megablaster = spriteMegablaster.get(0);
		
		/** gestion des input **/
		// on demande de déplacer le personnage
		if ( input.isKeyDown(Input.KEY_UP))
		{
			y -= 10; 
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			y += 10;
		}
		else if (input.isKeyDown(Input.KEY_LEFT))
		{
			x -= 10;
		}
		else if (input.isKeyDown(Input.KEY_RIGHT))
		{
			x += 10;
		}
		/************** Nouvelles entrées "surveillées" !! **********************/
		// on appuie sur Escape
		if ( input.isKeyPressed(Input.KEY_ESCAPE))
		{
			System.exit(0); //on sort proprement du jeu
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
			//acceleration.play();;
		}
		// on clique avec la souris
		else if(input.isKeyPressed(Input.KEY_SPACE))
		{
			message ="Tir de plasma";
			tir.play();
		}
		else { if(isInCheatmode) {message=messageCheatmode;} else { message = "Normal"; }}
		bound.setLocation(x, y);
	}
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return true;
	}
}
