package universMap;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;

/**
 ** C'est l'état pricipal du jeu, c'est ici que l'on codera l'action du jeu !
 */
public class GameState extends BasicGameState{

	public static final int ID = 1; // l'identifiant de l'état

	private TableauEtoileScrolling TableauEtoileScrolling;

	private Megablaster megablaster;

	/** Instance du gestionnaire Input */
	private Input input;

	/** Instance des asteroides */
	private Image imgAsteroides;

	/** Instance des Xenites */
	private Image imgXenites;

	/** Instance des Xelion */
	private Image imgXelion;

	/** 
	 * La liste qui contiendra tous les objets Asteroid ainsi que leur nombre
	 */
	public ArrayList<Asteroids> sprites;
	/**
	 * La liste qui contiendra tous les objets Xenites ainsi que leur nombre
	 */
	private ArrayList<Xenites> spriteXenites;
	/**
	 * La liste qui contiendra tous les objets Xelions ainsi que leur nombre
	 */
	private ArrayList<Xelion> spriteXelion;
	private final int ASTEROIDS = 20; // Le nombre d'Asteroides
	private final int XENITES = 6; // Le nombre de Xenites
	private final int XELION = 2; // Le nombre de Xelions

	/**
	 * Cheatmode et vie infinie
	 */
	private boolean isInCheatmode = false;

	/**
	 * Gestion des differents etats dans le jeu
	 */
	private enum etat
	{
		ENCOURS,PERDU,GAGNE
	}
	private etat etatEnJeu = etat.ENCOURS;		// 	Etat en cours par defaut au demarrage du jeu
	private Image imgGagne;
	private Image imgPerdu;

	private boolean isAlive = false; 

	@Override
	public int getID() {return ID;}

	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		imgAsteroides = new Image("res/Astero_0.png");
		imgXenites = new Image("res/Xenites.png");
		imgXelion = new Image("res/Xelions.png");
		
		TableauEtoileScrolling = new TableauEtoileScrolling();

		megablaster = new Megablaster(input);
		megablaster.init(container, game);
		
		TableauEtoileScrolling.init();
		
