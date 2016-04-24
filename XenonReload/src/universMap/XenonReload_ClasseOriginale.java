package universMap;

/**
 *
 * La classe est le point d'entré du jeu
 */
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class XenonReload_ClasseOriginale extends StateBasedGame{

	private Menu menu;
	private GameState gameState;
	private Asteroids asteroids;

	public static AppGameContainer container;

	public XenonReload_ClasseOriginale(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
    	menu = new Menu();
    	gameState = new GameState(null, null);
    	gameState = new GameState();
        addState(menu);
        addState(gameState);
        /*
		if (container instanceof AppGameContainer)  {

			this.container = (AppGameContainer) container;// on stocke le conteneur du jeu !
		}

		//menu = new MenuState();
		//jeu = new GameState3();//le jeu en lui meme !!
		if (Menu.etat == 0)
		{
			//menu = new MenuState();
			//addState(menu);
		}
		else if (Menu.etat == 2)
		{
			//jeu = new GameState();//le jeu en lui meme !!
			//addState(jeu);
		}
		container.setShowFPS(true);//on ne veut pas voir le FPS ?? mettre alors "false" !
		container.setMinimumLogicUpdateInterval(10);//permet d'avoir une vitesse de rafraÃ®chissment constante qquesoit l'ordi !

		//addState(jeu);	//on ajoute le GameState au conteneur !
		 */
	}


	public static void main(String[] args) {
		try
		{
			//AppGameContainer container = new AppGameContainer(new XenonReload("Xenon Reload (2012-2013)"));
			container = new AppGameContainer(new XenonReload_ClasseOriginale("Xenon Reload (2012-2013)"));
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(60);
			container.setMultiSample(4);
			container.setVSync(true);
			container.start();
		} catch (SlickException e) {e.printStackTrace();}
	}
	/*
			if (Menu.etat == 0 )
			{
				AppGameContainer container = new AppGameContainer(new XenonReload());
				container.setDisplayMode(800, 600, false);
				container.setTargetFrameRate(60);
				container.start();
			} else if (Menu.etat == 2)
			{
				AppGameContainer container4 = new AppGameContainer((Game) new XenonReload());
				container4.setDisplayMode(800, 600, false);
				container4.setTargetFrameRate(60);
				//container4.reinit(); //	start();
				container4.start();
				// AppGameContainer container4 = new AppGameContainer(new HelloSound());
				//container4.setDisplayMode(800, 600, false);
				//container4.setTargetFrameRate(60);
				//container4.start();
			}
		}
		//catch (SlickException e) {e.printStackTrace();}  // l'exception de base de slick !!
	 */
} 
