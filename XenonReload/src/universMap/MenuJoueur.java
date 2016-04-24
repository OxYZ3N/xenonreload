package universMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 ** C'est l'état du Menu principal, c'est ici que l'on choisira/personnalisera son vaisseau, 
 *  chargera sa partie, multiplayers crédits, top 5 des meilleurs joueurs
 * @param <E>
 */

public class MenuJoueur<E> extends BasicGameState implements ComponentListener{

	public static final int ID = 3; // L'identifiant de l'état
	private Image menuJoueur;
	private Image logoBitmapBrother;
	private Image xenonPins;

	/** Instanciation des fontes */
	private SpriteSheet font;
	private Image spriteFont;

	/** Vitesse et position des fontes */
	private Random rand = new Random();
	private float x,y;
	private Vector2f vitesse ;
	private float vx,vy;
	private final int AST_SPEED = 1;
	private int speed;

	public StateBasedGame game;

	public GameContainer container;

	/** Instanciation du gestionnaire Input */
	private Input input;

	/** les boutons activés du menu joueur */
	private MouseOverArea onePlayer;
	private MouseOverArea twoPlayer;
	private MouseOverArea loadGame;
	private MouseOverArea saveGame;
	private MouseOverArea continueGame;
	private MouseOverArea controlInstructions;
	private MouseOverArea flecheGauche;
	private MouseOverArea flecheDroite;

	public String texte = "abcde";
	//public String texte = "abcdefghijkl";
	//public String texte = "mnopqrstuvwx";
	//public String texte = "yz0123456789";
	//public String texte = "><.:";
	private final int longueurScroll = texte.length();
	private int coefPas = 40;
	private int coefMult = (longueurScroll*coefPas)-coefPas;
	public int posX, posY;
	public ArrayList<E> listeFonte; // La liste qui contiendra toutes les fontes ainsi que leur nombre : public ArrayList<ScrollingTexte> sprites;
	public Map<String, Object> spriteKey = new HashMap<String, Object>(); //HashMap<String, Object> spriteKey = new HashMap<String, Object>();
	public Map<String, Object> sortingSprite = new HashMap<String, Object>();
	public String sprFonte;
	public char caractere;

	/*public MenuJoueur(SpriteSheet font2) {
		// TODO Auto-generated constructor stub
	}*/
	@Override
	public int getID() {
		return ID;
	}
	@SuppressWarnings("unchecked")
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		input = container.getInput();
		menuJoueur = new Image("res/xenon_menu.png");
		xenonPins = new Image("res/xenon_pins.png");
		logoBitmapBrother = new Image("res/Logo-bitmap-brothers.png");

