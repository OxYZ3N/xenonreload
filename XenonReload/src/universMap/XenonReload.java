package universMap;

/**
 *
 * La classe est le point d'entré du jeu
 */
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class XenonReload extends StateBasedGame{

	private Menu menu;
	private MenuJoueur menuJoueur;
	private GameState gameState;
	private Asteroids asteroids;

	public static AppGameContainer container;

	public XenonReload(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		menu = new Menu();
		gameState = new GameState();
		menuJoueur = new MenuJoueur<Object>();
		addState(menu);
		addState(menuJoueur);
		addState(gameState);
		//container.setShowFPS(true);					//on ne veut pas voir le FPS ?? mettre alors "false" !
		//container.setMinimumLogicUpdateInterval(10);	//permet d'avoir une vitesse de rafraichissment constante qquesoit l'ordi !
	}

	public static void main(String[] args) {
		try
		{
			container = new AppGameContainer(new XenonReload("Xenon Reload (2012-2013)"));
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(60);
			container.setMultiSample(4);
			container.setVSync(true);
			container.start();
		} catch (SlickException e) {e.printStackTrace();}
	}
} 