		sprites = new ArrayList();
		//on crée les astéroides
		for (int n = 0; n < ASTEROIDS; n++) 
		{
			Asteroids ast = new Asteroids(imgAsteroides);
			sprites.add(ast);//on ajoute ce nouvel astéroide dans la liste
		}
		spriteXenites = new ArrayList();
		//on crée les Xenites
		for (int n = 0; n < XENITES; n++) 
		{
			Xenites xenites = new Xenites(imgXenites);
			spriteXenites.add(xenites);//on ajoute ce nouveau Xenites dans la liste
		}
		spriteXelion = new ArrayList();
		//on crée les Xelions
		for (int n = 0; n < XELION; n++) 
		{
			Xelion xelion = new Xelion(imgXelion);
			spriteXelion.add(xelion);//on ajoute ce nouveau Xelion dans la liste
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		/**
		 * Affichage du fond étoilé
		 */
		//tableauEtoile.init();
		TableauEtoileScrolling.render(container, g);
		//tableauEtoile.render();

		megablaster.render(container, game, g);

		if (etatEnJeu == etat.GAGNE)
		{
			imgGagne.draw();
		} else if(etatEnJeu == etat.PERDU)
		{
			imgPerdu.draw();
		}

		/**
		 * Affichage des Asteroides
		 */
		for (int n=0; n < sprites.size(); n++)
		{
			Asteroids spr = (Asteroids) sprites.get(n);
			if (spr.isAlive())
			{
				if (spr.setAlive(true)) { spr.render(g); }
			}
		}
		/**
		 * Affichage des Xenites
		 */
		for (int n=0; n < spriteXenites.size(); n++)
		{
			Xenites sprXenites = (Xenites) spriteXenites.get(n);
			if (sprXenites.isAlive())
			{
				if (sprXenites.setAlive(true)) { sprXenites.render(g); }
			}
		}

		/**
		 * Affichage des Xelions
		 */
		for (int n=0; n < spriteXelion.size(); n++)
		{
			Xelion sprXelion = (Xelion) spriteXelion.get(n);
			if (sprXelion.isAlive())
			{
				sprXelion.render(g);
			}
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		TableauEtoileScrolling.update(container, delta);
		/**
		 * Affichage et raffraichissement des Asteroides
		 */
		for (int n=0; n < sprites.size(); n++)
		{
			Asteroids spr = (Asteroids) sprites.get(n);
			if (spr.isAlive())
			{
				if (spr.setAlive(spr.isAliveAsteroide) == true) {spr.update(); }
				if (spr.setAlive(spr.isAliveAsteroide) == false) {sprites.remove(n);}
			}
		}

		/**
		 * Affichage et raffraichissement des Xenites
		 */
		for (int n=0; n < spriteXenites.size(); n++)
		{
			Xenites sprXenites = (Xenites) spriteXenites.get(n);
			if (sprXenites.isAlive())
			{
				if (sprXenites.setAlive(sprXenites.isAliveXenite) == true) {sprXenites.update(); }
				if (sprXenites.setAlive(sprXenites.isAliveXenite) == false) {spriteXenites.remove(n);}
			}
		}

		/**
		 * Affichage et raffraichissement des Xelions
		 */
		for (int n=0; n < spriteXelion.size(); n++)
		{
			Xelion sprXelion = (Xelion) spriteXelion.get(n);
			if (sprXelion.isAlive())
			{
				sprXelion.update();
			}
		}		

		megablaster.update(container, game, delta);

		/**
		 * Gestion des impacts Asteroides
		 */
		for(int i=0;i<sprites.size();i++)  //on va contrôler tous les astéroids !!
		{
			Asteroids current = (Asteroids) sprites.get(i);
			if(megablaster.getBound().intersects(current.getBound()))  //le joueur touche un astéroid ?
			{
				//on codera la perte des points de vie du player
				System.out.println("le joueur vient de se prendre un astéroide !!");//controle que cela fonctionne
				current.destroy();
				megablaster.setCollision(true); // On retire une vie au joueur
				/** Sound explosion = new Sound("res/shoot.wav");
				explosion.play(); */
			}
			/*if(station.getBound().intersects(current.getBound()))  //la station reçoit un impact ?
			{
				//on codera la perte des points de vie de la station
			}*/
		}

		/**
		 * Gestion des impacts Xenites
		 */
		for(int i=0;i<spriteXenites.size();i++)  //on va contrôler tous les Xenites !!
		{
			Xenites currentXenites = (Xenites) spriteXenites.get(i);
			if(megablaster.getBound().intersects(currentXenites.getBound()))  //le joueur touche un Xenite ?
			{
				//on codera la perte des points de vie du player sauf s'il a un bouclier qui le protège dans ce cas, on annule le bouclier et il ne perd pas de vie
				System.out.println("le joueur vient de se prendre un Xenites !!");//controle que cela fonctionne
				currentXenites.destroy();
				megablaster.setCollision(true); // On retire une vie au joueur
			}
			/*if(station.getBound().intersects(current.getBound()))  //la station reçoit un impact ?
			{
				//on codera la perte des points de vie de la station
			}*/
		}

		/**
		 * Gestion des impacts Xelions
		 */
		for(int i=0;i<spriteXelion.size();i++)  //on va contrôler tous les Xelion !!
		{
			Xelion currentXelion = (Xelion) spriteXelion.get(i);
			if(megablaster.getBound().intersects(currentXelion.getBound()))  //le joueur touche un Xelion ?
			{
				//on codera la perte des points de vie du player sauf s'il a un bouclier qui le protège dans ce cas, on annule le bouclier et il ne perd pas de vie
				System.out.println("le joueur vient de se prendre un Xelion !!");//controle que cela fonctionne
			}
			/*if(station.getBound().intersects(current.getBound()))  //la station reçoit un impact ?
			{
				//on codera la perte des points de vie de la station
			}*/
		}
		if((megablaster.getLife() == -1) && (megablaster.isInCheatmode == false))
		{
			System.out.println("le joueur vient de perdre !!");
			//etatEnJeu = etat.PERDU;
		}
		if((sprites.size() == 0) && (spriteXenites.size() == 0) && (spriteXelion.size() == 0))
		{
			System.out.println("le joueur vient de gagner !!");
			//etatEnJeu = etat.GAGNE;
		}
	}

	public boolean isAlive() {
		return true;
	}
}