		onePlayer = new MouseOverArea(container,new Image("res/oneplayer.png"), 70, 150,this);
		onePlayer.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		onePlayer.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));

		twoPlayer = new MouseOverArea(container,new Image("res/twoplayer.png"), 70,180,this);
		twoPlayer.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		twoPlayer.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));		

		loadGame = new MouseOverArea(container,new Image("res/loadgame.png"), 70,210,this);
		loadGame.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		loadGame.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));	

		saveGame = new MouseOverArea(container,new Image("res/savegame.png"), 70,240,this);
		saveGame.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		saveGame.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));	

		continueGame = new MouseOverArea(container,new Image("res/continuegame.png"), 70,370,this);
		continueGame.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		continueGame.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));

		controlInstructions = new MouseOverArea(container,new Image("res/controlinstructions.png"), 70,400,this);
		controlInstructions.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		controlInstructions.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));

		flecheGauche = new MouseOverArea(container,new Image("res/flechegauche.png"), 451,210,this);
		flecheGauche.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		flecheGauche.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));

		flecheDroite = new MouseOverArea(container,new Image("res/flechedroite.png"), 736,210,this);
		flecheDroite.setNormalColor(new Color(0.9f,0.9f,0.9f,1f));
		flecheDroite.setMouseOverColor(new Color(0.7f,0.7f,0.7f,1f));

		font = new SpriteSheet("res/font.png", 30,44);

		/*listeFonte = new ArrayList();
		for (int n = 0; n < longueurScroll; n++) 
		{
			MenuJoueur ast = new MenuJoueur(font);
			listeFonte.add((E) ast);//on ajoute cette nouvelle fonte dans la liste
		}
		 */
		vitesse = new Vector2f();

		//crée une position initiale
		float x = 700;
		float y = 500;
		setPosition(x, y);

		vx = (float) -2; //Vitesse de déplacement

		while(vx == 0)/*on évite les valeurs 0 = fontes immobiles !*/
		{
			vx = (float) rand.nextInt(5)-2;
		}
		setVitesse((int)+2,vx,vy); //setVitesse((int)rand.nextInt(3)*AST_SPEED,vx,vy);
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		menuJoueur.draw(0,0);
		onePlayer.render(container, g);
		twoPlayer.render(container, g);

		loadGame.render(container, g);
		saveGame.render(container, g);

		continueGame.render(container, g);
		controlInstructions.render(container, g);

		flecheGauche.render(container, g);
		flecheDroite.render(container, g);

		//spriteFont = font.getSprite(0,0);
		//for (int n=0; n < listeFonte.size(); n++)
		//{
		//	MenuJoueur spr = (MenuJoueur) listeFonte.get(n);
		//	//spr.render(container, game, g);
		//	spriteFontA.draw(x,y);
		//	spriteFontB.draw(x+40,y);
		//}

		// http://stackoverflow.com/questions/780541/how-to-sort-hash-map

		int blankSpace = 0;
		for (String listeSprite : spriteKey.keySet()) //key, hash
		{
			Object titi = spriteKey.get(listeSprite);
			((Image) titi).draw(x+blankSpace,y);
			blankSpace = blankSpace + coefPas;
			if (blankSpace>coefMult) blankSpace = 0; //0+40+40+40+40
		}

		xenonPins.draw(0,496);
		logoBitmapBrother.draw(719,496);

		this.game = game;
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_ESCAPE))
		{
			game.enterState(Menu.ID); //on revient au menu principal
		}
		x = ((x - vitesse.x)-speed);
		mappingTexteVersFonte(texte);
		sortingListTexte(sortingSprite);
		bordureDebordementTexte();
	}

	public void mappingTexteVersFonte(String mapTexte)
	{
		StringBuilder strBuilder = new StringBuilder(mapTexte);
		String strReverse = strBuilder.reverse().toString();

		//TODO:A mettre dans une Hashmap
		for (int i=0;i<strReverse.length();i++)
		{
			strReverse.toLowerCase();
			caractere = strReverse.charAt(i);
			switch(caractere)
			{
			case 'a':
				posX=0;
				posY=0;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'b':
				posX=0;
				posY=1;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'c':
				posX=0;
				posY=2;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'd':
				posX=0;
				posY=3;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'e':
				posX=0;
				posY=4;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'f':
				posX=0;
				posY=5;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'g':
				posX=0;
				posY=6;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'h':
				posX=0;
				posY=7;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'i':
				posX=0;
				posY=8;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'j':
				posX=1;
				posY=0;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'k':
				posX=1;
				posY=1;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'l':
				posX=1;
				posY=2;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'm':
				posX=1;
				posY=3;
				affichageTexteVersFonte(posX, posY);
				break;	
			case 'n':
				posX=1;
				posY=4;
				affichageTexteVersFonte(posX, posY);
				break;		
			case 'o':
				posX=1;
				posY=5;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'p':
				posX=1;
				posY=6;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'q':
				posX=1;
				posY=7;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'r':
				posX=1;
				posY=8;
				affichageTexteVersFonte(posX, posY);
				break;
			case 's':
				posX=2;
				posY=0;
				affichageTexteVersFonte(posX, posY);
				break;
			case 't':
				posX=2;
				posY=1;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'u':
				posX=2;
				posY=2;
				affichageTexteVersFonte(posX, posY);
				break;				
			case 'v':
				posX=2;
				posY=3;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'w':
				posX=2;
				posY=4;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'x':
				posX=2;
				posY=5;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'y':
				posX=2;
				posY=6;
				affichageTexteVersFonte(posX, posY);
				break;
			case 'z':
				posX=2;
				posY=7;
				affichageTexteVersFonte(posX, posY);
				break;
			case '0':
				posX=2;
				posY=8;
				affichageTexteVersFonte(posX, posY);
				break;
			case '1':
				posX=3;
				posY=0;
				affichageTexteVersFonte(posX, posY);
				break;
			case '2':
				posX=3;
				posY=1;
				affichageTexteVersFonte(posX, posY);
				break;
			case '3':
				posX=3;
				posY=2;
				affichageTexteVersFonte(posX, posY);
				break;
			case '4':
				posX=3;
				posY=3;
				affichageTexteVersFonte(posX, posY);
				break;
			case '5':
				posX=3;
				posY=4;
				affichageTexteVersFonte(posX, posY);
				break;
			case '6':
				posX=3;
				posY=5;
				affichageTexteVersFonte(posX, posY);
				break;
			case '7':
				posX=3;
				posY=6;
				affichageTexteVersFonte(posX, posY);
				break;
			case '8':
				posX=3;
				posY=7;
				affichageTexteVersFonte(posX, posY);
				break;
			case '9':
				posX=3;
				posY=8;
				affichageTexteVersFonte(posX, posY);
				break;
			case '>':
				posX=4;
				posY=0;
				affichageTexteVersFonte(posX, posY);
				break;
			case '<':
				posX=4;
				posY=1;
				affichageTexteVersFonte(posX, posY);
				break;
			case '.':
				posX=4;
				posY=2;
				affichageTexteVersFonte(posX, posY);
				break;
			case ':':
				posX=4;
				posY=3;
				affichageTexteVersFonte(posX, posY);
				break;
			}
		}
	}

	public void affichageTexteVersFonte(int posX, int posY)
	{
		spriteFont = font.getSprite(posX,posY);
		sprFonte = toString().valueOf(caractere);
		spriteKey.put(sprFonte, spriteFont);
	}

	public void sortingListTexte(Map<String, Object> sortingSprite)
	{
		List<Object> keySortByAscHashNumber = new ArrayList<Object>(spriteKey.keySet());

		Collections.sort(keySortByAscHashNumber, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				return o1.hashCode() - o2.hashCode();
			}
		});

		for (Object p : keySortByAscHashNumber) 
		{
			System.out.println(p.hashCode() + " ");
			sortingSprite.put(Integer.toString(p.hashCode()), "");
		}
	}

	public void setVitesse(int i, float vx2, float vy2) {
		if (i==0) {speed = i+1;} // Si la valeur est égale à 0, on ajoute 1 pour éviter que la fonte soit immobile !
		else {speed = i;}
		vx = vx2;
	}

	public void setPosition(float x2, float y2) {
		x = x2;
		y = y2;
	}

	public void bordureDebordementTexte()
	{
		//int w = spriteFontA.getWidth()-1;
		int w = spriteFont.getWidth()-1;
		float newx = x;
		if(x < -w) {newx = 800 + w;}
		else if(x > 800+w){newx = -w;}
		setPosition(newx,y);//enregistre les changements
	}

	public void componentActivated(AbstractComponent arg0) {
		// TODO Auto-generated method stub
	}
}
